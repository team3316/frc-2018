/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.auton.commands.AlignToCube;
import org.usfirst.frc.team3316.robot.commands.chassis.BrakeMode;
import org.usfirst.frc.team3316.robot.commands.chassis.CoastMode;
import org.usfirst.frc.team3316.robot.commands.chassis.DriveOneAxis;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorEmptyCommand;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorJoystick;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorMoveToEdge;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorToLevelBangbang;
import org.usfirst.frc.team3316.robot.commands.holder.HolderCollection;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRoll;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRollType;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRoll;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollType;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.CollectCube;
import org.usfirst.frc.team3316.robot.sequences.EjectCube;
import org.usfirst.frc.team3316.robot.sequences.StopCollectionEjection;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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

    public Joystick joystickLeft, joystickRight;
    public Joystick joystickOperator;
    public Joystick joystickElevator;
    public DBugJoystickButton intakeInBtn, intakeOutBtn;
    public AnalogTrigger intakeDirectionalBtn;
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
//	DBugJoystickButton toggleChassisBrakeMode = new DBugJoystickButton(joystickOperator,
//		"button_Chassis_Break_Toggle");
//	toggleChassisBrakeMode.whenPressed(new DBugToggleCommand(new BrakeMode(), new CoastMode()));
	
	/*
	 * Collection and Ejection
	 */
	DBugJoystickButton buttonCollection = new DBugJoystickButton(joystickOperator, "button_Collection");
	buttonCollection.whenPressed(new DBugToggleCommand(new CollectCube(), new StopCollectionEjection()));
	
	DBugJoystickButton buttonEjection = new DBugJoystickButton(joystickOperator, "button_Ejection");
	buttonEjection.whenPressed(new DBugToggleCommand(new EjectCube(), new StopCollectionEjection()));
	
	/*
	 * Holder
	 */
	DBugJoystickButton toggleHolderRollIn = new DBugJoystickButton(joystickOperator, "button_Holder_RollIn");
	toggleHolderRollIn.whenPressed(
		new DBugToggleCommand(new HolderCollection(), new HolderRoll(HolderRollType.Stop)));

	DBugJoystickButton toggleHolderRollOut = new DBugJoystickButton(joystickOperator, "button_Holder_RollOut");
	toggleHolderRollOut.whenPressed(
		new DBugToggleCommand(new HolderEjection(), new HolderRoll(HolderRollType.Stop)));
	
	/*
	 * Elevator
	 */
	DBugJoystickButton elevatorTop = new DBugJoystickButton(joystickOperator,
		"button_Elevetor_Top");
	elevatorTop.whenPressed(new ElevatorMoveToEdge(Level.Top));
	DBugJoystickButton elevatorScale = new DBugJoystickButton(joystickOperator,
		"button_Elevetor_Scale");
	elevatorScale.whenPressed(new ElevatorToLevelBangbang(Level.Scale));
	DBugJoystickButton elevatorSwitch = new DBugJoystickButton(joystickOperator,
		"button_Elevetor_Switch");
	elevatorSwitch.whenPressed(new ElevatorToLevelBangbang(Level.Switch));
	DBugJoystickButton elevatorBottom = new DBugJoystickButton(joystickOperator,
		"button_Elevetor_Bottom");
	elevatorBottom.whenPressed(new ElevatorMoveToEdge(Level.Bottom));
	
	DBugJoystickButton toggleElevatorJoystick = new DBugJoystickButton(joystickOperator, "button_Elevator_Joystick_Toggle");
	toggleElevatorJoystick.whenPressed(
		new DBugToggleCommand(new ElevatorJoystick(), new ElevatorEmptyCommand()));
	
	
    
    }
}
