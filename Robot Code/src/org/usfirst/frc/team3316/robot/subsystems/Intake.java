package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

/**
 * The intake susbsystem
 * @author jonathano
 */
public class Intake extends DBugSubsystem {
	// Actuators
	private DBugSpeedController leftSC, rightSC;

	/**
	 * Constructor
	 */
	public Intake() {
		Robot.actuators.IntakeActuators();
		this.leftSC = Robot.actuators.intakeLeft;
		this.rightSC = Robot.actuators.intakeRight;
	}

	@Override
	public void initDefaultCommand() {
		// TODO: Add default command
	}

	/**
	 * Set the motor output voltage independently.
	 * @param vLeft voltage for the left motor
	 * @param vRight voltage for the right motor
	 */
	public void setMotors(double vLeft, double vRight) {
		this.leftSC.setMotor(vLeft);
		this.rightSC.setMotor(vRight);
	}

	/**
	 * Set the motor output voltage to both motors.
	 * @param v voltage for both the motors
	 */
	public void setMotors(double v) {
		this.leftSC.setMotor(v);
		this.rightSC.setMotor(v);
	}

	/**
	 * Changes the gripper direction.
	 */
	public void changeDirection() {
		this.leftSC.invert();
		this.rightSC.invert();
	}
}
