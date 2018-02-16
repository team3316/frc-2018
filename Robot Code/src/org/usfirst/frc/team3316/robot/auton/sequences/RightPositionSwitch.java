package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.auton.commands.DriveDistance;
import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorToLevelBangbang;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

public class RightPositionSwitch extends DBugCommandGroup {

    public RightPositionSwitch (SwitchType type) {
	PathPoints startPoints = new PathPoints();
	startPoints.addPathPoint(0.0, 0.0);
	startPoints.addPathPoint(-0.35, 2.6);
	PathFollowCommand startPath = new PathFollowCommand(startPoints, 3);
	
	PathPoints endPoints = new PathPoints();
	endPoints.addPathPoint(0.0, 0.0);
	endPoints.addPathPoint(1.5, 0.0);
	endPoints.addPathPoint(1.5, 2.0);
	endPoints.addPathPoint(-0.5, 2.0);
	
	PathFollowCommand endPath = new PathFollowCommand(endPoints, 6);

//	addSequential(new ElevatorToLevelBangbang(Level.Switch));
	addSequential(startPath);
//	addSequential(new HolderEjection());
//	addSequential(new ElevatorToLevelBangbang(Level.Bottom));
	addSequential(new DriveDistance(-1.0));
	addSequential(endPath);
	
	
    }

}
