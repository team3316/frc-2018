
import java.util.Hashtable;

public class Config {
	public static Hashtable<String, Object> variablesB;
	public static Hashtable<String, Object> constantsB;

	public static Hashtable<String, Object> variablesA;
	public static Hashtable<String, Object> constantsA;

	private Config() {
	}

	static {
		variablesB = new Hashtable<String, Object>();
		constantsB = new Hashtable<String, Object>();

		variablesA = new Hashtable<String, Object>();
		constantsA = new Hashtable<String, Object>();

		initConfig();
		IO.initIO();
	}

	public static void addToConstantsA(String key, Object value) {
		System.out.println("Trying to add to constants A: key " + key + " value " + value);

		if (constantsA.containsKey(key)) {
			constantsA.replace(key, value);
		} else {
			constantsA.put(key, value);
		}
	}

	public static void addToVariablesA(String key, Object value) {
		System.out.println("Trying to add to variables A: key " + key + " value " + value);

		if (variablesA.containsKey(key)) {
			variablesA.replace(key, value);
		} else {
			variablesA.put(key, value);
		}
	}

	public static void addToConstantsB(String key, Object value) {
		System.out.println("Trying to add to constants B: key " + key + " value " + value);

		if (constantsB.containsKey(key)) {
			constantsB.replace(key, value);
		} else {
			constantsB.put(key, value);
		}
	}

	public static void addToVariablesB(String key, Object value) {
		System.out.println("Trying to add to variables B: key " + key + " value " + value);

		if (variablesB.containsKey(key)) {
			variablesB.replace(key, value);
		} else {
			variablesB.put(key, value);
		}
	}

	public static void addToConstants(String key, Object value) {
		addToConstantsA(key, value);
		addToConstantsB(key, value);
	}

	public static void addToVariables(String key, Object value) {
		addToVariablesA(key, value);
		addToVariablesB(key, value);
	}

