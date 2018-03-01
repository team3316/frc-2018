package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorToLevel;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

public class CenterPositionSwitch extends DBugCommandGroup {

	public CenterPositionSwitch(SwitchType type) {
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0); // Robot starting point

		if (type == SwitchType.RIGHT) {
			startPoints.addPathPoint(2.1, 1.95); // Right switch position from the center
			startPoints.addPathPoint(1.2, 2.0); // Rotation to be able to legally install a cube
		} else if (type == SwitchType.LEFT) {
			startPoints.addPathPoint(-3.7, 1.75); // Left switch position from the center
			startPoints.addPathPoint(-2.4, 1.8); // Rotation to be able to legally install a cube
		}

		PathFollowCommand startPath = new PathFollowCommand(startPoints, 7);

		addParallel(new ElevatorToLevel(Level.Switch));
		addSequential(startPath);
		addSequential(new HolderEjection());
	}

}
