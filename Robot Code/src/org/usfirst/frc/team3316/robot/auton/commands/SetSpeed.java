package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class SetSpeed extends DBugCommand {

	private double speed = 0, initYaw = 0;
	private PIDController pidLeftSpeed, pidRightSpeed, pidYaw;
	private double velocity = 0, ratio = 0;

	public SetSpeed(double speed) {
		this.speed = speed;

//		initPIDSpeed();
//		initPIDYaw();
		this.pidLeftSpeed = Robot.chassis.setSpeedPID(true, 0, 0, 0, 0);
		this.pidRightSpeed = Robot.chassis.setSpeedPID(false, 0, 0, 0, 0);

		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void init() {
		Robot.chassis.setBrake(true);

		pidLeftSpeed.setOutputRange(-1, 1);
		pidRightSpeed.setOutputRange(-1, 1);
		pidYaw.setOutputRange(-1, 1);

		pidLeftSpeed.setPID((double) config.get("chassis_SetSpeed_PID_KP") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KI") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KD") / 1000);
		pidRightSpeed.setPID((double) config.get("chassis_SetSpeed_PID_KP") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KI") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KD") / 1000);
		pidYaw.setPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000);

		pidLeftSpeed.setSetpoint(speed);
		pidRightSpeed.setSetpoint(speed);
		pidYaw.setSetpoint(0.0);

		initYaw = Robot.chassis.getYaw();

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
