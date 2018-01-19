/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.paths.Path1;
import org.usfirst.frc.team3316.robot.commands.emptyCommand;
import org.usfirst.frc.team3316.robot.commands.chassis.MoveChassis;
import org.usfirst.frc.team3316.robot.commands.chassis.ResetGyro;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDB {
	/*
	 * Runnable that periodically updates values from the robot into the
	 * SmartDashboard This is the place where all of the robot data should be
	 * displayed from
	 */
	private class UpdateSDBTask extends TimerTask {
		public UpdateSDBTask() {
			logger.info("Created UpdateSDBTask");
		}

		public void run() {
			/*
			 * For driver (shai shai)
			 */

			// Chassis
			put("Distace right", Robot.chassis.getRightDistance());
			put("Distace left", Robot.chassis.getLeftDistance());
			put("Speed", Robot.chassis.getSpeed());

			put("Yaw angle", Robot.chassis.getYaw());
			put("Roll angle", Robot.chassis.getRoll());
			put("Pitch angle", Robot.chassis.getPitch());
			
			logger.info("yaw:" + Robot.chassis.getYaw() + ", roll:" + Robot.chassis.getRoll() + ", pitch:" + Robot.chassis.getPitch());

			put("Low Speed", Robot.chassis.isDrivingSlowly());
			
			put("LEFT ENCODER DIST", Robot.chassis.getLeftDistance());
			put("RIGHT ENCODER DIST", Robot.chassis.getRightDistance());
		}

		private void put(String name, double d) {
			SmartDashboard.putNumber(name, d);
		}

		private void put(String name, int i) {
			SmartDashboard.putNumber(name, i);
		}

		private void put(String name, boolean b) {
			SmartDashboard.putBoolean(name, b);
		}

		private void put(String name, String s) {
			SmartDashboard.putString(name, s);
		}
	}

	DBugLogger logger = Robot.logger;
	Config config = Robot.config;

	private UpdateSDBTask updateSDBTask;

	private Hashtable<String, Class<?>> variablesInSDB;

	public SDB() {
		variablesInSDB = new Hashtable<String, Class<?>>();

		initSDB();
//		initDriverCameras();
	}

	public void timerInit() {
		updateSDBTask = new UpdateSDBTask();
		Robot.timer.schedule(updateSDBTask, 0, 10);
	}

	/**
	 * Adds a certain key in the config to the SmartDashboard
	 * 
	 * @param key
	 *            the key required
	 * @return whether the value was put in the SmartDashboard
	 */
	public boolean putConfigVariableInSDB(String key) {
		Object value = config.get(key);
		if (value != null) {
			Class<?> type = value.getClass();

			boolean constant = Character.isUpperCase(key.codePointAt(0))
					&& Character.isUpperCase(key.codePointAt(key.length() - 1));

			if (type == Double.class) {
				SmartDashboard.putNumber(key, (double) value);
			} else if (type == Integer.class) {
				SmartDashboard.putNumber(key, (int) value);
			} else if (type == Boolean.class) {
				SmartDashboard.putBoolean(key, (boolean) value);
			}

			if (!constant) {
				variablesInSDB.put(key, type);
				logger.info("Added to SDB " + key + " of type " + type + " and allows for its modification");
			} else {
				logger.info("Added to SDB " + key + " of type " + type + " BUT DOES NOT ALLOW for its modification");
			}

			return true;
		}

		return false;
	}

	public Set<Entry<String, Class<?>>> getVariablesInSDB() {
		return variablesInSDB.entrySet();
	}

	private void initSDB() {
		SmartDashboard.putData(new UpdateVariablesInConfig()); // NEVER REMOVE
		// THIS COMMAND

		// Chassis
		SmartDashboard.putData(new ResetGyro());
		
		

		logger.info("Finished initSDB()");
	}

	private void initDriverCameras() {
		// Cameras
		CameraServer.getInstance().startAutomaticCapture("cam0", 0);
		CameraServer.getInstance().startAutomaticCapture("cam1", 1);
	}

	/**
	 * This method puts in the live window of the test mode all of the robot's
	 * actuators and sensors. It is disgusting.
	 */
	public void initLiveWindow() {
		initLiveWindowActuators();
		initLiveWindowSensors();

		logger.info("Finished initLiveWindow()");
	}

	private void initLiveWindowActuators() {

	}

	private void initLiveWindowSensors() {
		
	}
}