package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class IntakeDirectionalRollIn extends DBugCommand {
    double vLeft;
    double vRight;
    boolean invertMotors;

    public IntakeDirectionalRollIn (boolean shouldInvert) {
	requires(Robot.rollerGripper);
	invertMotors = shouldInvert;
    }

    @Override
    protected void init() {
	Robot.rollerGripper.setMotors(0.0);
	vLeft = (double) Robot.config.get("intake_DirectionalRollIn_Left_Voltage");
	vRight = (double) Robot.config.get("intake_DirectionalRollIn_Right_Voltage");
    }

    @Override
    protected void execute() {
	// Pretty hacky solution but working prototype
	double l = !invertMotors ? vLeft : vRight;
	double r = !invertMotors ? vRight : vLeft;
	Robot.rollerGripper.setMotors(l, r);
    }

    @Override
    protected boolean isFinished() {
//	double leftTriggerValue = Robot.joysticks.joystickOperator.getTriggerAxis(Hand.kLeft);
//	return leftTriggerValue < 0.75;
	return false;
    }

    @Override
    protected void fin() {
	Robot.rollerGripper.setMotors(0.0);
    }

    @Override
    protected void interr() {
	fin();
    }

}
