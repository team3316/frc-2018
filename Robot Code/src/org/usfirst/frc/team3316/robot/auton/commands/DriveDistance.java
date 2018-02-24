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

	private double dist, initDist = 0, initYaw = 0;
	private PIDController pidDrive, pidYaw;
	private double velocity = 0, ratio = 0;
	public boolean started = false;

	public DriveDistance(double dist) {
		requires(Robot.chassis);

		this.dist = dist;

		started = false;
		
		pidDrive = this.initPIDDrive();
		pidYaw = Robot.chassis.setYawPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000, 0);
	}

	private PIDController initPIDDrive() {
		return new PIDController(0, 0, 0, new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) {
				return;
			}

			public double pidGet() {
				double currentDist = Robot.chassis.getDistance() - initDist;

				return currentDist;
			}

			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput() {

			public void pidWrite(double output) {
				velocity = output;
			}
		}, 0.02);
	}

	// Called just before this Command runs the first time
	protected void init() {
		Robot.chassis.setBrake(true);

		pidDrive.setOutputRange(-1, 1);
		pidYaw.setOutputRange(-1, 1);

		pidDrive.setAbsoluteTolerance((double) config.get("chassis_DriveDistance_PID_Tolerance"));

		pidDrive.setPID((double) config.get("chassis_DriveDistance_PID_DRIVE_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_DRIVE_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_DRIVE_KD") / 1000);

		pidDrive.setSetpoint(dist);
		pidYaw.setSetpoint(0.0);

		// REMARK: Need to reset yaw _before_ this command initializes
		initDist = Robot.chassis.getDistance();
		initYaw = Robot.chassis.getYaw();

		pidDrive.enable();
		pidYaw.enable();
		started = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double leftVoltage = Robot.chassis.getLeftVolatge(velocity, ratio);
		double rightVoltage = Robot.chassis.getRightVoltage(velocity, ratio);
		Robot.chassis.setMotors(leftVoltage, rightVoltage);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pidDrive.onTarget();
	}

	// Called once after isFinished returns true
	protected void fin() {
		pidDrive.reset();
		pidDrive.disable();

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
}
