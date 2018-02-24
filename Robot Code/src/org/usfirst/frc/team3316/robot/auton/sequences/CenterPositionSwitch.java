package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.auton.commands.DriveDistance;
import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorToLevelBangbang;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

public class CenterPositionSwitch extends DBugCommandGroup {

	public CenterPositionSwitch(SwitchType type) {
		if (type == SwitchType.RIGHT) {
			PathPoints startPoints = new PathPoints();
			startPoints.addPathPoint(0.0, 0.0);
			startPoints.addPathPoint(2.1, 1.95);
			startPoints.addPathPoint(1.2, 2.0);
			PathFollowCommand startPath = new PathFollowCommand(startPoints, 7);

			addParallel(new ElevatorToLevelBangbang(1.7));
			addSequential(startPath);
			addSequential(new HolderEjection());
		} else if (type == SwitchType.LEFT) {
			PathPoints startPoints = new PathPoints();
			startPoints.addPathPoint(0.0, 0.0);
			startPoints.addPathPoint(-3.7, 1.75);
			startPoints.addPathPoint(-2.4, 1.8);
			PathFollowCommand startPath = new PathFollowCommand(startPoints, 7);

			addParallel(new ElevatorToLevelBangbang(1.7));
			addSequential(startPath);
			addSequential(new HolderEjection());
		}

	}

}
