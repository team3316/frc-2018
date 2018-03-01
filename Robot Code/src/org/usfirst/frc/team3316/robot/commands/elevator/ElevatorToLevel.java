package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

public class ElevatorToLevel extends ElevatorMovingCommand {
	public ElevatorToLevel(Level level) {
		requires(Robot.elevator);
		this.setpoint = level.getSetpoint();
	}

	protected void setParameters() {
		tolerance = (double) config.get("elevator_PID_Tolerance");

		boolean lessThanSetpoint = setpoint > Robot.elevator.getPosition();
		double configDownVoltage = (double) config.get("elevator_BangBang_DownVoltage");
		double configUpVoltage = (double) config.get("elevator_BangBang_UpVoltage");

		/*
		 * Explanation: when the elevator is lower than the setpoint, we want it to
		 * operate usually - thus setting the down and up voltages to be the regular
		 * ones. But, when we are higher than the setpoint, we would want to go down to
		 * it in the same speed, thus setting the down speed to be the inverted up
		 * speed.
		 */
		downVoltage = lessThanSetpoint ? configDownVoltage : -configUpVoltage;
		upVoltage = lessThanSetpoint ? configUpVoltage : -configDownVoltage;
	}
}
