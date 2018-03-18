package org.usfirst.frc.team3316.robot.commands.elevator;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

public abstract class ElevatorMoveToEdge extends DBugCommand {
	private Level level;
	private double v;

	public ElevatorMoveToEdge() {
		requires(Robot.elevator);
	}
	
	abstract Level setLevel();
	abstract double setVoltage();
	
	abstract void moveInit();
	abstract void running();

	@Override
	protected void init() {
		this.level = setLevel();
		
		moveInit();
	}

	@Override
	protected void execute() {
		running();
	
		v = setVoltage();	
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
