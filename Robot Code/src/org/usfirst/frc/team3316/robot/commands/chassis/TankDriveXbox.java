package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;

public class TankDriveXbox extends Drive {
    // TODO: Add commenting

    protected static Joystick joystickLeft, joystickRight, joystickOperator;

    static boolean invertY, invertX;

    static double deadBand = 0.0, speedFactor;

    public TankDriveXbox() {
	super();

	joystickLeft = Robot.joysticks.joystickLeft;
	joystickRight = Robot.joysticks.joystickRight;
	joystickOperator = Robot.joysticks.joystickOperator;
    }

    protected void set() {
	speedFactor = (double) config.get("chassis_SpeedFactor_Current");

	right = getLeftY() * speedFactor;
	left = getRightY() * speedFactor;
    }

    protected static double getLeftY() {
	updateConfigVariables();
	double y = deadBand(joystickOperator.getRawAxis((int) config.get("chassis_Joystick_Left_Axis")));
	if (invertY) {
	    return -y;
	}
	return y;
    }

    protected static double getLeftX() {
	updateConfigVariables();
	double x = deadBand(joystickLeft.getX());
	if (invertX) {
	    return -x;
	}
	return x;
    }

    protected static double getRightY() {
	updateConfigVariables();
	double y = deadBand(joystickOperator.getRawAxis((int) config.get("chassis_Joystick_Right_Axis")));
	if (invertY) {
	    return -y;
	}
	return y;
    }

    protected static double getRightX() {
	updateConfigVariables();
	double x = deadBand(joystickRight.getX());
	if (invertX) {
	    return -x;
	}
	return x;
    }

    private static double deadBand(double x) {
	if (Math.abs(x) < deadBand) {
	    return 0;
	}
	return x;
    }

    /*
     * Here we call the get method of the config every execute because we want
     * the variables to update without needing to cancel the commands.
     */
    private static void updateConfigVariables() {
	deadBand = (double) config.get("chassis_TankDrive_DeadBand");

	invertX = (boolean) config.get("chassis_TankDrive_InvertX");
	invertY = (boolean) config.get("chassis_TankDrive_InvertY");
    }
}
