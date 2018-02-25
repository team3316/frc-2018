package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks.AxisType;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks.JoystickType;
import org.usfirst.frc.team3316.robot.utils.PIDControllers;

import edu.wpi.first.wpilibj.PIDController;

public class DriveOneAxis extends DBugCommand {

	private JoystickType type;
	private double speedFactor;
	private PIDController pidYaw;

	public DriveOneAxis(JoystickType type) {
		requires(Robot.chassis);
		this.type = type;
	}

	@Override
	protected void init() {
		speedFactor = (double) config.get("chassis_SpeedFactor_Current");

		pidYaw = PIDControllers.getYawPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000, 0);
		pidYaw.setOutputRange(-1, 1);
		pidYaw.setSetpoint(0.0);

		pidYaw.enable();
	}

	@Override
	protected void execute() {
		double axisValue = Robot.joysticks.getAxis(AxisType.RightY, this.type);
		Robot.chassis.currentLeftV = axisValue * speedFactor;
		Robot.chassis.currentRightV = axisValue * speedFactor;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void fin() {
		pidYaw.reset();
		pidYaw.disable();

		Robot.chassis.setMotors(0, 0);
	}

	@Override
	protected void interr() {
		fin();
	}
}
