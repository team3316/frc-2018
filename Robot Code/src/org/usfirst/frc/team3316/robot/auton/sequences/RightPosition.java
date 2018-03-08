package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.auton.commands.AutonMode;
import org.usfirst.frc.team3316.robot.auton.commands.DriveDistance;
import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorMoveToEdge;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class RightPosition extends AutonPosition {
	PathFollowCommand startPath;


	@Override
	public void analizeMode() {
		if (mode != null && mode == AutonMode.Switch) {
			toSwitch();
		}
		else {
			if (scaleType == SwitchScaleType.RIGHT) {
				logger.info("I'll go straight to Right Scale");
			} else {
				toSwitch();
			}
		}
	}
	
	private void toSwitch() {
		if (switchType == SwitchScaleType.RIGHT) {
			logger.info("I'll go straight to Right Switch");
			toRightSwitch();
		} else {
			logger.info("I'll go behind to Left Switch");
			toLeftSwitch();
		}
	}
	
	private void toRightSwitch() {
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.0, 2.7);
		startPoints.addPathPoint(-0.5, 3.3);
		startPath = new PathFollowCommand(startPoints, 3);
		
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
		startPoints.addPathPoint(0.0, 3.0);
		startPath = new PathFollowCommand(startPoints, 3);
		
		addParallel(new MoveServo((double) config.get("servo_Final_Angle"), false));
		addSequential(startPath);
	}

}
