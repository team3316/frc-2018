package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.humanIO.Joysticks.JoystickType;

public class TankDrive extends Drive {
	public TankDrive() {
		super(JoystickType.Driver);
	}
}
