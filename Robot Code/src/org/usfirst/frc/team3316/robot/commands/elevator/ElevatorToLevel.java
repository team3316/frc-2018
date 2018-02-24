package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.subsystems.Elevator;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

import edu.wpi.first.wpilibj.PIDController;

public class ElevatorToLevel extends DBugCommand {
	private PIDController pid;
	private double setpoint;

	public ElevatorToLevel(Elevator.Level level) {
		requires(Robot.elevator);
		this.setpoint = level.getSetpoint();
	}

	public ElevatorToLevel(double setpoint) {
		requires(Robot.elevator);
		this.setpoint = setpoint;
	}

	@Override
	protected void init() {
		this.pid = Robot.elevator.getPIDControllerElevator((double) config.get("elevator_PID_KP") / 1000.0,
				(double) config.get("elevator_PID_KI") * (0.2 / setpoint) / 1000.0,
				(double) config.get("elevator_PID_KD") * (setpoint / 0.2) / 1000.0, 0.0, setpoint);
		this.pid.setSetpoint(setpoint);
		this.pid.enable();
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return pid.onTarget();
	}

	@Override
	protected void fin() {
		this.pid.disable();
	}

	@Override
	protected void interr() {
		fin();
	}
}
