package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks.AxisType;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks.JoystickType;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveOneAxis extends DBugCommand {

	private JoystickType type;
	private double speedFactor, initYaw = 0, ratio = 0;
	private PIDController pidYaw;

	public DriveOneAxis(JoystickType type) {
		requires(Robot.chassis);
		this.type = type;
	}

	private void initPIDYaw() {
		pidYaw = new PIDController(0, 0, 0, new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) {
				return;
			}

			public double pidGet() {
				double currentYaw = Robot.chassis.getYaw() - initYaw;

				return currentYaw;
			}

			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput() {

			public void pidWrite(double output) {
				ratio = -output;
			}
		}, 0.02);
	}

	@Override
	protected void init() {
		initPIDYaw();

		pidYaw.setOutputRange(-1, 1);

		pidYaw.setPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000);

		pidYaw.setSetpoint(0.0);

		initYaw = Robot.chassis.getYaw();

		pidYaw.enable();
	}

	@Override
	protected void execute() {
		speedFactor = (double) config.get("chassis_SpeedFactor_Current");

		double axisValue = Robot.joysticks.getAxis(AxisType.RightY, this.type);
		double leftVoltage = Robot.chassis.getLeftVolatge(axisValue * speedFactor, ratio);
		double rightVoltage = Robot.chassis.getRightVoltage(axisValue * speedFactor, ratio);

		Robot.chassis.setMotors(leftVoltage, rightVoltage);
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
