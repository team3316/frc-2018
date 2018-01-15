package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class IntakeRollIn extends DBugCommand {
    double v;

    public IntakeRollIn() {
	requires(Robot.rollerGripper);
    }

    @Override
    protected void init() {
	Robot.rollerGripper.setMotors(0.0);
	v = (double) Robot.config.get("intake_RollIn_Voltage");
    }

    @Override
    protected void execute() {
	Robot.rollerGripper.setMotors(v);
    }

    @Override
    protected boolean isFinished() {
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
