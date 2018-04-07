package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.auton.commands.AutonMode;
import org.usfirst.frc.team3316.robot.auton.commands.DriveDistance;
import org.usfirst.frc.team3316.robot.auton.commands.TurnByGyro;
import org.usfirst.frc.team3316.robot.auton.commands.TurnByGyroBB;
import org.usfirst.frc.team3316.robot.chassis.paths.PathFollowCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorMoveToEdge;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorShaken;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorToLevel;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorMoveToBottom;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorMoveToTop;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.holder.MoveServo;
import org.usfirst.frc.team3316.robot.sequences.CollectCube;
import org.usfirst.frc.team3316.robot.sequences.EjectCube;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class RightPosition extends AutonPosition {
	PathFollowCommand startPath;

	public RightPosition() {
		// TODO Auto-generated constructor stub
		requires(Robot.emptySubsystem);
	}

	@Override
	public void analizeMode() {
		if (mode != null && mode == AutonMode.Switch) {
			toSwitch();
		} else {
			toScale();
		}
	}

	private void toSwitch() {
		if (switchType == SwitchScaleType.RIGHT) {
			logger.info("I'll go straight to Right Switch");
			toRightSwitch();
			anotherCube();
		} else {
			logger.info("I'll go behind to Left Switch");
			toLeftSwitch();
		}
	}

	private void toScale() {
		if (scaleType == SwitchScaleType.RIGHT) {
			logger.info("I'll go straight to Right Scale");
			toRightScale();

			if (cubeType == CubeState.TwoCubesSwitch || cubeType == CubeState.TwoCubesScale) {
				logger.info("And I'll install another cube");
				anotherCube();
			}
		} else {
			// toSwitch();
			logger.info("I'll go behind to Left Scale");
			toLeftScale();

			if (cubeType == CubeState.TwoCubesSwitch || cubeType == CubeState.TwoCubesScale) {
				logger.info("And I'll install another cube");
				anotherCube();
			}
		}
	}

	private void toRightScale() {
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.0, 4.0);
		startPoints.addPathPoint(-0.52, 6.15);

		addParallel(new ElevatorMoveToTop());
		addSequential(new PathFollowCommand(startPoints, 3.0));
		addSequential(new ElevatorMoveToTop());
		addSequential(new HolderEjection());
		addSequential(new DriveDistance(-0.53));
		addSequential(new ElevatorMoveToBottom());
		addSequential(new TurnByGyroBB(-110.0));
		addParallel(new DriveDistance(1.4), 1.0);
		addSequential(new CollectCube());
	}

	private void toLeftScale() {
		PathPoints startPoints = new PathPoints();
		// startPoints.addPathPoint(0.0, 0.0);
		// startPoints.addPathPoint(0.0, 5.65);
		// startPoints.addPathPoint(-4.4, 5.65);

		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.3, 5.95);
		startPoints.addPathPoint(-3.85, 5.95);

		addSequential(new PathFollowCommand(startPoints, 5.0));
		addParallel(new TurnByGyroBB(80.0));
		addSequential(new ElevatorMoveToTop());
		addParallel(new ElevatorShaken());
		addSequential(new DriveDistance(0.8));
		addSequential(new ElevatorMoveToTop());
		addSequential(new HolderEjection());
		addSequential(new DriveDistance(-0.85));
		addSequential(new ElevatorMoveToBottom());
		addSequential(new TurnByGyroBB(-155.0));
		addParallel(new DriveDistance(0.65), 0.75);
		addSequential(new CollectCube());
	}

	private void anotherCube() {
		if (cubeType == CubeState.TwoCubesSwitch) {
			if (mode == AutonMode.ScaleOrSwitch) {
				if (scaleType == SwitchScaleType.RIGHT && switchType == SwitchScaleType.RIGHT) {
					anotherCubeSwitch();
				} else if (scaleType == SwitchScaleType.LEFT && switchType == SwitchScaleType.LEFT) {
					anotherCubeSwitch();
				}
			} else {
				anotherCubeSwitch();
			}
		} else if (cubeType == CubeState.TwoCubesScale && mode == AutonMode.ScaleOrSwitch) {
			if (scaleType == SwitchScaleType.RIGHT) {
				anotherScaleRight();
			} else if (scaleType == SwitchScaleType.LEFT) {
				// TODO: Add another scale left
			}
		}

	}

	private void anotherCubeSwitch() {
		addParallel(new MoveServo((double) config.get("servo_Final_Angle"), false));
		addSequential(new ElevatorToLevel(Level.Switch));
		addSequential(new HolderEjection());
	}

	private void anotherScaleRight() {
		addSequential(new DriveDistance(-0.5), 0.5);
		addSequential(new TurnByGyroBB(true, 10.0));
		addSequential(new ElevatorMoveToTop());
		addSequential(new DriveDistance(0.85));
		addSequential(new ElevatorMoveToTop());
		addSequential(new EjectCube());
	}

	private void toRightSwitch() {
		PathPoints startPoints = new PathPoints();
		startPoints.addPathPoint(0.0, 0.0);
		startPoints.addPathPoint(0.3, 4.0);
		startPoints.addPathPoint(-0.3, 4.1);
		startPath = new PathFollowCommand(startPoints, 3.0);

		addParallel(new MoveServo((double) config.get("servo_Final_Angle"), false));
		addSequential(startPath);
		addSequential(new HolderEjection());
		addSequential(new ElevatorMoveToBottom());
		addSequential(new DriveDistance(-0.5), 1.5);
		addSequential(new TurnByGyroBB(54.0));
		addSequential(new DriveDistance(2.1), 3.0);
		addSequential(new TurnByGyroBB(true, 10.0));
		addSequential(new DriveDistance(1.2), 2.0);
		addSequential(new TurnByGyroBB(true, 153.0));
		addParallel(new DriveDistance(1.0), 1.0);
		addSequential(new CollectCube());
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
