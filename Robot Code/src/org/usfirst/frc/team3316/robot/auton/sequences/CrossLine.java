package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

public class CrossLine extends DBugCommandGroup {
	public CrossLine() {
		logger.info("I'll just cross the line");
		PathFollowCommand startPath;
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.0, 3.0);
		startPath = new PathFollowCommand(startPoints, 3);
		
		addParallel(new MoveServo((double) config.get("servo_Final_Angle"), false));
		addSequential(startPath);
	}
}
