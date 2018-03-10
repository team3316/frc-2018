package org.usfirst.frc.team3316.robot.commands.elevator;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Gear;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

public class ClimbUp extends DBugCommandGroup {
	public ClimbUp() {
		addSequential(new ShiftGear(Gear.HIGH));
		addSequential(new ElevatorMoveToEdge(Level.Bottom));
	}
}
