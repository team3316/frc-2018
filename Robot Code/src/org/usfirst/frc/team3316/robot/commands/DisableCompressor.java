package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class DisableCompressor extends DBugCommand {

	@Override
	protected void init() {
		Robot.actuators.compressor.stop();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void fin() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interr() {
		// TODO Auto-generated method stub

	}

}
