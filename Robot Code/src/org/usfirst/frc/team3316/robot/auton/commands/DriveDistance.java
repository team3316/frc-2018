package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class DriveDistance extends DBugCommand {

	private double distRight, distLeft, initDistanceRight = 0, initDistanceLeft = 0, initYaw = 0;
	private PIDController pidRight, pidLeft, pidYaw;
	private double velocityRight = 0, velocityLeft = 0, ratio = 0;
	public boolean started = false;

	public DriveDistance(double distanceRight, double distanceLeft) {
		this.distRight = distanceRight;
		this.distLeft = distanceLeft;

		started = false;

		initPIDRight();
		initPIDLeft();
		initPIDYaw();
	}

	private void initPIDRight() {
		pidRight = new PIDController(0, 0, 0, new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) {
				return;
			}

			public double pidGet() {
				double currentDist = Robot.chassis.getRightDistance() - initDistanceRight;

				return currentDist;
			}

			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput() {

			public void pidWrite(double output) {
				velocityRight = output;
			}
		}, 0.02);
	}

	private void initPIDLeft() {
		pidLeft = new PIDController(0, 0, 0, new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) {
				return;
			}

			public double pidGet() {
				double currentDist = Robot.chassis.getLeftDistance() - initDistanceLeft;

				return currentDist;
			}

			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput() {

			public void pidWrite(double output) {
				velocityLeft = output;
			}
		}, 0.02);
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

	// Called just before this Command runs the first time
	protected void init() {
		Robot.chassis.setBrake(true);

		pidRight.setOutputRange(-1, 1);
		pidLeft.setOutputRange(-1, 1);
		pidYaw.setOutputRange(-1, 1);

		pidRight.setAbsoluteTolerance((double) config.get("chassis_DriveDistance_PID_Tolerance"));
		pidLeft.setAbsoluteTolerance((double) config.get("chassis_DriveDistance_PID_Tolerance"));

		pidRight.setPID((double) config.get("chassis_DriveDistance_PID_RIGHT_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_RIGHT_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_RIGHT_KD") / 1000);
		pidLeft.setPID((double) config.get("chassis_DriveDistance_PID_LEFT_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_LEFT_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_LEFT_KD") / 1000);
		pidYaw.setPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000);

		pidRight.setSetpoint(distRight);
		pidLeft.setSetpoint(distLeft);
		pidYaw.setSetpoint(0.0);

		initDistanceRight = Robot.chassis.getRightDistance();
		initDistanceLeft = Robot.chassis.getLeftDistance();
		initYaw = Robot.chassis.getYaw();

		pidRight.enable();
		pidLeft.enable();
		pidYaw.enable();
		started = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.chassis.setMotors(getLeftVolatge(velocityLeft, ratio), getRightVoltage(velocityRight, ratio));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pidRight.onTarget() && pidLeft.onTarget();
	}

	// Called once after isFinished returns true
	protected void fin() {
		pidRight.reset();
		pidRight.disable();

		pidLeft.reset();
		pidLeft.disable();

		pidYaw.reset();
		pidYaw.disable();

		Robot.chassis.setMotors(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interr() {
		logger.info("DriveDistance interrupted");
		fin();
	}

	// Utils
	private double getLeftVolatge(double v, double r) {
		if (v > 0) { // Driving forward
			if (r > 0) { // Swerving right
				return v * (-r + 1);
			} else { // Swerving left
				return v;
			}
		} else { // Driving back
			if (r < 0) { // Swerving right
				return v * (r + 1);
			} else { // Swerving left
				return v;
			}
		}
	}

	private double getRightVoltage(double v, double r) {
		if (v > 0) { // Driving forward
			if (r < 0) { // Swerving left
				return v * (r + 1);
			} else { // Swerving right
				return v;
			}
		} else { // Driving back
			if (r > 0) { // Swerving left
				return v * (-r + 1);
			} else { // Swerving right
				return v;
			}
		}
	}
}
