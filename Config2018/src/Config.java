
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
				 * Buttons
				 */
				{
					// Joystick operator

					addToVariables("button_Intake_Toggle", 5);

					addToVariables("button_Chassis_Break_Toggle", 1);
					addToVariables("button_Chassis_DriveOneAxis", 3);
					addToVariables("axis_Chassis_DriveOneAxis1", 3);
					addToVariables("axis_Chassis_DriveOneAxis2", 2);
					addToVariables("axis_Chassis_SwitchLimit", 0.5);
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

				addToVariables("chassis_Joystick_Right_Axis", 1);
				addToVariables("chassis_Joystick_Left_Axis", 5);

				addToConstants("CHASSIS_LEFT_ENCODER_REVERSE", false);
				addToConstants("CHASSIS_RIGHT_ENCODER_REVERSE", true);

				addToConstants("CHASSIS_ENCODERS_DISTANCE_PER_PULSE", 0.00124224); // in
				// meters
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
				addToVariables("chassis_SpeedFactor_Current", -0.95); // the default value

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
					// Right
					addToVariables("chassis_DriveDistance_PID_RIGHT_KP", 0.0);
					addToVariables("chassis_DriveDistance_PID_RIGHT_KI", 0.0);
					addToVariables("chassis_DriveDistance_PID_RIGHT_KD", 0.0);
					// Left
					addToVariables("chassis_DriveDistance_PID_LEFT_KP", 0.0);
					addToVariables("chassis_DriveDistance_PID_LEFT_KI", 0.0);
					addToVariables("chassis_DriveDistance_PID_LEFT_KD", 0.0);

					// Yaw
					addToVariables("chassis_DriveDistance_PID_YAW_KP", 270.0);
					addToVariables("chassis_DriveDistance_PID_YAW_KI", 0.0);
					addToVariables("chassis_DriveDistance_PID_YAW_KD", 0.0);
				}
			}

			/*
			 * TURN BY GYRO
			 */
			{
				// PID
				addToVariables("chassis_TurnByGyro_PID_Tolerance", 1.0);

				addToVariables("chassis_TurnByGyro_PID_KP", 82.0);
				addToVariables("chassis_TurnByGyro_PID_KI", 0.7);
				addToVariables("chassis_TurnByGyro_PID_KD", 5.0);
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
			    addToVariables("chassis_Speed_PID_Left_KP", 40.0 / 1000.0);
			    addToVariables("chassis_Speed_PID_Left_KD", 0.0);
			    addToVariables("chassis_Speed_PID_Left_KI", 0.0);
			    addToVariables("chassis_Speed_PID_Left_KF", 0.0);
			    addToVariables("chassis_Speed_PID_Right_KP", 57.0 / 1000.0);
			    addToVariables("chassis_Speed_PID_Right_KI", 0.0);
			    addToVariables("chassis_Speed_PID_Right_KD", 0.0);
			    addToVariables("chassis_Speed_PID_Right_KF", 0.0);
			    
			    addToVariables("chassis_Speed_PID_Tolerance", 0.05);
			}
			
			/*
			 * YAW PID
			 */
			{
			    addToVariables("chassis_Yaw_PID_KP", 90.0 / 1000.0);
			    addToVariables("chassis_Yaw_PID_KD", 0.0);
			    addToVariables("chassis_Yaw_PID_KI", 0.0);
			}
			
			/*
			 * PATH FOLLOWER
			 */
			{
			    addToVariables("chassis_PF_Steptime", 0.1); // in seconds
			    addToVariables("chassis_PF_Totaltime", 6.0); // in seconds
			    
			    addToConstants("CHASSIS_PF_TRACKWIDTH", 1.804); // in feet
			}

		}
	}
}
