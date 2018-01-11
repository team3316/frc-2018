/**
 * Le robot actuators
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.*;

public class Actuators
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// General
	public Compressor compressor;

	// Chassis
	public DBugSpeedController chassisLeft1, chassisLeft2, chassisRight1, chassisRight2;
	public TalonSRX chassisLeft1SC, chassisLeft2SC, chassisRight1SC, chassisRight2SC;
	
	// Intake
	public DBugSpeedController intakeMotor;
	public SpeedController intakeMotorSC;
	public Servo intakeExtenderServo;
	
	// Climbing
	public DBugSpeedController climbingMotor;
	public SpeedController climbingMotorSC;
	
	public Actuators() {}

	/*
	 * General
	 */

	private void GeneralActuatorsA()
	{}

	private void GeneralActuatorsB()
	{}

	public void GeneralActuators()
	{
		if (config.robotA)
		{
			GeneralActuatorsA();
			compressor = new Compressor(0);
		}
		else
		{
			GeneralActuatorsB();
			compressor = new Compressor(0);
		}
	}
	
	/*
	 * Chassis
	 */
	private void ChassisActuatorsA()
	{
		chassisLeft1SC = new TalonSRX((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1"));
		chassisLeft2SC = new TalonSRX((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2"));
		chassisRight1SC = new TalonSRX((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1"));
		chassisRight2SC = new TalonSRX((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2"));
	}

	private void ChassisActuatorsB()
	{}

	public void ChassisActuators()
	{
		if (config.robotA)
		{
			ChassisActuatorsA();
		}
		else
		{
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
	private void IntakeActuatorsA()
	{
		intakeMotorSC = new Talon((int) Robot.config.get("INTAKE_MOTOR"));
		intakeExtenderServo = new Servo((int) Robot.config.get("INTAKE_SERVO"));
	}

	private void IntakeActuatorsB()
	{}
	
	public void IntakeActuators()
	{
		if (config.robotA)
		{
			IntakeActuatorsA();
		}
		else
		{
			IntakeActuatorsB();
		}
		intakeMotor = new DBugSpeedController(intakeMotorSC, (boolean) Robot.config.get("INTAKE_MOTOR_REVERSE"),
				(int) config.get("INTAKE_MOTOR_PDP_CHANNEL"));
	}
	
	/*
	 * Climbing
	 */
	private void ClimbingActuatorsA()
	{
		climbingMotorSC = new VictorSP((int) Robot.config.get("CLIMBING_MOTOR"));
	}

	private void ClimbingActuatorsB()
	{}
	
	public void ClimbingActuators()
	{
		if (config.robotA)
		{
			ClimbingActuatorsA();
		}
		else
		{
			ClimbingActuatorsB();
		}
		climbingMotor = new DBugSpeedController(climbingMotorSC, (boolean) Robot.config.get("CLIMBING_MOTOR_REVERSE"),
				(int) config.get("CLIMBING_MOTOR_PDP_CHANNEL"));
	}
}
