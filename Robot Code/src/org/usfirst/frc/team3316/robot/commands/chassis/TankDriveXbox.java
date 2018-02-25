package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.humanIO.Joysticks.JoystickType;

/**
 * A tank drive command that uses the Xbox controller.
 */
public class TankDriveXbox extends Drive {
	public TankDriveXbox() {
		super(JoystickType.Xbox);
	}
}
