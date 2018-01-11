package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class CoastMode extends DBugCommand
{
	// TODO: Add commenting

	public CoastMode()
	{}

	@Override
	protected void init() {
		Robot.chassis.setBrake(false);
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void fin() {}

	@Override
	protected void interr() {}
}
