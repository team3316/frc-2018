package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.Utils;

import com.sun.scenario.effect.Offset;

public class TurnByGyroBB extends DBugCommand {
	private double upVoltage, downVoltage, angle, setpoint, tolerance, initYaw, configOffset;
	private boolean fromConfig;

	public TurnByGyroBB(double setpoint) {
			this.setpoint = setpoint;
			fromConfig = false;
	}
	
	public TurnByGyroBB(boolean fromConfig, double offset) {
		this.fromConfig = true;
		configOffset = offset;
	}

	@Override
	protected void init() {
		if (fromConfig) {
			this.setpoint = -(Robot.chassis.getYaw() + configOffset);
		}
		
		Robot.chassis.resetYaw();
		
		initYaw = Robot.chassis.getYaw();
		Robot.chassis.setBrake(true);
		
		this.angle = Robot.chassis.getYaw() - initYaw;
		this.tolerance = (double) config.get("chassis_TurnByGyro_PID_Tolerance");

		double configUpVoltage = (double) config.get("chassis_TurnByGyro_UpVoltage");
		double configDownVoltage = (double) config.get("chassis_TurnByGyro_DownVoltage");

		this.upVoltage = this.setpoint < 0 ? configUpVoltage : configDownVoltage;
		this.downVoltage = this.setpoint < 0 ? configDownVoltage : configUpVoltage;
		
		config.add("chassis_TurnByGyro_CurrentInitYaw", initYaw);
	}

	@Override
	protected void execute() {
		this.angle = (Robot.chassis.getYaw() - initYaw);
		if (this.angle > (this.setpoint + this.tolerance)) {
			Robot.chassis.setMotors(this.downVoltage, -this.downVoltage);
			logger.info("V:" + downVoltage);
		} else if (this.angle < (this.setpoint - this.tolerance)) {
			Robot.chassis.setMotors(-this.upVoltage, this.upVoltage);
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
		fin();
	}

}
