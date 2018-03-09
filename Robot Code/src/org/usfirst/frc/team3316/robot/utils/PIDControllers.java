package org.usfirst.frc.team3316.robot.utils;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.emptyCommand;
import org.usfirst.frc.team3316.robot.subsystems.DBugSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDOutput;

/*
 * REMARK: This class is defined as a subsystem so that commands who use it will
 * need to require it, making it unavailable to other commands at the same time.
 */
public class PIDControllers extends DBugSubsystem {
	public static PIDController getSpeedPID(boolean leftSide, double Kp, double Ki, double Kd, double Kf) {
		PIDController pid = new PIDController(Kp, Ki, Kd, Kf, new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				return;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kRate;
			}

			@Override
			public double pidGet() {
				if (leftSide) {
					return Robot.chassis.getLeftSpeed();
				}

				return Robot.chassis.getRightSpeed();
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				if (leftSide) {
					Robot.chassis.currentLeftV = output;
				} else {
					Robot.chassis.currentRightV = output;
				}
			}
		}, 0.02);

		return pid;
	}

	public static PIDController getYawPID(double Kp, double Ki, double Kd, double Kf) {
		double initialYaw = Robot.chassis.getYaw();

		PIDController pid = new PIDController(Kp, Ki, Kd, Kf, new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				return;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				double currentYaw = Robot.chassis.getYaw();
				return currentYaw - initialYaw;
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				double ratio = -output;
				Robot.chassis.currentRatio = ratio; // This is required for the distance PID

				double leftVoltage = Utils.calculateLeftVoltage(Robot.chassis.currentLeftV, ratio);
				double rightVoltage = Utils.calculateRightVoltage(Robot.chassis.currentRightV, ratio);
				Robot.chassis.setMotors(leftVoltage, rightVoltage);
			}
		}, 0.02);

		return pid;
	}

	public static PIDController getDrivePID(double kP, double kI, double kD, double kF) {
		double initialDistance = Robot.chassis.getDistance();

		PIDController pid = new PIDController(kP, kI, kD, kF, new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) {
				return;
			}

			public double pidGet() {
				double currentDist = Robot.chassis.getDistance() - initialDistance;

				return currentDist;
			}

			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput() {
			public void pidWrite(double output) {
				double velocity = output;
				double leftVoltage = Utils.calculateLeftVoltage(velocity, Robot.chassis.currentRatio);
				double rightVoltage = Utils.calculateRightVoltage(velocity, Robot.chassis.currentRatio);
				Robot.chassis.setMotors(leftVoltage, rightVoltage);
			}
		}, 0.02);

		return pid;
	}

	@Override
	public void initDefaultCommand() {
		// Do nothing here
	}
}
