package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.PIDControllers;

import edu.wpi.first.wpilibj.PIDController;

/**
 * A command for driving a given amount of meters forward.
 */
public class DriveDistance extends DBugCommand {

	private PIDController pidDrive, pidYaw;
	private double distance;
	public boolean started = false;

	public DriveDistance(double dist) {
		requires(Robot.chassis);
		requires(Robot.pidControllers);

		distance = dist;
		started = false;
	}

	// Called just before this Command runs the first time
	protected void init() {
		Robot.chassis.setBrake(true);

		// REMARK: Need to reset yaw _before_ this command initializes
		pidYaw = PIDControllers.getYawPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000, 0);
		pidDrive = PIDControllers.getDrivePID((double) config.get("chassis_DriveDistance_PID_DRIVE_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_DRIVE_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_DRIVE_KD") / 1000, 0);

		pidDrive.setOutputRange(-1, 1);
		pidYaw.setOutputRange(-1, 1);

		pidDrive.setAbsoluteTolerance((double) config.get("chassis_DriveDistance_PID_Tolerance"));

		pidDrive.setSetpoint(distance);
		pidYaw.setSetpoint(0.0);

		pidDrive.enable();
		pidYaw.enable();
		started = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Nothing here
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pidDrive.onTarget();
	}

	// Called once after isFinished returns true
	protected void fin() {
		pidDrive.reset();
		pidDrive.disable();

		pidYaw.reset();
		pidYaw.disable();

		Robot.chassis.setMotors(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interr() {
		fin();
	}
}
