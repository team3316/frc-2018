package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorShaken;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Class for the Elevator subsystem.
 */
public class Elevator extends DBugSubsystem {
	// Variables
	private double tolerance, switchPosition;
	public boolean joystickControl = false;

	// Sensors
	private DigitalInput heBottom, heTop, heBPBottom, heBPTop;
	private Encoder encoder;

	// Actuators
	private DBugSpeedController motor1, motor2;
	private DoubleSolenoid shifter;

	/**
	 * Elevator levels enum
	 */
	public enum Level {
		Bottom(1), Switch(2), Scale(3), Top(4), Intermediate(0), BrakePointBottom(5), BrakePointTop(6);

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
	 * Constructor
	 */
	public Elevator() {
		// Sensors
		Robot.sensors.ElevatorSensors();
		this.heBottom = Robot.sensors.elevatorHeBottom;
		this.heTop = Robot.sensors.elevatorHeTop;
		this.heBPBottom = Robot.sensors.elevatorHeBPBottom; // Brake point hall effect
		this.heBPTop = Robot.sensors.elevatorHeBPTop; // Brake point hall effect
		this.encoder = Robot.sensors.elevatorEncoder;

		// Actuators
		Robot.actuators.ElevatorActuators();
		this.motor1 = Robot.actuators.elevatorMotorOne;
		this.motor2 = Robot.actuators.elevatorMotorTwo;
		this.shifter = Robot.actuators.elevatorShifter;

		this.switchPosition = Level.Switch.getSetpoint();
		this.tolerance = (double) config.get("elevator_level_tolerance");
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorShaken());
	}

	@Override
	public void periodic() {
		if (this.getLevel() == Level.Bottom) {
			this.encoder.reset();
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
		if (l == Level.Bottom && voltage < 0.0) {
			return;
		} else if (l == Level.Top && voltage > 0.0) {
			return;
		} else {
			this.motor1.setMotor(voltage);
			this.motor2.setMotor(voltage);
		}
	}

	/**
	 * Sets both the motors to brake, according to the parameter.
	 * 
	 * @param brakeMode
	 *            The brake mode: true for brake, false for coast
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
		if (!this.heBottom.get())
			return Level.Bottom;
		if (!this.heTop.get())
			return Level.Top;
		if (!this.heBPBottom.get())
			return Level.BrakePointBottom;
		if (!this.heBPTop.get())
			return Level.BrakePointTop;
		if (Utils.isInNeighborhood(this.getPosition(), switchPosition, tolerance))
			return Level.Switch;
		return Level.Intermediate;
	}

	public Gear getGear() {
		if (shifter.get() == Value.kReverse) {
			return Gear.LOW;
		} else {
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
		return this.encoder.getDistance();
	}

	/**
	 * Sets the motor voltage according to a given setpoint and a given tolerance.
	 * 
	 * @param setpoint
	 *            The given setpoint
	 * @param tolerance
	 *            The given tolerance
	 * @param upVoltage
	 *            The voltage to set the motors to when the position is too low
	 * @param downVoltage
	 *            The voltage to set the motors to when the position is too high
	 */
	public void setBangbangVoltage(double setpoint, double tolerance, double upVoltage, double downVoltage) {
		double position = this.getPosition();
		if (position < (setpoint - tolerance)) {
			this.setMotors(upVoltage);
		} else if (position > (setpoint + tolerance)) {
			this.setMotors(downVoltage);
		} else {
			this.setMotors(0.0);
		}
	}

	/**
	 * Shifts the ball shifter gearbox to the wanted gear.
	 * 
	 * @param gear
	 *            The wanted gear: high or low
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
}
