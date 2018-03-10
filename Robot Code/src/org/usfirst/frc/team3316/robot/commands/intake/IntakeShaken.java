package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class IntakeShaken extends DBugCommand {
	private double leftVoltage, rightVoltage, stepTime, stopTime;
	private long currentTime, diff1, diff2;
	private boolean isInverted;

	public IntakeShaken() {
		requires(Robot.intake);
	}

	@Override
	protected void init() {
		this.leftVoltage = IntakeRollType.Directional.getLeftVoltage();
		this.rightVoltage = IntakeRollType.Directional.getRightVoltage();
		this.stepTime = (double) Robot.config.get("intake_shaken_stepTime");
		this.stopTime = (double) Robot.config.get("intake_shaken_stopTime");
		this.currentTime = System.currentTimeMillis();
		this.isInverted = false;
		this.diff1 = 0;
		this.diff2 = 0;
	}

	@Override
	protected void execute() {
		long time = System.currentTimeMillis();
		this.diff1 = time - this.currentTime;
		this.diff2 = time - this.currentTime;

		if (diff1 <= this.stepTime) {
			Robot.intake.setMotors(this.leftVoltage, this.rightVoltage);
		} else if (diff2 <= this.stepTime + this.stopTime) {
			this.diff1 = 0;
			Robot.intake.setMotors(this.leftVoltage, 0.0);
		} else {
			this.diff1 = 0;
			this.diff2 = 0;
			this.currentTime = time;
		}
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
