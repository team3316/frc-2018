package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.auton.commands.AutonMode;
import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class LeftPosition extends AutonPosition {

	@Override
	public void analizeMode() {
		if (mode != null && mode == AutonMode.Switch) {
			toSwitch();
		}
		else {
			if (scaleType == SwitchScaleType.LEFT) {
				logger.info("I'll go straight to LEFT Scale");
			} else {
				toSwitch();
			}
		}
	}
	
	private void toSwitch() {
		if (switchType == SwitchScaleType.LEFT) {
			logger.info("I'll go straight to Left Switch");
			toLeftSwitch();
		} else {
			logger.info("I'll go behind to Right Switch");
			crossAuto();
		}
	}
	
	private void crossAuto() {
		PathFollowCommand startPath;
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.0, 3.0);
		startPath = new PathFollowCommand(startPoints, 3);
		
		addParallel(new MoveServo((double) config.get("servo_Final_Angle"), false));
		addSequential(startPath);
	}
	
	private void toLeftSwitch() {
		PathFollowCommand startPath;
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.0, 2.7);
		startPoints.addPathPoint(0.5, 3.3);
		startPath = new PathFollowCommand(startPoints, 3);
		
		addParallel(new MoveServo((double) config.get("servo_Final_Angle"), false));
		addSequential(startPath);
		addSequential(new HolderEjection());
		addSequential(new WaitCommand(1.0));
	}
	
}
