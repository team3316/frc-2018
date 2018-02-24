package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.subsystems.Elevator;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

import edu.wpi.first.wpilibj.PIDController;

public class ElevatorToLevelBangbang extends DBugCommand {
	private double setpoint, tolerance, upVoltage, downVoltage;

	public ElevatorToLevelBangbang(Elevator.Level level) {
		requires(Robot.elevator);
		this.setpoint = level.getSetpoint();
	}

	public ElevatorToLevelBangbang(double setpoint) {
		requires(Robot.elevator);
		this.setpoint = setpoint;
	}

	@Override
	protected void init() {
		tolerance = (double) config.get("elevator_PID_Tolerance");
		if (setpoint > Robot.elevator.getPosition()) {
			downVoltage = (double) config.get("elevator_BangBang_DownVoltage");
			upVoltage = (double) config.get("elevator_BangBang_UpVoltage");
		} else {
			upVoltage = -(double) config.get("elevator_BangBang_DownVoltage");
			downVoltage = -(double) config.get("elevator_BangBang_UpVoltage");
		}
	}

	@Override
	protected void execute() {
		double elePos = Robot.elevator.getPosition();
		if (elePos < (setpoint - tolerance)) {
			Robot.elevator.setMotors(upVoltage);
		} else if (elePos > (setpoint + tolerance)) {
			Robot.elevator.setMotors(downVoltage);
		} else {
			Robot.elevator.setMotors(0.0);
		}
	}

	@Override
	protected boolean isFinished() {
		return Robot.elevator.getPosition() < setpoint + tolerance
				&& Robot.elevator.getPosition() > setpoint - tolerance;
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
