package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;

public enum HolderRollType {
	RollIn (1),
	RollOut (2),
	Stop (3),
	Directional (4);

	private int type;

	private HolderRollType (int type) {
		this.type = type;
	}

	/**
	 * The config key for the left motor voltage amount.
	 * @return The config key
	 */
	private String leftConfigKey () {
		switch (this.type) {
		case 1: return "holder_rollIn_voltage";
		case 2: return "holder_rollOut_voltage";
		case 4: return "holder_directionalRollIn_leftVoltage";
		default: return "";
		}
	}

	/**
	 * The config key for the right motor voltage amount.
	 * @return The config key
	 */
	private String rightConfigKey () {
		switch (this.type) {
		case 1: return "holder_rollIn_voltage";
		case 2: return "holder_rollOut_voltage";
		case 4: return "holder_directionalRollIn_rightVoltage";
		default: return "";
		}
	}
	
	/**
	 * Gets the left voltage amount from the config according to the command type
	 * @return The left voltage
	 */
	public double getLeftVoltage () {
		String key = this.leftConfigKey();
		return key == "" ? 0.0 : (double) Robot.config.get(key);
	}

	/**
	 * Gets the right voltage amount from the config according to the command type
	 * @return The right voltage
	 */
	public double getRightVoltage () {
		String key = this.rightConfigKey();
		return key == "" ? 0.0 : (double) Robot.config.get(key);
	}
}
