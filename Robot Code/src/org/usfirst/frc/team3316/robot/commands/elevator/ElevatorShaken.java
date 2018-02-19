package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ElevatorShaken extends DBugCommand {
    private double setpoint, tolerance, upVoltage, downVoltage;

    public ElevatorShaken() {
	requires(Robot.elevator);
    }
    
    @Override
    protected void init() {
	this.setpoint = Robot.elevator.getPosition();
	tolerance = (double)config.get("elevator_Shaken_Tolerance");
	upVoltage = (double)config.get("elevator_Shaken_Voltage");
	downVoltage = -(double)config.get("elevator_Shaken_Voltage");
    }

    @Override
    protected void execute() {
	    double elePos = Robot.elevator.getPosition();
	    if (elePos < (setpoint - tolerance)) {
		Robot.elevator.setMotors(upVoltage);
	    }
	    else if (elePos > (setpoint + tolerance)) {
		Robot.elevator.setMotors(downVoltage);
	    }
	    else {
		Robot.elevator.setMotors(0.0);
	    }
    }

    @Override
    protected boolean isFinished() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    protected void fin() { }

    @Override
    protected void interr() {}

}
