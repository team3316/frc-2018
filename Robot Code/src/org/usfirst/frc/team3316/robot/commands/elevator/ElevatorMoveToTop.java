package org.usfirst.frc.team3316.robot.commands.elevator;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

public class ElevatorMoveToTop extends ElevatorMoveToEdge {
	
	private double v;
	
	@Override
	Level setLevel() {
		// TODO Auto-generated method stub
		return Level.Top;
	}

	@Override
	double setVoltage() {
		// TODO Auto-generated method stub
		return v;
	}

	@Override
	void moveInit() {
		v = (double) config.get("elevator_MoveToEdge_UpVoltage");
	}

	@Override
	void running() {}

}
