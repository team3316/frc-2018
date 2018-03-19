package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

public class ElevatorMoveVoltage extends DBugCommand {
	private double v;
	private boolean stoppable;
	
	public ElevatorMoveVoltage(double v, boolean stoppable) {
		this.v = v;
		this.stoppable = stoppable;
	}
	
	@Override
	protected void init() {}

	@Override
	protected void execute() {
		Robot.elevator.setMotors(v);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		if (!stoppable) {
			return false;
		}
		else {
			return Robot.elevator.getLevel() == Level.Bottom;
		}
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
