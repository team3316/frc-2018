package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorJoystick;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorShaken;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for the Elevator subsystem.
 */
public class Elevator extends DBugSubsystem {
	// Variables
	private double offset;

	// Sensors
	private DigitalInput heBottom, heTop;
	private Encoder encoder;
	private double encoderDistPerPulse;

	// Actuators
	private DBugSpeedController motor1, motor2;
	private DoubleSolenoid shifter;

	/**
	 * Elevator levels enum
	 */
	public enum Level {
		Bottom(1), Switch(2), Scale(3), Top(4), Intermediate(0);

		private int type;

		private Level(int type) {
			this.type = type;
		}

		private String getConfigKey() {
			String baseKey = "elevator_setpoint_";
			switch (this.type) {
			case 1:
				return baseKey + "bottom";
			case 2:
				return baseKey + "switch";
			case 3:
				return baseKey + "scale";
			case 4:
				return baseKey + "top";
			default:
				return "";
			}
		}

		public double getSetpoint() {
			String configKey = this.getConfigKey();
			if (configKey == "")
				return Double.NaN;
			return (double) Robot.config.get(configKey);
		}
	}

	/**
	 * Ball shifter gears
	 */
	public enum Gear {
		HIGH, LOW;
	}

	/**
	 * Elevator distance PID source
	 */
	public class DistanceSource implements PIDSource {
		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return Robot.elevator.getPosition();
		}

		@Override
		public void setPIDSourceType(PIDSourceType arg0) {
			return;
		}
	}

	/**
	 * Elevator distance PID output
	 */
	public class DistanceOutput implements PIDOutput {
		@Override
		public void pidWrite(double voltage) {
			Robot.elevator.setMotors(voltage);
			logger.info("ele_v:" + voltage);
		}
	}

	/**
	 * Constructor
	 */
	public Elevator() {
		// Sensors
		Robot.sensors.ElevatorSensors();
		this.heBottom = Robot.sensors.elevatorHeBottom;
		this.heTop = Robot.sensors.elevatorHeTop;
		this.encoder = Robot.sensors.elevatorEncoder;
		
		this.encoderDistPerPulse = (double)config.get("ELEVATOR_ENCODER_DISTANCE_PER_PULSE");

		// Actuators
		Robot.actuators.ElevatorActuators();
		this.motor1 = Robot.actuators.elevatorMotorOne;
		this.motor2 = Robot.actuators.elevatorMotorTwo;
		this.shifter = Robot.actuators.elevatorShifter;
	}

	@Override
	public void initDefaultCommand() {
	    setDefaultCommand(new ElevatorShaken());
	}

	@Override
	public void periodic() {
		 double setpoint = this.getLevel().getSetpoint();
		 if (!Double.isNaN(setpoint)) {
		     this.offset = setpoint - this.encoder.getRaw() * encoderDistPerPulse;
		 }
	}

	/**
	 * Sets both the elevator motors to the same output voltage.
	 * 
	 * @param voltage
	 *            - The output voltage
	 */
	public void setMotors(double voltage) {
		 Level l = this.getLevel();
		 if ((l == Level.Bottom && voltage < 0) || l == Level.Top && voltage > 0) return;
		this.motor1.setMotor(voltage);
		this.motor2.setMotor(voltage);
	}

	/**
	 * Sets both the motors to brake, according to the parameter.
	 * 
	 * @param brakeMode
	 *            - The brake mode: true for brake, false for coast
	 */
	public void setBrake(boolean brakeMode) {
		this.motor1.switchToBrake(brakeMode);
		this.motor2.switchToBrake(brakeMode);
	}

	/**
	 * Returns the current level of the elevator according to the hall effect
	 * sensors.
	 * 
	 * @return The current level of the elevator
	 */
	public Level getLevel() {
		if (!this.heBottom.get()) return Level.Bottom;
		if (!this.heTop.get()) return Level.Top;
		return Level.Intermediate;
	}
	
	public Gear getGear() {
		if (shifter.get() == Value.kReverse) {
			return Gear.LOW;
		}
		else {
			return Gear.HIGH;
		}
	}

	/**
	 * Retrieves the current position of the elevator using the ball shifter's
	 * encoder value.
	 * 
	 * @return The elevator's position (in meters)
	 */
	public double getPosition() {
		return this.encoder.getRaw() * encoderDistPerPulse + this.offset;
	}

	/**
	 * Shifts the ball shifter gearbox to the wanted gear.
	 * 
	 * @param gear
	 *            - The wanted gear: high or low
	 */
	public void shiftGear(Gear gear) {
		switch (gear) {
		case HIGH:
			this.shifter.set(Value.kForward);
			break;
		case LOW:
			this.shifter.set(Value.kReverse);
			break;
		default:
			this.shifter.set(Value.kOff);
			break;
		}
	}

	/**
	 * Returns a PID controller configured with the provided parameters, and has a
	 * setpoint according to the given level.
	 * 
	 * @param kP
	 *            - The proportional constant
	 * @param kI
	 *            - The integral constant
	 * @param kD
	 *            - The differential constant
	 * @param level
	 *            - The wanted level to make the elevator reach
	 * @return A PID controller with the provided constants.
	 */
	public PIDController getPIDControllerElevator(double kP, double kI, double kD, double kF, double setpoint) {
		return this.getPIDController(kP, kI, kD, kF, setpoint);
	}

	/**
	 * Returns a PID controller configured with the provided parameters.
	 * 
	 * @param kP
	 *            - The proportional constant
	 * @param kI
	 *            - The integral constant
	 * @param kD
	 *            - The differential constant
	 * @param setpoint
	 *            - The wanted setpoint to make the elevator reach
	 * @return A PID controller with the provided constants.
	 */
	public PIDController getPIDController(double kP, double kI, double kD, double kF, double setpoint) {
		PIDController pid = new PIDController(kP, kI, kD, kF, new DistanceSource(), new DistanceOutput());
//		if (!Double.isNaN(setpoint)) {
//		    pid.setSetpoint(setpoint);
//		}
//		pid.setContinuous();
		pid.setAbsoluteTolerance((double) Robot.config.get("elevator_PID_Tolerance"));
		pid.setOutputRange(-1.0, 1.0);
		return pid;
	}
}
