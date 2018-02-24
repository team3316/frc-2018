package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorJoystick;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorMoveToEdge;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorShaken;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorToLevelBangbang;
import org.usfirst.frc.team3316.robot.commands.holder.HolderCollection;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRoll;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRollType;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.CollectCube;
import org.usfirst.frc.team3316.robot.sequences.EjectCube;
import org.usfirst.frc.team3316.robot.sequences.StopCollectionEjection;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Class for joysticks and joystick buttons
 */
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
	public DBugJoystickButton intakeInBtn, intakeOutBtn;
	public AnalogTrigger intakeDirectionalBtn;
	public DBugJoystickDigitalAxis DriveOneAxisAxisButton1;

	public double deadBand = 0.0;
	public boolean invertX, invertY;

	public enum AxisType {
		LeftX(1), LeftY(2), RightX(3), RightY(4);

		private int type;

		private AxisType(int type) {
			this.type = type;
		}

		private String getOperatorConfigKey() {
			String baseKey = "chassis_Joystick_";
			switch (this.type) {
			case 1:
				return baseKey + "LeftX_Axis";
			case 2:
				return baseKey + "LeftY_Axis";
			case 3:
				return baseKey + "RightX_Axis";
			case 4:
				return baseKey + "RightY_Axis";
			default:
				return "";
			}
		}

		public int getOperatorAxis() {
			String configKey = this.getOperatorConfigKey();
			return (int) Robot.config.get(configKey);
		}
	}

	public enum JoystickType {
		Operator, Driver;
	}

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
		toggleHolderRollIn
				.whenPressed(new DBugToggleCommand(new HolderCollection(), new HolderRoll(HolderRollType.Stop)));

		DBugJoystickButton toggleHolderRollOut = new DBugJoystickButton(joystickOperator, "button_Holder_RollOut");
		toggleHolderRollOut
				.whenPressed(new DBugToggleCommand(new HolderEjection(), new HolderRoll(HolderRollType.Stop)));

		/*
		 * Elevator
		 */
		DBugJoystickButton elevatorTop = new DBugJoystickButton(joystickOperator, "button_Elevetor_Top");
		elevatorTop.whenPressed(new ElevatorMoveToEdge(Level.Top));
		DBugJoystickButton elevatorScale = new DBugJoystickButton(joystickOperator, "button_Elevetor_Scale");
		elevatorScale.whenPressed(new ElevatorToLevelBangbang(Level.Scale));
		DBugJoystickButton elevatorSwitch = new DBugJoystickButton(joystickOperator, "button_Elevetor_Switch");
		elevatorSwitch.whenPressed(new ElevatorToLevelBangbang(Level.Switch));
		DBugJoystickButton elevatorBottom = new DBugJoystickButton(joystickOperator, "button_Elevetor_Bottom");
		elevatorBottom.whenPressed(new ElevatorMoveToEdge(Level.Bottom));

		DBugJoystickButton toggleElevatorJoystick = new DBugJoystickButton(joystickOperator,
				"button_Elevator_Joystick_Toggle");
		toggleElevatorJoystick.whenPressed(new DBugToggleCommand(new ElevatorJoystick(), new ElevatorShaken()));

	}

	/**
	 * Returns the value from an axis of the operator joystick with dead band applied.
	 * @param axisType The axis to operate on
	 * @return The axis value with dead band
	 */
	private double getOperatorDeadBandAxis(AxisType axisType) {
		double toBeBanded = joystickOperator.getRawAxis(axisType.getOperatorAxis());
		return deadBand(toBeBanded);
	}

	/**
	 * Returns the value from an axis of the driver joystick with dead band applied.
	 * @param axisType The axis to operate on
	 * @return The axis value with dead band
	 */
	private double getDriverDeadBandAxis(AxisType axisType) {
		switch (axisType) {
		case LeftX:
			return deadBand(joystickLeft.getX());
		case LeftY:
			return deadBand(joystickLeft.getY());
		case RightX:
			return deadBand(joystickRight.getY());
		case RightY:
			return deadBand(joystickRight.getY());
		default:
			return Double.NaN;
		}
	}

	/**
	 * Returns the value from an axis of a given joystick with dead band applied.
	 * @param axisType The axis to operate on
	 * @param joystickType The joystick which has the axis
	 * @return The axis value with dead band
	 */
	private double getDeadBandAxis(AxisType axisType, JoystickType joystickType) {
		switch (joystickType) {
		case Operator:
			return getOperatorDeadBandAxis(axisType);
		case Driver:
			return getDriverDeadBandAxis(axisType);
		default:
			return Double.NaN;
		}
	}

	/**
	 * Returns an axis of a joystick with dead band, inverted or not, and updates the config variables.
	 * @param axisType
	 * @param joystickType
	 * @return
	 */
	public double getAxis(AxisType axisType, JoystickType joystickType) {
		updateConfigVariables();
		double y = getDeadBandAxis(axisType, joystickType);
		return invertY ? -y : y;
	}

	private double deadBand(double x) {
		if (Math.abs(x) < deadBand) {
			return 0;
		}
		return x;
	}

	/*
	 * Here we call the get method of the config every execute because we want the
	 * variables to update without needing to cancel the commands.
	 */
	private void updateConfigVariables() {
		deadBand = (double) config.get("chassis_TankDrive_DeadBand");

		invertX = (boolean) config.get("chassis_TankDrive_InvertX");
		invertY = (boolean) config.get("chassis_TankDrive_InvertY");
	}
}
