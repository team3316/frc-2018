package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class MoveServo extends DBugCommand {
	double angle;
	long initialTime;
	boolean eleLimit, isWorking;

	public MoveServo(double angle, boolean eleLimit) {
		this.angle = angle;
		this.eleLimit = eleLimit;
	}

	@Override
	protected void init() {
		initialTime = System.currentTimeMillis();

		if (!eleLimit) {
			isWorking = true;
			Robot.holder.moveServo(angle);
		} else {
			if (Robot.elevator.getPosition() >= (double) config.get("servo_Elevator_Height_DownLimit")
					&& Robot.elevator.getPosition() <= (double) config.get("servo_Elevator_Height_UpLimit")) {
				isWorking = true;
				Robot.holder.moveServo(angle);
			} else {
				isWorking = false;
			}
		}
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		long currentTime = System.currentTimeMillis();
		return currentTime - initialTime >= (double) config.get("servo_Work_Time") || !isWorking;
	}

	@Override
	protected void fin() {
	}

	@Override
	protected void interr() {
	}

}
