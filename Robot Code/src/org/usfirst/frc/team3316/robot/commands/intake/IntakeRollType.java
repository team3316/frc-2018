package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;

public enum IntakeRollType {
	RollIn(1), RollOut(2), Stop(3), Directional(4);

	private int type;

	private IntakeRollType(int type) {
		this.type = type;
	}

	/**
	 * The config key for the left motor voltage amount.
	 * 
	 * @return The config key
	 */
	private String leftConfigKey() {
		switch (this.type) {
		case 1:
			return "intake_rollIn_voltage";
		case 2:
			return "intake_rollOut_voltage";
		case 3:
			return "intake_stop_voltage";
		case 4:
			return "intake_directionalRollIn_leftVoltage";
		default:
			return "";
		}
	}

	/**
	 * The config key for the right motor voltage amount.
	 * 
	 * @return The config key
	 */
	private String rightConfigKey() {
		switch (this.type) {
		case 1:
			return "intake_rollIn_voltage";
		case 2:
			return "intake_rollOut_voltage";
		case 3:
			return "intake_stop_voltage";
		case 4:
			return "intake_directionalRollIn_rightVoltage";
		default:
			return "";
		}
	}

	/**
	 * Gets the left voltage amount from the config according to the command type
	 * 
	 * @return The left voltage
	 */
	public double getLeftVoltage() {
		String key = this.leftConfigKey();
		if (key == "intake_stop_voltage") return 0.0;
		return key == "" ? Double.NaN : (double) Robot.config.get(key);
	}

	/**
	 * Gets the right voltage amount from the config according to the command type
	 * 
	 * @return The right voltage
	 */
	public double getRightVoltage() {
		String key = this.rightConfigKey();
		if (key == "intake_stop_voltage") return 0.0;
		return key == "" ? Double.NaN : (double) Robot.config.get(key);
	}
}
