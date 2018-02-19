package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ElevatorMoveVoltage extends DBugCommand {
	private double voltage;

	public ElevatorMoveVoltage(double voltage) {
		requires(Robot.elevator);
		this.voltage = voltage;
	}

	@Override
	protected void init() {
		// Nothin' here boi
	}

	@Override
	protected void execute() {
		Robot.elevator.setMotors(this.voltage);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void fin() {
		// nothing much
	}

	@Override
	protected void interr() {
		this.fin();
	}
}
