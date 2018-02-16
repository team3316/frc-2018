package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;

public class HolderCollection extends DBugCommandGroup {

    public HolderCollection() {
	addParallel(new HolderRoll(HolderRollType.RollIn));
	addSequential(new WaitForCubeIn());
	addParallel(new HolderRoll(HolderRollType.Stop));
    }

}
