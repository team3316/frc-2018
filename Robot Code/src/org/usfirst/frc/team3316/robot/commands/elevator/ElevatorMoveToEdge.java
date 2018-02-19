package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

public class ElevatorMoveToEdge extends DBugCommand {
    Level level;
    double v;
    
    public ElevatorMoveToEdge(Level level) {
	requires(Robot.elevator);
	this.level = level;
    }

    @Override
    protected void init() {
	switch (level) {
	case Bottom:
	    v = -(double)config.get("elevator_BangBang_UpVoltage");
	    break;
	case Top:
	    v = (double)config.get("elevator_BangBang_UpVoltage");
	    break;
	default:
	    v = 0.0;
	    break;   
	}
    }

    @Override
    protected void execute() {
	Robot.elevator.setMotors(v);
    }

    @Override
    protected boolean isFinished() {
	return Robot.elevator.getLevel() == level;
    }

    @Override
    protected void fin() {
	Robot.elevator.setMotors(0.0);
    }

    @Override
    protected void interr() {
	fin();
    }

}
