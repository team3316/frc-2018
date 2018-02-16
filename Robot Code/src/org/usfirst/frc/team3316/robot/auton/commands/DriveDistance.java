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

	initPIDDrive();
	initPIDYaw();
    }

    private void initPIDDrive() {
	pidDrive = new PIDController(0, 0, 0, new PIDSource() {
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

	pidDrive.setOutputRange(-1, 1);
	pidYaw.setOutputRange(-1, 1);

	pidDrive.setAbsoluteTolerance((double) config.get("chassis_DriveDistance_PID_Tolerance"));

	pidDrive.setPID((double) config.get("chassis_DriveDistance_PID_DRIVE_KP") / 1000,
		(double) config.get("chassis_DriveDistance_PID_DRIVE_KI") / 1000,
		(double) config.get("chassis_DriveDistance_PID_DRIVE_KD") / 1000);
	pidYaw.setPID((double) config.get("chassis_DriveDistance_PID_YAW_KP") / 1000,
		(double) config.get("chassis_DriveDistance_PID_YAW_KI") / 1000,
		(double) config.get("chassis_DriveDistance_PID_YAW_KD") / 1000);

	pidDrive.setSetpoint(dist);
	pidYaw.setSetpoint(0.0);

	initDist = Robot.chassis.getDistance();
//	initYaw = Robot.chassis.getYaw();
	initYaw = 0.0;

	pidDrive.enable();
	pidYaw.enable();
	started = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.chassis.setMotors(getLeftVolatge(velocity, ratio), getRightVoltage(velocity, ratio));
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
