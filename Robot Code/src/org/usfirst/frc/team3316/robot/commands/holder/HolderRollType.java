package org.usfirst.frc.team3316.robot.commands.holder;

import org.usfirst.frc.team3316.robot.Robot;

public enum HolderRollType {
	RollIn(1), RollOut(2), Stop(3);

	private int type;

	private HolderRollType(int type) {
		this.type = type;
	}

	/**
	 * The config key for the motor voltage amount.
	 * 
	 * @return The config key
	 */
	private String configKey() {
		switch (this.type) {
		case 1:
			return "holder_rollIn_voltage";
		case 2:
			return "holder_rollOut_voltage";
		case 3:
			return "holder_stop_voltage";
		default:
			return "";
		}
	}

	/**
	 * Gets the voltage amount from the config according to the command type
	 * 
	 * @return The output voltage
	 */
	public double getVoltage() {
		String key = this.configKey();
		// TODO: Ask Barak whether this should be in the config or not.
		if (key == "holder_stop_voltage") return 0.0;
		return key == "" ? Double.NaN : (double) Robot.config.get(key);
	}
}
