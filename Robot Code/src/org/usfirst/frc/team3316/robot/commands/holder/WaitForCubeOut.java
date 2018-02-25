package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class WaitForCubeOut extends DBugCommand {
	private long difference, currentTime;

	@Override
	protected void init() {
		difference = 0;
		currentTime = System.currentTimeMillis();
	}

	@Override
	protected void execute() {
		currentTime = System.currentTimeMillis();
		difference = Robot.holder.isCubeIn() ? currentTime - difference : 0;
	}

	@Override
	protected boolean isFinished() {
		return Robot.holder.isCubeIn() && difference >= 500.0;
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
