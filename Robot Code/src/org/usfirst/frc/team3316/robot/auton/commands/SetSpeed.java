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
public class SetSpeed extends DBugCommand {

	private double speed = 0, initYaw = 0;
	private PIDController pidSpeed, pidYaw;
	private double velocity = 0, ratio = 0;

	public SetSpeed(double speed) {
		this.speed = speed;

		initPIDSpeed();
		initPIDYaw();
		
		requires(Robot.chassis);
	}

	private void initPIDSpeed() {
		pidSpeed = new PIDController(0, 0, 0, new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) {
				return;
			}

			public double pidGet() {
				double currentSpeed = Robot.chassis.getSpeed();

				return currentSpeed;
			}

			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kRate;
			}
		}, new PIDOutput() {

			public void pidWrite(double output) {
				velocity = output;
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

		pidSpeed.setOutputRange(-1, 1);
		pidYaw.setOutputRange(-1, 1);

		pidSpeed.setPID((double) config.get("chassis_SetSpeed_PID_KP") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KI") / 1000,
				(double) config.get("chassis_SetSpeed_PID_KD") / 1000);
		pidYaw.setPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000);

		pidSpeed.setSetpoint(speed);
		pidYaw.setSetpoint(0.0);

		initYaw = Robot.chassis.getYaw();

		pidSpeed.enable();
		pidYaw.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.chassis.setMotors(getLeftVolatge(velocity, ratio), getRightVoltage(velocity, ratio));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void fin() {
		pidSpeed.reset();
		pidSpeed.disable();

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
