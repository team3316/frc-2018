package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRoll;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRollType;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRoll;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollType;

public class RollInShaken extends DBugCommand {
	private long lastTime, difference;
	private HolderRoll holderRollIn;
	private IntakeRoll intakeRollIn, intakeStop;
	private boolean isRollInRunning;

	public RollInShaken() {
		requires(Robot.intake);
		requires(Robot.holder);
	}

	@Override
	protected void init() {
		this.difference = 0;
		this.lastTime = System.currentTimeMillis();
		this.isRollInRunning = true;

		this.holderRollIn = new HolderRoll(HolderRollType.RollIn);
		this.intakeRollIn = new IntakeRoll(IntakeRollType.RollIn);
		this.intakeStop = new IntakeRoll(IntakeRollType.Stop);

		this.holderRollIn.start();
		this.intakeRollIn.start();
	}

	@Override
	protected void execute() { 
		long time = System.currentTimeMillis();
		this.difference = time - this.lastTime;

		if (this.difference > 1000 && !this.isRollInRunning) {
			this.intakeRollIn.start();
			this.lastTime = time;
			this.isRollInRunning = true;
		} else if (this.difference > 1000 && this.isRollInRunning) {
			this.intakeStop.start();
			this.lastTime = time;
			this.difference = 0;
			this.isRollInRunning = false;
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void fin() {
		// nada
	}

	@Override
	protected void interr() {
		// TODO Auto-generated method stub
	}
}
