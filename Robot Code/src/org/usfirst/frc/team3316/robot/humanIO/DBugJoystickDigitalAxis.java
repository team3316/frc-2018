package org.usfirst.frc.team3316.robot.humanIO;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DBugJoystickDigitalAxis {

    private class whileHeldCommand extends TimerTask {
	boolean lastValue = false;
	Command cmd;

	public whileHeldCommand(Command cmd) {
	    this.cmd = cmd;
	}

	@Override
	public void run() {
	    if (get() != lastValue) {
		if (get()) {
		    cmd.start();
		} else {
		    cmd.cancel();
		}
	    }

	    lastValue = get();
	}

    }
    
    private class whenPressedCommand extends TimerTask {
	boolean lastValue = false;
	Command cmd;
	int counter;

	public whenPressedCommand(Command cmd) {
	    this.cmd = cmd;
	    counter = 0;
	}

	@Override
	public void run() {
	    if (get()) {
		if (cmd.isRunning()) {
		    counter++;
		    if (counter > 15) {
			cmd.start();
		    }
		}
		else {
		    counter = 0;
		}
	    }
	    else {
		counter = 0;
	    }

	    lastValue = get();
	}

    }

    private Joystick joystick;
    private int axis;
    private double switchLimit;

    public DBugJoystickDigitalAxis(Joystick joystick, int axis, double switchLimit) {
	this.joystick = joystick;
	this.axis = axis;
    }

    public boolean get() {
	return joystick.getRawAxis(axis) > switchLimit;
    }

    public void whenPressed(Command cmd) {
	Robot.timer.schedule(new whenPressedCommand(cmd), 0, 20);
    }

    public void whileHeld(Command cmd) {
	Robot.timer.schedule(new whileHeldCommand(cmd), 0, 20);
    }
}
