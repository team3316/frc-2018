package org.usfirst.frc.team3316.robot.auton.sequences;

import org.usfirst.frc.team3316.robot.auton.commands.AutonMode;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;

public abstract class AutonPosition extends DBugCommandGroup {
	protected AutonMode mode;
	protected SwitchScaleType switchType, scaleType;
	protected CubeState cubeType;
	
	public void setMode(AutonMode mode) {
		this.mode = mode;
	}
	
	public void setCubeType(CubeState type) {
		this.cubeType = type;
	}
	
	public void setSwitchType(SwitchScaleType type) {
		this.switchType = type;
	}
	
	public void setScaleType(SwitchScaleType type) {
		this.scaleType = type;
	}
	
	abstract public void analizeMode();
}
