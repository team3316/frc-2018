package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignRobot;

public class DriveToCube extends DBugCommand {
	DBugCommand cmd;

	// Called just before this Command runs the first time
	protected void init() {
		cmd = new DriveDistance(AlignRobot.getDistanceFromCube());
		cmd.start();
	}

	protected void execute() {
		// Nothing here
	}

	protected boolean isFinished() {
		return cmd.isRunning();
	}

	protected void fin() {
		cmd = null;
	}

	protected void interr() {
		fin();
	}
}
