package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.PIDControllers;

import edu.wpi.first.wpilibj.PIDController;

/**
 *
 */
public class SetSpeed extends DBugCommand {
	private double speed = 0;
	private PIDController pidLeftSpeed, pidRightSpeed, pidYaw;

	public SetSpeed(double speed) {
		this.speed = speed;

		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void init() {
		Robot.chassis.setBrake(true);

		pidLeftSpeed = PIDControllers.getSpeedPID(true, (double) config.get("chassis_SetSpeed_PID_KP") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KI") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KD") / 1000, 0);
		pidRightSpeed = PIDControllers.getSpeedPID(false, (double) config.get("chassis_SetSpeed_PID_KP") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KI") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KD") / 1000, 0);
		pidYaw = PIDControllers.getYawPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000, 0);

		pidLeftSpeed.setOutputRange(-1, 1);
		pidRightSpeed.setOutputRange(-1, 1);
		pidYaw.setOutputRange(-1, 1);

		pidLeftSpeed.setSetpoint(speed);
		pidRightSpeed.setSetpoint(speed);
		pidYaw.setSetpoint(0.0);

		pidLeftSpeed.enable();
		pidRightSpeed.enable();
		pidYaw.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Nothin' here
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void fin() {
		pidLeftSpeed.reset();
		pidLeftSpeed.disable();

		pidRightSpeed.reset();
		pidRightSpeed.disable();

		pidYaw.reset();
		pidYaw.disable();

		Robot.chassis.setMotors(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interr() {
		logger.info("DriveDistance interrupted");
		fin();
	}
}
