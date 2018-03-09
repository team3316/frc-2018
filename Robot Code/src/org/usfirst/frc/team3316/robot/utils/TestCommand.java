package org.usfirst.frc.team3316.robot.utils;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class TestCommand extends DBugCommand {
	@Override
	protected void init() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void execute() {
		System.out.println("test completed!");
	}

	@Override
	protected boolean isFinished() {
		return false;
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
