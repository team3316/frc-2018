package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.holder.HolderStop;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeStop;

public class StopCollectionEjection extends DBugCommandGroup {

	public StopCollectionEjection() {
		addParallel(new HolderStop(), 0.1);
		addParallel(new IntakeStop(), 0.1);
	}

}
