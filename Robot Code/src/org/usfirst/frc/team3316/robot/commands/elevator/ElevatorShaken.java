package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;

public class ElevatorShaken extends ElevatorMovingCommand {
	protected void setParameters() {
		this.setpoint = Robot.elevator.getPosition();
		tolerance = (double) config.get("elevator_Shaken_Tolerance");
		upVoltage = (double) config.get("elevator_Shaken_UpVoltage");
		downVoltage = (double) config.get("elevator_Shaken_DownVoltage");
	}

	protected boolean isShaken() {
		return true;
	}
}
