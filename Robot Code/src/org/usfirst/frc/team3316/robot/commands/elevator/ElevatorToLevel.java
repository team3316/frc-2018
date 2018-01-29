package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.subsystems.Elevator;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

import edu.wpi.first.wpilibj.PIDController;

public class ElevatorToLevel extends DBugCommand {
	private Elevator.Level level;
	private PIDController pid;

	public ElevatorToLevel(Elevator.Level level) {
		requires(Robot.elevator);
		this.level = level;
	}

	@Override
	protected void init() {
		this.pid = Robot.elevator.getPIDController((double) config.get("elevator_PID_KP"),
												  (double) config.get("elevator_PID_KI"),
												  (double) config.get("elevator_PID_KD"),
												  level);
		this.pid.enable();
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		Elevator.Level currentLevel = Robot.elevator.getLevel();
		boolean isInCorrectLevel = currentLevel == this.level;
		return isInCorrectLevel;
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
