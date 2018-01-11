package org.usfirst.frc.team3316.robot.paths;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PathsCommandGroup extends CommandGroup {

    public PathsCommandGroup() {
        addSequential(new Path1());
//        addSequential(new Path2());
    }
}
