package org.usfirst.frc.team3316.robot.paths;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.commands.SetPathPIDController;
import org.usfirst.frc.team3316.robot.util.falcon.FalconPathPlanner;
import org.usfirst.frc.team3316.robot.util.falcon.PathPoints;
import org.usfirst.frc.team3316.robot.util.gen.Utils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team3316.robot.util.falcon.*;

/**
 *
 */
public class Path2 extends Command {

    FalconPathPlanner path;
    Command cmd = null;
    
    int i = 0;

    public Path2() {
	requires(Robot.none);
	
	PathPoints waypoints = new PathPoints(); // Object using to store all
						 // the points

	/*
	 * The place where you can change the path (point values in meters)
	 */
	
	waypoints.addPathPoint(1.0, 2.2);
	waypoints.addPathPoint(0.0, 2.2);
	
	/*
	 * The end of that place
	 */

	path = new FalconPathPlanner(waypoints.getPathPoints());
	path.calculate(RobotMap.pf_total_time, RobotMap.pf_step_time, RobotMap.PF_ROBOT_TRACK_WIDTH);

	// printLinePlot("Path", "X (feet)", "Y (feet)", path.smoothPath);
    }

    protected void initialize() {
	cmd = (new SetPathPIDController(Utils.convertFootToMeter(path.smoothLeftVelocity[0][1]),
		Utils.convertFootToMeter(path.smoothRightVelocity[0][1]), path.heading[0][1], path));
	
	cmd.start();
	
	i = 0;
    }
    
    protected void execute() {
	i++; // Using to assure command is not interrupted before cmd starts
    }
    
    protected boolean isFinished() {
	return (!cmd.isRunning() && cmd != null && i > 1);
    }
    
    protected void end() {
	cmd.cancel();
	cmd = null;
    }
    
    protected void interrupted() {
	end();
    }

    private void printLinePlot(String title, String xTitle, String yTitle, double[][] data) {
	if (!GraphicsEnvironment.isHeadless()) {
	    FalconLinePlot fig = new FalconLinePlot(data, null, Color.blue);
	    fig.yGridOn();
	    fig.xGridOn();
	    fig.setYLabel(yTitle);
	    fig.setXLabel(xTitle);
	    fig.setTitle(title);
	}
    }

}
