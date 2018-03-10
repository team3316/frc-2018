package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.Utils;

public class TurnByGyroBB extends DBugCommand {
	private double upVoltage, downVoltage, angle, setpoint, tolerance;

	public TurnByGyroBB(double setpoint) {
		requires(Robot.chassis);
		this.setpoint = setpoint;
	}

	@Override
	protected void init() {
		Robot.chassis.resetYaw();
		Robot.chassis.setBrake(true);
		
		this.angle = Robot.chassis.getYaw();
		this.tolerance = (double) config.get("chassis_TurnByGyro_PID_Tolerance");

		double configUpVoltage = (double) config.get("chassis_TurnByGyro_UpVoltage");
		double configDownVoltage = (double) config.get("chassis_TurnByGyro_DownVoltage");

		this.upVoltage = this.setpoint < 0 ? configUpVoltage : configDownVoltage;
		this.downVoltage = this.setpoint < 0 ? configDownVoltage : configUpVoltage;
	}

	@Override
	protected void execute() {
		this.angle = Robot.chassis.getYaw();
		if (this.angle > (this.setpoint + this.tolerance)) {
			Robot.chassis.setMotors(-this.downVoltage, this.downVoltage);
			logger.info("V:" + downVoltage);
		} else if (this.angle < (this.setpoint - this.tolerance)) {
			Robot.chassis.setMotors(this.upVoltage, -this.upVoltage);
			logger.info("V:" + upVoltage);
		}
	}

	@Override
	protected boolean isFinished() {
		return Utils.isInNeighborhood(this.angle, this.setpoint, this.tolerance);
	}

	@Override
	protected void fin() {
		Robot.chassis.setMotors(0.0, 0.0);
	}

	@Override
	protected void interr() {
		Robot.chassis.setMotors(0.0, 0.0);
	}

}
