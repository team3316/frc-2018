package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollerGripper extends DBugSubsystem {
    // Actuators
    private DBugSpeedController leftSC, rightSC;

    public RollerGripper () {
	Robot.actuators.RollerGripperActuators();
	leftSC = Robot.actuators.rollerGripperLeft;
	rightSC = Robot.actuators.rollerGripperRight;
    }

    @Override
    public void initDefaultCommand() {
	// TODO Auto-generated method stub
    }

    public void setMotors (double v) {
	this.leftSC.setMotor(v);
	this.rightSC.setMotor(-v);
    }
}
