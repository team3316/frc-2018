package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class MoveChassis extends DBugCommand {

    double leftV, rightV;
    
    /**
     * Move the chassis using values to the speed controllers
     * @param l - -1 to 1
     * @param r - -1 to 1
     */
    public MoveChassis(double l, double r) {
	requires(Robot.chassis);
	
	leftV = l;
	rightV = r;
    }
    
    @Override
    protected void init() {
	Robot.chassis.setMotors(0.0, 0.0);
    }

    @Override
    protected void execute() {
	Robot.chassis.setMotors(leftV, rightV);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void fin() {
	Robot.chassis.setMotors(0.0, 0.0);
    }

    @Override
    protected void interr() {
	fin();
    }

}
