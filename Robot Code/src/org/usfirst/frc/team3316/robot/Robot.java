
package org.usfirst.frc.team3316.robot;

import java.util.Timer;

import org.usfirst.frc.team3316.robot.auton.commands.AutonMode;
import org.usfirst.frc.team3316.robot.auton.sequences.AutonPosition;
import org.usfirst.frc.team3316.robot.auton.sequences.CenterPosition;
import org.usfirst.frc.team3316.robot.auton.sequences.CrossLine;
import org.usfirst.frc.team3316.robot.auton.sequences.LeftPosition;
import org.usfirst.frc.team3316.robot.auton.sequences.RightPosition;
import org.usfirst.frc.team3316.robot.auton.sequences.SwitchScaleType;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.ResetGyro;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorMoveToEdge;
import org.usfirst.frc.team3316.robot.commands.elevator.ShiftGear;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks;
import org.usfirst.frc.team3316.robot.humanIO.SDB;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.robotIO.Actuators;
import org.usfirst.frc.team3316.robot.robotIO.Sensors;
import org.usfirst.frc.team3316.robot.subsystems.Chassis;
import org.usfirst.frc.team3316.robot.subsystems.Elevator;
import org.usfirst.frc.team3316.robot.subsystems.EmptySubsystem;
import org.usfirst.frc.team3316.robot.subsystems.Holder;
import org.usfirst.frc.team3316.robot.subsystems.Intake;
import org.usfirst.frc.team3316.robot.utils.PIDControllers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static Config config;
	public static DBugLogger logger;
	public static Timer timer;

	/*
	 * Human IO
	 */
	public static Joysticks joysticks;
	public static SDB sdb;

	/*
	 * Robot IO
	 */
	public static Actuators actuators;
	public static Sensors sensors;

	/*
	 * Subsystems
	 */
	public static Chassis chassis;
	public static EmptySubsystem emptySubsystem;
	public static Intake intake;
	public static Holder holder;
	public static Elevator elevator;
	public static PIDControllers pidControllers;

	/*
	 * Choosers
	 */
	SendableChooser<AutonPosition> positionChooser;
	SendableChooser<AutonMode> modeChooser;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	public void robotInit() {
		/*
		 * Above all else
		 */
		try {
			logger = new DBugLogger();
			timer = new Timer();
			config = new Config();

			/*
			 * Human IO (that does not require subsystems)
			 */
			joysticks = new Joysticks();

			/*
			 * Robot IO
			 */
			actuators = new Actuators();
			sensors = new Sensors();

			Robot.actuators.GeneralActuators();
			Robot.sensors.GeneralSensors();
			Robot.sensors.VisionSensors();

			/*
			 * Subsystems
			 */
			emptySubsystem = new EmptySubsystem();
			chassis = new Chassis();
			intake = new Intake();
			elevator = new Elevator();
			holder = new Holder();
			pidControllers = new PIDControllers();

			/*
			 * Human IO (that requires subsystems)
			 */
			sdb = new SDB();

			/*
			 * Timer
			 */
			sdb.timerInit();

			/*
			 * Human IO (that requires subsystems)
			 */
			joysticks.initButtons();

			/*
			 * Choosers
			 */
			positionChooser = new SendableChooser<AutonPosition>();
			positionChooser.addDefault("Center", new CenterPosition());
			positionChooser.addObject("Left", new LeftPosition());
			positionChooser.addObject("Right", new RightPosition());
			
			SmartDashboard.putData("ROBOT POSITION A", positionChooser);

			modeChooser = new SendableChooser<AutonMode>();
			modeChooser.addDefault("Cross Line", AutonMode.CrossLine);
			modeChooser.addObject("Switch", AutonMode.Switch);
			modeChooser.addObject("Scale Or Switch", AutonMode.ScaleOrSwitch);
			
			SmartDashboard.putData("AUTON MODE A", modeChooser);

		} catch (Exception e) {
			logger.severe(e);
		}
	}

	public void disabledInit() {
		System.out.println(chassis);
		chassis.setBrake(false);
		elevator.setBrake(true);
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		if (modeChooser.getSelected() != AutonMode.CrossLine) {
			AutonPosition posCmd = positionChooser.getSelected();
			posCmd.setMode(modeChooser.getSelected());
			
			SwitchScaleType switchType = SwitchScaleType.LEFT, scaleType = SwitchScaleType.LEFT;
			String gameData;
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			if (gameData.length() > 0) {
				if (gameData.charAt(0) == 'L') {
					switchType = SwitchScaleType.LEFT;
				} else {
					switchType = SwitchScaleType.RIGHT;
				}
				
				if (gameData.charAt(1) == 'L') {
					scaleType = SwitchScaleType.LEFT;
				} else {
					scaleType = SwitchScaleType.RIGHT;
				}
			}
			
			posCmd.setSwitchType(switchType);
			posCmd.setScaleType(scaleType);
			
			posCmd.analizeMode();

			posCmd.start();
		} else {
			(new CrossLine()).start();
		}
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		elevator.setBrake(true);
		(new ResetGyro()).start();

		// TODO: Move to a specific command activated by a button.
//		(new ElevatorMoveToEdge(Level.Bottom)).start();
//		(new ShiftGear(Gear.LOW)).start();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testInit() {
	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}
