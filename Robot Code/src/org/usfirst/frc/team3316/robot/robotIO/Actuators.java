package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.*;

/**
 * Le robot actuators
 */
public class Actuators {
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// General
	public Compressor compressor;

	// Chassis
	public DBugSpeedController chassisLeft1, chassisLeft2, chassisRight1, chassisRight2;
	public WPI_TalonSRX chassisLeft1SC, chassisLeft2SC, chassisRight1SC, chassisRight2SC;

	// Roller grippers
	public DBugSpeedController holderMotor, intakeLeft, intakeRight;
	public VictorSP holderSC, intakeLeftSC, intakeRightSC;
	
	// TO REMOVE AFTER TESTING
	public DBugSpeedController holderLeft, holderRight;
	public WPI_TalonSRX holderLeftSC, holderRightSC;
	

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
		// if (config.robotA) {
		// GeneralActuatorsA();
		// compressor = new Compressor(0);
		// } else {
		// GeneralActuatorsB();s
		// compressor = new Compressor(0);
		// }
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
		chassisLeft1 = new DBugSpeedController(chassisLeft1SC, (boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL"));
		chassisLeft2 = new DBugSpeedController(chassisLeft2SC, (boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL"));

		chassisRight1 = new DBugSpeedController(chassisRight1SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL"));
		chassisRight2 = new DBugSpeedController(chassisRight2SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL"));
	}

	/*
	 * Intake
	 */
	private void IntakeActuatorsA() {
		intakeLeftSC = new VictorSP((int) Robot.config.get("INTAKE_MOTOR_LEFT"));
		intakeRightSC = new VictorSP((int) Robot.config.get("INTAKE_MOTOR_RIGHT"));
	}

	private void IntakeActuatorsB() {
		// Nothin' here atm
	}

	public void IntakeActuators() {
		if (config.robotA) {
			IntakeActuatorsA();
		} else {
			IntakeActuatorsB();
		}

		intakeLeft = new DBugSpeedController(intakeLeftSC, true, -1);
		intakeRight = new DBugSpeedController(intakeRightSC, false, -1);
	}

	/*
	 * Holder
	 */
	private void HolderActuatorsA() {
//		holderSC = new VictorSP((int) Robot.config.get("HOLDER_MOTOR_PORT"));
	    
	    // FOR MULE (TO REMOVE AFTER TESTING)
	    holderLeftSC = new WPI_TalonSRX(5);
	    holderRightSC = new WPI_TalonSRX(4);
	}
	
	private void HolderActuatorsB() {
		// Nothin' here atm
	}
	
	public void HolderActuators() {
		if (config.robotA) {
			HolderActuatorsA();
		} else {
			HolderActuatorsB();
		}
		
//		holderMotor = new DBugSpeedController(holderSC, false	, -1);
		
		// TO REMOVE AFTER TESTINGS
		holderLeft = new DBugSpeedController(holderLeftSC, true, -1);
		holderRight = new DBugSpeedController(holderRightSC, false, -1);
	}
}
