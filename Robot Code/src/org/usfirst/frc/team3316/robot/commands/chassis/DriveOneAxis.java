package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveOneAxis extends DBugCommand {

    private Joystick joystickOperator;
    private boolean invertY;
    private double deadBand = 0.0, speedFactor, initYaw = 0, ratio = 0;
    private PIDController pidYaw;

    public DriveOneAxis() {
	requires(Robot.chassis);
	
	joystickOperator = Robot.joysticks.joystickOperator;
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

    protected double getY() {
	updateConfigVariables();
	double y = deadBand(joystickOperator.getRawAxis((int) config.get("chassis_Joystick_Right_Axis")));
	if (invertY) {
	    return -y;
	}
	return y;
    }

    @Override
    protected void execute() {
	// TODO Auto-generated method stub
	speedFactor = (double) config.get("chassis_SpeedFactor_Current");

	double leftVoltage = getLeftVolatge(getY() * speedFactor, ratio);
	double rightVoltage = getRightVoltage(getY() * speedFactor, ratio);

	Robot.chassis.setMotors(leftVoltage, rightVoltage);
    }

    @Override
    protected boolean isFinished() {
	// TODO Auto-generated method stub
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

    // Utils

    /*
     * Here we call the get method of the config every execute because we want
     * the variables to update without needing to cancel the commands.
     */
    private void updateConfigVariables() {
	deadBand = (double) config.get("chassis_TankDrive_DeadBand");

	invertY = (boolean) config.get("chassis_TankDrive_InvertY");
    }

    private double deadBand(double x) {
	if (Math.abs(x) < deadBand) {
	    return 0;
	}
	return x;
    }

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
