package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class StopChassis extends DBugCommand {

    public StopChassis() {
	requires(Robot.chassis);
    }
    
    @Override
    protected void init() {
	Robot.chassis.setMotors(0.0, 0.0);
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
