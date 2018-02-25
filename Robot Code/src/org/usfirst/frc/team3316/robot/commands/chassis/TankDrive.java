package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.humanIO.Joysticks.JoystickType;

/**
 * A tank drive command that uses the Logitech controllers.
 */
public class TankDrive extends Drive {
	public TankDrive() {
		super(JoystickType.Logitech);
	}
}
