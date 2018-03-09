package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class WaitForCubeIn extends DBugCommand {
	private long lastTime;
	private double stallTime;

	@Override
	protected void init() {
		lastTime = System.currentTimeMillis();
		stallTime = (double) config.get("cubeWait_stall_time");
	}

	@Override
	protected void execute() {
		if (!Robot.holder.isCubeIn()) {
			lastTime = System.currentTimeMillis();
		}
	}

	@Override
	protected boolean isFinished() {
		return Robot.holder.isCubeIn() && System.currentTimeMillis() - lastTime >= stallTime;
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
