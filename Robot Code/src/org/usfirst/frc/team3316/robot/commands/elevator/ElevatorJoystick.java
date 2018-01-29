package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.Joystick;

public class ElevatorJoystick extends DBugCommand {
	private Joystick joystick;

	public ElevatorJoystick() {
		requires(Robot.elevator);
		joystick = Robot.joysticks.joystickElevator;
	}

	@Override
	protected void init() {
		// Nothin' here
	}

	@Override
	protected void execute() {
		double joystickValue = joystick.getRawAxis((int) Robot.config.get("elevator_Joystick_Axis"));
		Robot.elevator.setMotors(joystickValue);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void fin() {
		Robot.elevator.setMotors(0.0);
	}

	@Override
	protected void interr() {
		this.fin();
	}
}