package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DBugToggleCommand extends DBugCommand {
	/**
	 * First pressing activates cmd 1 and the second activates cmd 2 Note: This
	 * does not work on whileHeld
	 */

	private Command cmd1, cmd2;
	private static boolean toggle = false; // What command will run next
	private static int counter = 0;

	public DBugToggleCommand(Command cmd1, Command cmd2) {
		this.cmd1 = cmd1;
		this.cmd2 = cmd2;
	}

	public DBugToggleCommand(Command cmd) {
		this.cmd1 = cmd;
	}

	protected void init() {
		counter++;

		if (cmd2 != null)
			if (toggle) {
				cmd1.cancel();
				cmd2.start();
				toggle = false;
			} else {
				cmd2.cancel();
				cmd1.start();
				toggle = true;
			}
		else if (toggle) {
			cmd1.cancel();
			toggle = false;
		} else {
			cmd1.start();
			toggle = true;
		}

		SmartDashboard.putNumber("counter", counter);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void fin() {}

	protected void interr() {
		fin();
	}
}
