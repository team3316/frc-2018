package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.Utils;

public abstract class ElevatorMovingCommand extends DBugCommand {
	protected double setpoint, tolerance, upVoltage, downVoltage;

	public ElevatorMovingCommand() {
		requires(Robot.elevator);
	}

	protected abstract void setParameters();
	protected abstract boolean isShaken();

	@Override
	protected void init() {
		this.setParameters();
	}

	@Override
	protected void execute() {
		Robot.elevator.setBangbangVoltage(setpoint, tolerance, upVoltage, downVoltage);
	}

	@Override
	protected boolean isFinished() {
		if (!this.isShaken()) {
			logger.info(Utils.isInNeighborhood(Robot.elevator.getPosition(), setpoint, tolerance) ? "in neighborhood" : "out of neighborhood");
			return Utils.isInNeighborhood(Robot.elevator.getPosition(), setpoint, tolerance);
		} else {
			return false;
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
