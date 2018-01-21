package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignRobot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AlignToCube extends DBugCommand {
	private static PIDController pid;
	private static double setPoint;
	private double lastTowerAngle;
	private static double tolerance;

	public AlignToCube() {
		requires(Robot.chassis);

		pid = new PIDController(0, 0, 0, new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			public double pidGet() {
				return Robot.chassis.getYaw();
			}

			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput() {
			public void pidWrite(double output) {
				double velocity = output;
				if (AlignRobot.isObjectDetected()) {
					Robot.chassis.setMotors(velocity, -velocity);
				}
			}
		});

		pid.setOutputRange(-1, 1);
	}

	protected void init() {
		setPoint = 0.0;
		lastTowerAngle = Double.MAX_VALUE;
		tolerance = (double) config.get("chassis_TurnByGyro_PID_Tolerance");

		pid.setAbsoluteTolerance(tolerance);

		pid.setSetpoint(setPoint);

		pid.enable();
	}

	protected void execute() {
		pid.setPID((double) config.get("chassis_TurnByGyro_PID_KP") / 1000,
				(double) config.get("chassis_TurnByGyro_PID_KI") / 1000,
				(double) config.get("chassis_TurnByGyro_PID_KD") / 1000);

		double currentAngle = Robot.chassis.getYaw();

		/*
		 * This code is with setpoint set by the vision
		 */
		if (AlignRobot.isObjectDetected()) {
			double towerAngle = AlignRobot.getCubeAngle();

			if (towerAngle != lastTowerAngle && towerAngle != 3316.0) {

				setPoint = towerAngle + currentAngle;

				lastTowerAngle = towerAngle;

				pid.setSetpoint(setPoint);
			}
		} else {
			pid.reset();
			pid.enable();

			Robot.chassis.setMotors(0.0, 0.0);
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void fin() {
		pid.reset();

		Robot.chassis.setMotors(0.0, 0.0);
	}

	protected void interr() {
		fin();
	}
}
