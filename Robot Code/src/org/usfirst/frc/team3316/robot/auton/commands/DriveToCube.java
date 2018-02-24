package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignRobot;

/**
 *
 */
public class DriveToCube extends DBugCommand {
	DBugCommand cmd;
	int iterationNum = 0;

	public DriveToCube() {
	}

	// Called just before this Command runs the first time
	protected void init() {
		cmd = new DriveDistance(AlignRobot.getDistanceFromCube());
		cmd.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		iterationNum++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (iterationNum > 1 && cmd.isRunning()) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void fin() {
		cmd = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interr() {
		fin();
	}
}
