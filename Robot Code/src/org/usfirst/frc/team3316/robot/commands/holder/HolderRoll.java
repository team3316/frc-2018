package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class HolderRoll extends DBugCommand {

	double voltage;
	HolderRollType type;

	public HolderRoll(HolderRollType type) {
		if (type != null) {
			requires(Robot.holder);
			this.type = type;
		} else {
			logger.severe("No holder roll type!!!");
		}
	}

	@Override
	protected void init() {
		Robot.holder.setMotor(0.0);
		voltage = this.type.getVoltage();
	}

	@Override
	protected void execute() {
		Robot.holder.setMotor(this.type.getVoltage());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void fin() {
		Robot.holder.setMotor(0.0);
	}

	@Override
	protected void interr() {
		this.fin();
	}
}
