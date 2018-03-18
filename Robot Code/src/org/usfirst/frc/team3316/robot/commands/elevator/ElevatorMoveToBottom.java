package org.usfirst.frc.team3316.robot.commands.elevator;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

public class ElevatorMoveToBottom extends ElevatorMoveToEdge {
	private double v;
	private long initTime, initSlowTime;
	private boolean voltageChanged = false, slowTime = false;
	
	private class CheckBPClass extends TimerTask {
		@Override
		public void run() {
			if (Robot.elevator.getLevel() == Level.BrakePointBottom) {
				voltageChanged = true;
				v = (double) config.get("elevator_MoveToEdge_SlowDownVoltage");
			}
			else if (Robot.elevator.getLevel() == Level.BrakePointTop) {
				voltageChanged = true;
				slowTime = true;
				v = 0.0;
				initSlowTime = System.currentTimeMillis();				
			}
		}
	}
	
	@Override
	Level setLevel() {
		// TODO Auto-generated method stub
		return Level.Bottom;
	}

	@Override
	double setVoltage() {
		// TODO Auto-generated method stub
		return v;
	}

	@Override
	void moveInit() {
		// TODO Auto-generated method stub
		CheckBPClass bpClass = new CheckBPClass(); // A class for detecting break point hall effect
		Robot.timer.schedule(bpClass, 0, 1);
		
		v = (double) config.get("elevator_MoveToEdge_StartDownVoltage");
		(new MoveServo((double) config.get("servo_Initial_Angle"), false)).start();
		
		initTime = System.currentTimeMillis();
		voltageChanged = false;
		slowTime = false;
	}

	@Override
	void running() {
		// TODO Auto-generated method stub
		if (System.currentTimeMillis() - initTime >= (double) config.get("elevator_MoveToEdge_StartDownTime") && !voltageChanged) {
			v = (double) config.get("elevator_MoveToEdge_DownVoltage");
			voltageChanged = true;
		}
		
		if (System.currentTimeMillis() - initSlowTime >= (double) config.get("elevator_MoveToEdge_SlowTime") && slowTime) {
			slowTime = false;
			v = (double) config.get("elevator_MoveToEdge_DownVoltage");
		}
	}

}
