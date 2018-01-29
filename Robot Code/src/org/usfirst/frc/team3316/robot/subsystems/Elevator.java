package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Class for the Elevator subsystem.
 */
public class Elevator extends DBugSubsystem {
	// Variables
	private double offset;

	// Sensors
	private DigitalInput heBottom, heSwitch, heScale, heTop;
	private Encoder encoder;
	
	// Actuators
	private DBugSpeedController motorOne, motorTwo;
	private DoubleSolenoid shifter;

	/**
	 * Elevator levels enum
	 */
	public enum Level {
		Bottom(1), Switch(2), Scale(3), Top(4), Intermediate(0);

		private int type;

		private Level (int type) {
			this.type = type;
		}

		private String getConfigKey () {
			String baseKey = "elevator_setpoint_";
			switch (this.type) {
				case 1: return baseKey + "bottom";
				case 2: return baseKey + "switch";
				case 3: return baseKey + "scale";
				case 4: return baseKey + "top";
				default: return "";
			}
		}

		public double getSetpoint () {
			String configKey = this.getConfigKey();
			if (configKey == "") return Double.NaN;
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
		}
	}

	/**
	 * Constructor
	 */
	public Elevator() {
		// Sensors
		Robot.sensors.ElevatorSensors();
		this.heBottom = Robot.sensors.elevatorHeBottom;
		this.heSwitch = Robot.sensors.elevatorHeSwitch;
		this.heScale = Robot.sensors.elevatorHeScale;
		this.heTop = Robot.sensors.elevatorHeTop;
		this.encoder = Robot.sensors.elevatorEncoder;
	
		// Actuators
		Robot.actuators.ElevatorActuators();
		this.motorOne = Robot.actuators.elevatorMotorOne;
		this.motorTwo = Robot.actuators.elevatorMotorTwo;
		this.shifter = Robot.actuators.elevatorShifter;
	}

	@Override
	public void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	@Override
	public void periodic() {
		double setpoint = this.getLevel().getSetpoint();
		if (!Double.isNaN(setpoint)) {
			this.offset = setpoint - this.encoder.getDistance();
		}
	}

	/**
	 * Sets both the elevator motors to the same output voltage.
	 * @param voltage - The output voltage
	 */
	public void setMotors (double voltage) {
		Level l = this.getLevel();
		if (l == Level.Bottom || l == Level.Top) return;
		this.motorOne.setMotor(voltage);
		this.motorTwo.setMotor(voltage);
	}

	/**
	 * Sets both the motors to brake, according to the parameter.
	 * @param brakeMode - The brake mode: true for brake, false for coast
	 */
	public void setBrake (boolean brakeMode) {
		this.motorOne.switchToBrake(brakeMode);
		this.motorTwo.switchToBrake(brakeMode);
	}

	/**
	 * Returns the current level of the elevator according to the hall effect sensors.
	 * @return The current level of the elevator
	 */
	public Level getLevel () {
		if (this.heBottom.get()) return Level.Bottom;
		if (this.heSwitch.get()) return Level.Switch;
		if (this.heScale.get()) return Level.Scale;
		if (this.heTop.get()) return Level.Top;
		return Level.Intermediate;
	}

	/**
	 * Retrieves the current position of the elevator using the ball shifter's encoder value.
	 * @return The elevator's position in the range [0, 100]
	 */
	public double getPosition () {
		return this.encoder.getDistance() + this.offset;
	}

	/**
	 * Shifts the ball shifter gearbox to the wanted gear.
	 * @param gear - The wanted gear: high or low
	 */
	public void shiftGear (Gear gear) {
		switch (gear) {
			case HIGH: this.shifter.set(Value.kForward);
			case LOW: this.shifter.set(Value.kReverse);
			default: this.shifter.set(Value.kOff);
		}
	}

	/**
	 * Returns a PID controller configured with the provided parameters, and has a setpoint according to the given level.
	 * @param kP - The proportional constant
	 * @param kI - The integral constant
	 * @param kD - The differential constant
	 * @param level - The wanted level to make the elevator reach
	 * @return A PID controller with the provided constants.
	 */
	public PIDController getPIDController (double kP, double kI, double kD, Elevator.Level level) {
		return this.getPIDController(kP, kI, kD, level.getSetpoint());
	}

	/**
	 * Returns a PID controller configured with the provided parameters.
	 * @param kP - The proportional constant
	 * @param kI - The integral constant
	 * @param kD - The differential constant
	 * @param setpoint - The wanted setpoint to make the elevator reach
	 * @return A PID controller with the provided constants.
	 */
	public PIDController getPIDController (double kP, double kI, double kD, double setpoint) {
		PIDController pid = new PIDController(kP, kI, kD, new DistanceSource(), new DistanceOutput());
		if (!Double.isNaN(setpoint)) pid.setSetpoint(setpoint);
		pid.setContinuous();
		pid.setAbsoluteTolerance((double) Robot.config.get("elevator_PID_Tolerance"));
		pid.setOutputRange(-1, 1);
		return pid;
	}
}
