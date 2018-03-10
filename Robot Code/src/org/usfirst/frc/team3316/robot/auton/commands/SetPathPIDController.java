package org.usfirst.frc.team3316.robot.auton.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.PIDControllers;
import org.usfirst.frc.team3316.robot.utils.Utils;
import org.usfirst.frc.team3316.robot.utils.falcon.FalconPathPlanner;

import edu.wpi.first.wpilibj.PIDController;

/**
 *
 */
public class SetPathPIDController extends DBugCommand {

	private PIDController pidLeft, pidRight, pidYaw;
	private double setpointLeft, setpointRight, setpointYaw;

	private FalconPathPlanner path;

	private int i = 1;

	private long lastTime;

	public SetPathPIDController(double setpointLeft, double setpointRight, double setpointYaw, FalconPathPlanner path) {
		requires(Robot.chassis);
		requires(Robot.pidControllers);

		// Setting values
		this.setpointLeft = setpointLeft;
		this.setpointRight = setpointRight;
		this.setpointYaw = setpointYaw;

		this.path = path;
	}

	// Called just before this Command runs the first time
	protected void init() {
		Robot.chassis.resetYaw();
		// PID Left
		pidLeft = PIDControllers.getSpeedPID(true, (double) config.get("chassis_Speed_PID_Left_KP"),
				(double) config.get("chassis_Speed_PID_Left_KI"), (double) config.get("chassis_Speed_PID_Left_KD"),
				(double) config.get("chassis_Speed_PID_Left_KF"));

		pidLeft.setAbsoluteTolerance((double) config.get("chassis_Speed_PID_Tolerance"));
		pidLeft.setSetpoint(this.setpointLeft);
		pidLeft.setOutputRange(-1.0, 1.0);

		// PID Right
		pidRight = PIDControllers.getSpeedPID(false, (double) config.get("chassis_Speed_PID_Right_KP"),
				(double) config.get("chassis_Speed_PID_Right_KI"), (double) config.get("chassis_Speed_PID_Right_KD"),
				(double) config.get("chassis_Speed_PID_Right_KF"));

		pidRight.setAbsoluteTolerance((double) config.get("chassis_Speed_PID_Tolerance"));
		pidRight.setSetpoint(this.setpointRight);
		pidRight.setOutputRange(-1.0, 1.0);

		// PID Yaw
		pidYaw = PIDControllers.getYawPID((double) config.get("chassis_Yaw_PID_KP"),
				(double) config.get("chassis_Yaw_PID_KI"), 0.0, 0.0);
		pidYaw.setSetpoint(this.setpointYaw);
		pidYaw.setOutputRange(-1.0, 1.0);

		pidLeft.enable();

		pidRight.enable();

		pidYaw.enable();

		lastTime = System.currentTimeMillis();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTime >= (long) ((double) config.get("chassis_PF_Steptime") * 1000)
				&& i < path.smoothCenterVelocity.length) {
			pidLeft.setSetpoint(Utils.convertFootToMeter(path.smoothLeftVelocity[i][1]));
			pidRight.setSetpoint(Utils.convertFootToMeter(path.smoothRightVelocity[i][1]));

			pidYaw.setSetpoint(-(path.heading[i][1] - 90));

			i++;
			lastTime = currentTime;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return i >= path.smoothCenterVelocity.length;
	}

	// Called once after isFinished returns true
	protected void fin() {
		pidLeft.disable();

		pidRight.disable();

		pidYaw.disable();

		Robot.chassis.setMotors(0.0, 0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interr() {
		end();
	}
}
