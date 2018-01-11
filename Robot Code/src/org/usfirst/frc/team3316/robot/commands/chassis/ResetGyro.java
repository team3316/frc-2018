package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ResetGyro extends DBugCommand
{
	// TODO: Add commenting

	public ResetGyro()
	{}

	@Override
	protected void init() {
		Robot.chassis.resetYaw();
		Robot.chassis.resetPitch();
		Robot.chassis.resetRoll();
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
