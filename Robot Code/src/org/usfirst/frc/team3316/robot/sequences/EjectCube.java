package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.holder.HolderEjection;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRoll;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollType;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeStop;

public class EjectCube extends DBugCommandGroup {
	public EjectCube() {
		addParallel(new IntakeRoll(IntakeRollType.RollOut));
		addSequential(new HolderEjection());
		addParallel(new IntakeStop(), 0.1);
	}
}
