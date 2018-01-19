package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class IntakeStop extends DBugCommand {
    double v;

    public IntakeStop() {
	requires(Robot.rollerGripper);
    }

    @Override
    protected void init() {
	// Nothin here
    }

    @Override
    protected void execute() {
	Robot.rollerGripper.setMotors(0.0);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void fin() {
	// nothin here
    }

    @Override
    protected void interr() {
	fin();
    }

}
