package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class CenterPosition extends AutonPosition {

	@Override
	public void analizeMode() {
		toSwitch();
	}
	
	private void toSwitch() {
		if (switchType == SwitchScaleType.LEFT) {
			logger.info("I'll go to Left Switch");
			toLeftSwitch();
		} else {
			logger.info("I'll go to Right Switch");
			toRightSwitch();
		}
	}
	
	private void toRightSwitch() {
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.3, 2.7);
		PathFollowCommand startPath = new PathFollowCommand(startPoints, 3);
		
		addParallel(new MoveServo((double) config.get("servo_Final_Angle"), false));
		addSequential(startPath);
		addSequential(new HolderEjection());
		addSequential(new WaitCommand(1.0));
//		addSequential(new DriveDistance(-0.5));
//		addSequential(new ElevatorMoveToEdge(Level.Bottom));
	}
	
	private void toLeftSwitch() {
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(-2.65, 1.6);
		startPoints.addPathPoint(-2.8, 2.7);
		PathFollowCommand startPath = new PathFollowCommand(startPoints, 5);
		
		addParallel(new MoveServo((double) config.get("servo_Final_Angle"), false));
		addSequential(startPath);
		addSequential(new HolderEjection());
		addSequential(new WaitCommand(1.0));
//		addSequential(new DriveDistance(-0.5));
//		addSequential(new ElevatorMoveToEdge(Level.Bottom));
	}

}
