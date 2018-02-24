package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks.AxisType;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks.JoystickType;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
	protected static Config config = Robot.config;
	protected static DBugLogger logger = Robot.logger;
	protected double speedFactor;

	double left = 0, right = 0;
	double lowestValue = 0;
	JoystickType type;

	public Drive(JoystickType type) {
		requires(Robot.chassis);
		this.type = type;
	}

	protected void initialize() {
		lowestValue = (double) config.get("chassis_LowPassFilter_LowestValue");
	}

	protected double getAxis(AxisType axisType) {
		double factored = Robot.joysticks.getAxis(axisType, this.type) * speedFactor;
		return Utils.lowPassFilter(factored, lowestValue, 0.0);
	}

	protected void execute() {
		speedFactor = (double) config.get("chassis_SpeedFactor_Current");
		left = getAxis(AxisType.LeftY);
		right = getAxis(AxisType.RightY);
		Robot.chassis.setMotors(left, right);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.chassis.setMotors(0, 0);
	}

	protected void interrupted() {
		end();
	}
}
