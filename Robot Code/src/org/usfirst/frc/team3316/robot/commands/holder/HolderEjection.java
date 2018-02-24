package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;

public class HolderEjection extends DBugCommandGroup {

	public HolderEjection() {
		addParallel(new HolderRoll(HolderRollType.RollOut));
		addSequential(new WaitForCubeOut());
		addParallel(new HolderRoll(HolderRollType.Stop), 0.1);
	}

}
