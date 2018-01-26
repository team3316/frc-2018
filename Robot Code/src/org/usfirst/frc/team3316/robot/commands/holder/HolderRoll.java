package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class HolderRoll extends DBugCommand {
	double leftVoltage, rightVoltage;
	HolderRollType type;

	public HolderRoll(HolderRollType type) {
		if (type != null) {
			requires(Robot.intake);
			this.type = type;
		} else {
			logger.severe("No intake roll type!!!");
		}
	}

	@Override
	protected void init() {
		Robot.intake.setMotors(0.0);
		leftVoltage = this.type.getLeftVoltage();
		rightVoltage = this.type.getRightVoltage();
	}

	@Override
	protected void execute() {
		Robot.holder.setMotors(leftVoltage);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void fin() {
		Robot.intake.setMotors(0.0);
	}

	@Override
	protected void interr() {
		this.fin();
	}

}
