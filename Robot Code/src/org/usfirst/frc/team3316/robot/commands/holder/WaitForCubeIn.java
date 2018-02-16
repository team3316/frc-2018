package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class WaitForCubeIn extends DBugCommand {

    public WaitForCubeIn() {}

    @Override
    protected void init() {}

    @Override
    protected void execute() {}

    @Override
    protected boolean isFinished() {
	// TODO Auto-generated method stub
	return Robot.holder.isCubeIn();
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
