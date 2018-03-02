package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class BrakeMode extends DBugCommand {
	// TODO: Add commenting

	public BrakeMode() {
	}

	@Override
	protected void init() {
		Robot.chassis.setBrake(true);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void fin() {
	}

	@Override
	protected void interr() {
	}
}
