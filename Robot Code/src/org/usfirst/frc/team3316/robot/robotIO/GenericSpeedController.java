package org.usfirst.frc.team3316.robot.robotIO;

import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

public class GenericSpeedController {
    private SpeedController sc;
    private TalonSRX scSRX;
    
    private boolean isSRX;
    
    public GenericSpeedController(Object sc) {
	if (sc instanceof TalonSRX) {
	    this.scSRX = (TalonSRX)sc;
	    this.isSRX = true;
	}
	else if (sc instanceof SpeedController) {
	    this.sc = (SpeedController)sc;
	    this.isSRX = false;
	}
    }
    
    public boolean isTalonSRX() {
	return isSRX;
    }
    
    public void set(double value) {
	if (isTalonSRX()) {
	    this.scSRX.set(value);
	}
	else if (!isTalonSRX()) {
	    this.sc.set(value);
	}
    }
    
    public void setInverted(boolean isInverted) {
	if (isTalonSRX()) {
	    this.scSRX.setInverted(isInverted);
	}
	else if (!isTalonSRX()) {
	    this.sc.setInverted(isInverted);
	}
    }
    
    /**
     * returns the supplied voltage (just for speed controllers connected with pwm)
     */
    public double get() {
	if (this.isTalonSRX()) {
	    return 0.0;
	} else {
	    return sc.get();
	}
    }
    
    public void enableBrakeMode(boolean isBrake) {
	if (this.isTalonSRX()) {
	    scSRX.enableBrakeMode(isBrake);
	}
    }
}
