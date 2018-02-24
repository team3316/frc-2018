package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorToLevelBangbang;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRoll;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRollType;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

public class LeftPositionSwitch extends DBugCommandGroup {
	public LeftPositionSwitch(SwitchType type) {
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.0, 2.5);
		startPoints.addPathPoint(0.6, 3.0);
		PathFollowCommand startPath = new PathFollowCommand(startPoints, 4);

		addParallel(startPath);
		addSequential(new ElevatorToLevelBangbang(1.7));
		addSequential(new HolderEjection());
	}
}
