package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeDirectionalRollIn;
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
//	setDefaultCommand(new IntakeDirectionalRollIn());
    }

    public void setMotors (double vLeft, double vRight) {
	this.leftSC.setMotor(-vLeft);
	this.rightSC.setMotor(vRight);
    }

    public void setMotors (double v) {
	this.leftSC.setMotor(v);
	this.rightSC.setMotor(-v);
    }

    public void changeGripperDirection () {
	double v = this.leftSC.getVoltage();
	this.leftSC.setMotor(-v);
    }
}
