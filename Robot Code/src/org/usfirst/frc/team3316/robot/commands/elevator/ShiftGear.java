package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Gear;

public class ShiftGear extends DBugCommand {
    Gear gear;
    
    public ShiftGear(Gear gear) {
	requires(Robot.elevator);
	this.gear = gear;
    }
    
    @Override
    protected void init() {
	Robot.elevator.shiftGear(gear);
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
