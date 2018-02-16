package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class WaitForCubeOut extends DBugCommand {
    private double timer;
    
    public WaitForCubeOut() {}

    @Override
    protected void init() {
	timer = 0.0;
    }

    @Override
    protected void execute() {
	if (!Robot.holder.isCubeIn()) {
	    timer += 20.0;
	}
	else {
	    timer = 0.0;
	}
    }

    @Override
    protected boolean isFinished() {
	// TODO Auto-generated method stub
	return (!Robot.holder.isCubeIn() && timer >= 500.0);
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
