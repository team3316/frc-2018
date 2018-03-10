package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class HolderEjectionServo extends DBugCommandGroup {

	public HolderEjectionServo() {
		addSequential(new MoveServo((double) config.get("servo_Final_Angle"), true));
		addSequential(new HolderEjection());
	}

}
