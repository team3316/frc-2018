package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.PIDControllers;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * A command for driving a given amount of meters forward.
 */
public class DriveDistance extends DBugCommand {

	private double distance;
	public boolean started = false;
	double initialDistance;
	PIDController pidDrive;

	public DriveDistance(double dist) {
		distance = dist;
		started = false;
	}

	// Called just before this Command runs the first time
	protected void init() {
		Robot.chassis.setBrake(true);
		initialDistance = Robot.chassis.getDistance();

		pidDrive = new PIDController((double) config.get("chassis_DriveDistance_PID_DRIVE_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_DRIVE_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_DRIVE_KD") / 1000, new PIDSource() {
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

						Robot.chassis.setMotors(velocity, velocity);
					}
				}, 0.02);

		pidDrive.setOutputRange(-1, 1);

		pidDrive.setAbsoluteTolerance((double) config.get("chassis_DriveDistance_PID_Tolerance"));

		pidDrive.setSetpoint(distance);

		pidDrive.enable();
		started = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Nothing here
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pidDrive.onTarget();
	}

	// Called once after isFinished returns true
	protected void fin() {
		pidDrive.reset();
		pidDrive.disable();

		Robot.chassis.setMotors(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interr() {
		fin();
	}
}
