package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.Joystick;

public class IntakeRoll extends DBugCommand {
	double leftVoltage, rightVoltage;
	IntakeRollType type;
	protected static Joystick joystickLeft, joystickRight, joystickOperator;

	public IntakeRoll(IntakeRollType type) {
		if (type != null) {
			requires(Robot.intake);
			this.type = type;
		} else {
			logger.severe("No intake roll type!!!");
		}
	}

	@Override
	protected void init() {
		Robot.intake.setMotors(0.0);
		leftVoltage = this.type.getLeftVoltage();
		rightVoltage = this.type.getRightVoltage();
		joystickLeft = Robot.joysticks.joystickLeft;
		joystickRight = Robot.joysticks.joystickRight;
		joystickOperator = Robot.joysticks.joystickOperator;
	}

	@Override
	protected void execute() {
		//Robot.intake.setMotors(joystickOperator.getRawAxis(4), joystickOperator.getRawAxis(0));
	    	Robot.intake.setMotors(-0.6, -0.3);
	}


	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void fin() {
		Robot.intake.setMotors(0.0);
	}

	@Override
	protected void interr() {
		this.fin();
	}

}
