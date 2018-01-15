/**
 * Le robot actuators
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.subsystems.RollerGripper;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.*;

public class Actuators {
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// General
	public Compressor compressor;

	// Chassis
	public DBugSpeedController chassisLeft1, chassisLeft2, chassisRight1, chassisRight2;
	public WPI_TalonSRX chassisLeft1SC, chassisLeft2SC, chassisRight1SC, chassisRight2SC;
	
	// Roller gripper
	public DBugSpeedController rollerGripperLeft, rollerGripperRight;
	public VictorSP rollerGripperLeftSC, rollerGripperRightSC;

	public Actuators() {
	}

	/*
	 * General
	 */

	private void GeneralActuatorsA() {
	}

	private void GeneralActuatorsB() {
	}

	public void GeneralActuators() {
//		if (config.robotA) {
//			GeneralActuatorsA();
//			compressor = new Compressor(0);
//		} else {
//			GeneralActuatorsB();s
//			compressor = new Compressor(0);
//		}
	}

	/*
	 * Chassis
	 */
	private void ChassisActuatorsA() {
		chassisLeft1SC = new WPI_TalonSRX((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1"));
		chassisLeft2SC = new WPI_TalonSRX((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2"));
		chassisRight1SC = new WPI_TalonSRX((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1"));
		chassisRight2SC = new WPI_TalonSRX((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2"));
	}

	private void ChassisActuatorsB() {
	}

	public void ChassisActuators() {
		if (config.robotA) {
			ChassisActuatorsA();
		} else {
			ChassisActuatorsB();
		}
		chassisLeft1 = new DBugSpeedController(chassisLeft1SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL"));
		chassisLeft2 = new DBugSpeedController(chassisLeft2SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL"));

		chassisRight1 = new DBugSpeedController(chassisRight1SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL"));
		chassisRight2 = new DBugSpeedController(chassisRight2SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL"));
	}

	/*
	 * Roller gripper
	 */
	private void RollerGripperActuatorsA () {
	    rollerGripperLeftSC = new VictorSP((int) Robot.config.get("ROLLERGRIPPER_MOTOR_LEFT"));
	    rollerGripperRightSC = new VictorSP((int) Robot.config.get("ROLLERGRIPPER_MOTOR_RIGHT"));
	}

	private void RollerGripperActuatorsB () {
	    // Nothin' here atm
	}
	
	public void RollerGripperActuators() {
	    if (config.robotA) {
		RollerGripperActuatorsA();
	    } else {
		RollerGripperActuatorsB();
	    }

	    rollerGripperLeft = new DBugSpeedController(rollerGripperLeftSC, false, -1);
	    rollerGripperRight = new DBugSpeedController(rollerGripperRightSC, false, -1);
	}
}
