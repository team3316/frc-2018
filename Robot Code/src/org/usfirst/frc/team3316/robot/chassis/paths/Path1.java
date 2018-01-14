package org.usfirst.frc.team3316.robot.chassis.paths;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.chassis.SetPathPIDController;
import org.usfirst.frc.team3316.robot.utils.Utils;
import org.usfirst.frc.team3316.robot.utils.falcon.FalconLinePlot;
import org.usfirst.frc.team3316.robot.utils.falcon.FalconPathPlanner;
import org.usfirst.frc.team3316.robot.utils.falcon.PathPoints;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 *
 */
public class Path1 extends DBugCommand {

    FalconPathPlanner path;
    Command cmd = null;
    
    int i = 0;

    public Path1() {
	requires(Robot.emptySubsystem);
	PathPoints waypoints = new PathPoints(); // Object using to store all
						 // the points

	/*
	 * The place where you can change the path (point values in meters)
	 */
	waypoints.addPathPoint(2.0,2.0);
	waypoints.addPathPoint(2.0, 3.5);
	waypoints.addPathPoint(0.0, 3.5);


	path = new FalconPathPlanner(waypoints.getPathPoints());
	path.calculate((double)config.get("chassis_PF_Totaltime"), (double)config.get("chassis_PF_Steptime"), (double)config.get("CHASSIS_PF_TRACKWIDTH"));

	// printLinePlot("Path", "X (feet)", "Y (feet)", path.smoothPath);
    }

    protected void init() {
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
    
    protected void fin() {
	cmd.cancel();
	cmd = null;
    }
    
    protected void interr() {
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
