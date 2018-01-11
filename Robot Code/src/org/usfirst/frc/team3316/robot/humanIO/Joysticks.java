/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeEmptyCommand;
import org.usfirst.frc.team3316.robot.commands.chassis.BrakeMode;
import org.usfirst.frc.team3316.robot.commands.chassis.CoastMode;
import org.usfirst.frc.team3316.robot.commands.chassis.DriveOneAxis;
import org.usfirst.frc.team3316.robot.commands.chassis.ChassisHighSpeed;
import org.usfirst.frc.team3316.robot.commands.chassis.ChassisLowSpeed;
import org.usfirst.frc.team3316.robot.commands.climbing.ClimbingStop;
import org.usfirst.frc.team3316.robot.commands.climbing.ClimbingUpFast;
import org.usfirst.frc.team3316.robot.commands.climbing.ClimbingUpSlow;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.CollectGear;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class Joysticks {
    /*
     * Defines a button in a gamepad POV for an array of angles
     */
    private class POVButton extends Button {
	Joystick m_joystick;
	int m_deg;

	public POVButton(Joystick joystick, int deg) {
	    m_joystick = joystick;
	    m_deg = deg;
	}

	public boolean get() {
	    if (m_joystick.getPOV() == m_deg) {
		return true;
	    }
	    return false;
	}
    }

    Config config = Robot.config;
    DBugLogger logger = Robot.logger;

    public Joystick joystickLeft, joystickRight, joystickOperator;
    public DBugJoystickButton lowerSpeedBtn, higherSpeedBtn;
    public DBugJoystickDigitalAxis DriveOneAxisAxisButton1;

    /**
     * Initializes the joysticks.
     */
    public Joysticks() {
	joystickLeft = new Joystick((int) Robot.config.get("JOYSTICK_LEFT"));
	joystickRight = new Joystick((int) Robot.config.get("JOYSTICK_RIGHT"));
	joystickOperator = new Joystick((int) Robot.config.get("JOYSTICK_OPERATOR"));
    }

    /**
     * Initializes the joystick buttons. This is done separately because they
     * usually require the subsystems to be already instantiated.
     */
    public void initButtons() {
	/*
	 * Chassis
	 */
	DBugJoystickButton toggleChassisBrakeMode = new DBugJoystickButton(joystickOperator,
		"button_Chassis_Break_Toggle");
	toggleChassisBrakeMode.whenPressed(new DBugToggleCommand(new BrakeMode(), new CoastMode()));

	DBugJoystickButton toggleChassisSpeed = new DBugJoystickButton(joystickOperator, "button_Chassis_Speed_Toggle");
	toggleChassisSpeed.whenPressed(new DBugToggleCommand(new ChassisLowSpeed(), new ChassisHighSpeed()));

	DBugJoystickButton DriveOneAxisButton = new DBugJoystickButton(joystickOperator, "button_Chassis_DriveOneAxis");
	DriveOneAxisButton.whileHeld(new DriveOneAxis());

	DriveOneAxisAxisButton1 = new DBugJoystickDigitalAxis(joystickOperator,
		(int) config.get("axis_Chassis_DriveOneAxis1"), (double) config.get("axis_Chassis_SwitchLimit"));
	DriveOneAxisAxisButton1.whileHeld(new DriveOneAxis());
	
	DBugJoystickDigitalAxis DriveOneAxisAxisButton2 = new DBugJoystickDigitalAxis(joystickOperator,
		(int) config.get("axis_Chassis_DriveOneAxis2"), (double) config.get("axis_Chassis_SwitchLimit"));
	DriveOneAxisAxisButton2.whileHeld(new DriveOneAxis());

	/*
	 * Intake
	 */
	DBugJoystickButton toggleIntakeBtn = new DBugJoystickButton(joystickOperator, "button_Intake_Toggle");
	toggleIntakeBtn.whenPressed(new DBugToggleCommand(new CollectGear(), new IntakeEmptyCommand()));

	/*
	 * Climbing
	 */
	DBugJoystickButton toggleClimbingButton = new DBugJoystickButton(joystickOperator,
		"button_ClimbingFast_Toggle");
	toggleClimbingButton.whenPressed(new DBugToggleCommand(new ClimbingUpFast(), new ClimbingStop()));

	DBugJoystickButton toggleClimbingSlowButton = new DBugJoystickButton(joystickOperator,
		"button_ClimbingSlow_Toggle");
	toggleClimbingSlowButton.whenPressed(new DBugToggleCommand(new ClimbingUpSlow(), new ClimbingStop()));
    }
}