	/*
	 * NOTE: constants and variables that are common to both robot A and robot B
	 * should be added with addToConstants() or addToVariables()
	 * 
	 * Use different constants and variables for the two robots only if there is a
	 * difference. TestModeStuff
	 */
	private static void initConfig() {
		/*
		 * Human IO
		 */
		{
			/*
			 * Constants
			 */
			{
				/*
				 * Joysticks
				 */
				{
					addToConstants("JOYSTICK_LEFT", 0);
					addToConstants("JOYSTICK_RIGHT", 1);
					addToConstants("JOYSTICK_OPERATOR", 2);
				}

				/*
				 * Buttons and axis
				 */
				{
					// Joystick operator
				    addToVariables("button_Elevetor_Top", 4);
					addToVariables("button_Elevetor_Bottom", 1);
					addToVariables("button_Elevetor_Switch", 2);
					addToVariables("button_Elevetor_Scale", 3);
					
					addToVariables("button_Elevator_Joystick_Toggle", 10);
					addToVariables("elevator_Joystick_Axis", 5);

					addToVariables("button_Collection", 6);
					addToVariables("button_Ejection", 5);
					
					addToVariables("button_Holder_RollIn", 8);
					addToVariables("button_Holder_RollOut", 7);
					
					addToVariables("button_Chassis_Break_Toggle", 1);
					addToVariables("button_Chassis_DriveOneAxis", 3);
					addToVariables("axis_Chassis_DriveOneAxis1", 3);
				}
			}
		}

		/*
		 * RobotIO
		 */
		{
			/*
			 * Constants
			 */
			addToConstants("CURRENT_CONTROL_COUNTER", 10);

			{
				/*
				 * Chassis
				 */
				addToConstantsA("CHASSIS_MOTOR_LEFT_REVERSE", false);
				addToConstantsA("CHASSIS_MOTOR_RIGHT_REVERSE", true);

				addToConstantsB("CHASSIS_MOTOR_LEFT_REVERSE", true);
				addToConstantsB("CHASSIS_MOTOR_RIGHT_REVERSE", false);

				addToVariables("chassis_Joystick_Right_Axis", 1);
				addToVariables("chassis_Joystick_Left_Axis", 5);

				addToConstants("CHASSIS_LEFT_ENCODER_REVERSE", false);
				addToConstants("CHASSIS_RIGHT_ENCODER_REVERSE", true);

				addToConstants("CHASSIS_ENCODERS_DISTANCE_PER_PULSE", 0.00124224); // in meters
			}

			{
				/*
				 * Elevator
				 */
				addToConstants("ELEVATOR_ENCODER_DISTANCE_PER_PULSE", 0.0008736); // in meters
				addToConstants("ELEVATOR_MOTORS_REVERSE", false);
			}
		}

		/*
		 * Chassis
		 */
		{
			/*
			 * Constants
			 */
			{
			}

			/*
			 * Variables
			 */
			{
				addToVariables("chassis_TankDrive_DeadBand", 0.05);

				addToVariables("chassis_SpeedFactor_Higher", -1.0);
				addToVariables("chassis_SpeedFactor_Lower", -0.3);
				addToVariables("chassis_SpeedFactor_Current", -1.0);

				addToVariables("chassis_TankDrive_InvertX", true);
				addToVariables("chassis_TankDrive_InvertY", false); // false value for xbox

				addToVariables("chassis_LowPassFilter_LowestValue", 0.05);
			}

			/*
			 * Drive Distance
			 */
			{
				addToVariables("chassis_DriveDistance_PID_Tolerance", 0.025);

				// PID
				// BY CAMERA
				addToVariables("chassis_DriveByCamera_PID_KP", 0.0);
				addToVariables("chassis_DriveByCamera_PID_KI", 0.0);
				addToVariables("chassis_DriveByCamera_PID_KD", 0.0);

				// BY ENCODERS
				{
					// Drive
					addToVariables("chassis_DriveDistance_PID_DRIVE_KP", 670.0);
					addToVariables("chassis_DriveDistance_PID_DRIVE_KI", 0.0);
					addToVariables("chassis_DriveDistance_PID_DRIVE_KD", 0.0);

					// Yaw
					addToVariables("chassis_DriveDistance_PID_YAW_KP", 270.0);
					addToVariables("chassis_DriveDistance_PID_YAW_KI", 0.0);
					addToVariables("chassis_DriveDistance_PID_YAW_KD", 0.0);
					addToVariables("chassis_DriveDistance_PID_YAW_KF", 0.0);
				}
			}

			/*
			 * TURN BY GYRO
			 */
			{
				// PID
				addToVariables("chassis_TurnByGyro_PID_Tolerance", 2.0);

				addToVariables("chassis_TurnByGyro_PID_KP", 100.0);
				addToVariables("chassis_TurnByGyro_PID_KI", 0.0);
				addToVariables("chassis_TurnByGyro_PID_KD", 300.0);

				addToVariables("chassis_TurnByGyro_VelocityFilter", 0.05);
			}

			/*
			 * Set Constant Voltage
			 */
			{
				addToVariables("chassis_SetConstantVoltage_Voltage", 0.0);
			}
			
			/*
			 * SPEED PID
			 */
			{
			    addToVariables("chassis_Speed_PID_Left_KP", 10.0 / 1000.0);
			    addToVariables("chassis_Speed_PID_Left_KD", 0.0);
			    addToVariables("chassis_Speed_PID_Left_KI", 0.0);
			    addToVariables("chassis_Speed_PID_Left_KF", 0.0);
			    addToVariables("chassis_Speed_PID_Right_KP", 10.0 / 1000.0);
			    addToVariables("chassis_Speed_PID_Right_KI", 0.0);
			    addToVariables("chassis_Speed_PID_Right_KD", 0.0);
			    addToVariables("chassis_Speed_PID_Right_KF", 0.0);
			    
			    addToVariables("chassis_Speed_PID_Tolerance", 0.05);
			}
			
			/*
			 * YAW PID
			 */
			{
			    addToVariables("chassis_Yaw_PID_KP", 50.0 / 1000.0);
			    addToVariables("chassis_Yaw_PID_KD", 0.0);
			    addToVariables("chassis_Yaw_PID_KI", 0.0);
			}
			
			/*
			 * PATH FOLLOWER
			 */
			{
			    addToVariables("chassis_PF_Steptime", 0.1); // in seconds
			    addToVariables("chassis_PF_Totaltime", 5.0); // in seconds
			    
			    addToConstants("CHASSIS_PF_TRACKWIDTH", 1.94); // in feet
			}

		}
		
		/*
		 * Intake
		 */
		{
			addToVariables("intake_rollIn_voltage", 0.7);
			addToVariables("intake_rollOut_voltage", -1.0);
			addToVariables("intake_shaken_stepTime", 750.0);
			addToVariables("intake_shaken_stopTime", 200.0);
			addToVariables("intake_directionalRollIn_leftVoltage", 1.0);
			addToVariables("intake_directionalRollIn_rightVoltage", 0.4);
		}
		
		
		/*
		 * Holder
		 */
		{
			addToVariables("holder_rollIn_voltage", -0.85);
			addToVariables("holder_rollOut_voltage", 1.0);
		}

		/*
		 * Holder + intake sequences
		 */
		{
			addToVariables("cubeWait_stall_time", 500.0);
		}

		/*
		 * Elevator
		 */
		{
			addToVariables("elevator_setpoint_bottom", 0.0);
			addToVariables("elevator_setpoint_switch", 1.7);
			addToVariables("elevator_setpoint_scale", 1.8);
			addToVariables("elevator_setpoint_top", 1.92);
			addToVariables("elevator_level_tolerance", 0.07);
			
			/*
			 * Elevator to Level
			 */
			{
			    // PID
			    addToVariables("elevator_PID_KP", 400.0);
			    addToVariables("elevator_PID_KI", 7.0);
			    addToVariables("elevator_PID_KD", 60.0);
			    
			    // BANG BANG
			    addToVariables("elevator_PID_Tolerance", 0.005);
			    addToVariables("elevator_BangBang_UpVoltage", 0.4);
			    addToVariables("elevator_BangBang_DownVoltage", -0.4);
			    
			    // SHAKEN
			    addToVariables("elevator_Shaken_Tolerance", 0.001);
			    addToVariables("elevator_Shaken_Voltage", 0.15);
			}
			
			/*
			 * ElevatorJoystick
			 */
			{
				addToVariables("elevator_Joystick_LowPassVal", 0.2);
			}
			
			/*
			 * Shifter
			 */
			{
			    addToVariables("elevator_Shifter_Delay", 1000.0); // in milliseconds
			}
		}
	}
}
