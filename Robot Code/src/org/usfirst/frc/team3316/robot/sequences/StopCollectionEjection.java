package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRoll;
import org.usfirst.frc.team3316.robot.commands.holder.HolderRollType;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRoll;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollType;

public class StopCollectionEjection extends DBugCommandGroup {

	public StopCollectionEjection() {
		addParallel(new HolderRoll(HolderRollType.Stop), 0.1);
		addParallel(new IntakeRoll(IntakeRollType.Stop), 0.1);
	}

}
