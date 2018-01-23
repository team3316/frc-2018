package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;

public class Elevator extends DBugSubsystem {
	// Variables
	public double position;

	// Sensors
	private AnalogInput heBottom, heSwitch, heScale, heTop;
	private Encoder encoder;
	
	// Actuators
	private DBugSpeedController motorOne, motorTwo;
	private DoubleSolenoid shifter;

	/**
	 * Constructor
	 */
	public Elevator() {
		// Sensors
		Robot.sensors.ElevatorSensors();
		this.heBottom = Robot.sensors.elevatorHeBottom;
		this.heSwitch = Robot.sensors.elevatorHeSwitch;
		this.heScale = Robot.sensors.elevatorHeScale;
		this.heTop = Robot.sensors.elevatorHeTop;
		this.encoder = Robot.sensors.elevatorEncoder;
	
		// Actuators
		Robot.actuators.ElevatorActuators();
		this.motorOne = Robot.actuators.elevatorMotorOne;
		this.motorTwo = Robot.actuators.elevatorMotorTwo;
		this.shifter = Robot.actuators.elevatorShifter;
	}

	@Override
	public void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	@Override
	public void periodic () {
		
	}
}
