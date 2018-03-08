package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.elevator.ElevatorMoveToEdge;
import org.usfirst.frc.team3316.robot.commands.holder.HolderCollection;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRoll;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollType;
import org.usfirst.frc.team3316.robot.subsystems.Elevator.Level;

public class EjectCube extends DBugCommandGroup {

    public EjectCube() {
	addParallel(new IntakeRoll(IntakeRollType.RollOut));
		addSequential(new HolderEjection());
	addParallel(new IntakeRoll(IntakeRollType.Stop), 0.1);
    }

}
