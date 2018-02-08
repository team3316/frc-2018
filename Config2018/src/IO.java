import java.util.HashMap;
import java.util.Map;

public class IO {
	public static Map<String, Integer> pwmA = new HashMap<>();
	public static Map<String, Integer> canA = new HashMap<>();

	public static Map<String, Integer> pwmB = new HashMap<>();
	public static Map<String, Integer> canB = new HashMap<>();

	public static Map<String, Integer> dioA = new HashMap<>();
	public static Map<String, Integer> dioB = new HashMap<>();

	public static Map<String, Integer> aioA = new HashMap<>();
	public static Map<String, Integer> aioB = new HashMap<>();

	public static Map<String, Integer> pdpA = new HashMap<>();
	public static Map<String, Integer> pdpB = new HashMap<>();

	/**
	 * Finds the key that is mapped to a specified channel in the parameter map.
	 * 
	 * @param in
	 *            The map we want to search in.
	 * @param channel
	 *            The specified channel.
	 * @return The key that is mapped to the requested channel. If none exists the
	 *         method returns null.
	 */
	private static Object findKey(Map<?, ?> in, Object value) {
		for (Object key : in.keySet()) {
			if (in.get(key).equals(value)) {
				return key;
			}
		}
		return null;
	}

	/**
	 * Puts a mapping of a key to a channel in the requested map.
	 * 
	 * @param in
	 *            The map to add the key.
	 * @param name
	 *            The key.
	 * @param channel
	 *            The channel to map the key to.
	 * @throws Exception
	 *             If the channel already has a mapping, throws an exception.
	 */
	private static void put(Map<String, Integer> in, String name, int channel) throws Exception {
		if (in.containsValue(channel)) {
			throw new Exception(
					"Channel " + channel + " for key " + name + " already exists and is: " + findKey(in, channel));
		}

		in.put(name, channel);
		Config.addToConstants(name, channel);
	}

	/**
	 * Puts a mapping of a key to a channel in the requested map of ROBOT A.
	 * 
	 * @param in
	 *            The map to add the key.
	 * @param name
	 *            The key.
	 * @param channel
	 *            The channel to map the key to.
	 * @throws Exception
	 *             If the channel already has a mapping, throws an exception.
	 */
	private static void putA(Map<String, Integer> in, String name, int channel) throws Exception {
		if (in.containsValue(channel)) {
			throw new Exception(
					"Channel " + channel + " for key " + name + " already exists and is: " + findKey(in, channel));
		}

		in.put(name, channel);
		Config.addToConstantsA(name, channel);
	}

	/**
	 * Puts a mapping of a key to a channel in the requested map of ROBOT B.
	 * 
	 * @param in
	 *            The map to add the key.
	 * @param name
	 *            The key.
	 * @param channel
	 *            The channel to map the key to.
	 * @throws Exception
	 *             If the channel already has a mapping, throws an exception.
	 */
	private static void putB(Map<String, Integer> in, String name, int channel) throws Exception {
		if (in.containsValue(channel)) {
			throw new Exception(
					"Channel " + channel + " for key " + name + " already exists and is: " + findKey(in, channel));
		}

		in.put(name, channel);
		Config.addToConstantsB(name, channel);
	}

	/**
	 * Put method for pwm channels on robot A. Read the documentation of the put
	 * method.
	 */
	private static void putPWMA(String name, int channel) throws Exception {
		putA(pwmA, name, channel);
	}

	/**
	 * Put method for can channels on robot A. Read the documentation of the put
	 * method.
	 */
	private static void putCANA(String name, int channel) throws Exception {
		putA(canA, name, channel);
	}

	/**
	 * Put method for pwm channels on robot B. Read the documentation of the put
	 * method.
	 */
	private static void putPWMB(String name, int channel) throws Exception {
		putB(pwmB, name, channel);
	}

	/**
	 * Put method for can channels on robot B. Read the documentation of the put
	 * method.
	 */
	private static void putCANB(String name, int channel) throws Exception {
		putB(canB, name, channel);
	}

	/**
	 * Put method for dio channels. Read the documentation of the put method.
	 */
	private static void putDIOA(String name, int channel) throws Exception {
		putA(dioA, name, channel);
	}

	/**
	 * Put method for dio channels. Read the documentation of the put method.
	 */
	private static void putDIOB(String name, int channel) throws Exception {
		putB(dioB, name, channel);
	}

	/**
	 * Put method for aio channels. Read the documentation of the put method.
	 */
	private static void putAIOA(String name, int channel) throws Exception {
		putA(aioA, name, channel);
	}

	/**
	 * Put method for aio channels. Read the documentation of the put method.
	 */
	private static void putAIOB(String name, int channel) throws Exception {
		putB(aioB, name, channel);
	}

	/**
	 * Put method for pdp channels. Read the documentation of the put method.
	 */
	private static void putPDPA(String name, int channel) throws Exception {
		putA(pdpA, name, channel);
	}

	/**
	 * Put method for pdp channels. Read the documentation of the put method.
	 */
	private static void putPDPB(String name, int channel) throws Exception {
		putB(pdpB, name, channel);
	}

	public static void initIO() {
		try {
			/*
			 * PWM and CAN initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
				    	// Chassis
				    	// Left front
					putCANA("CHASSIS_MOTOR_LEFT_1", 2);
					// Left back
					putCANA("CHASSIS_MOTOR_LEFT_2", 3);
					// Right front
					putCANA("CHASSIS_MOTOR_RIGHT_1", 12);
					// Right back
					putCANA("CHASSIS_MOTOR_RIGHT_2", 13);
					
					// Intake
					// Left
					putPWMA("INTAKE_MOTOR_LEFT", 1);
					// Right
					putPWMA("INTAKE_MOTOR_RIGHT", 2);
					
				    	// Elevator
				    	// Left front
					putCANA("ELEVATOR_MOTOR_1", 14);
					// Left back
					putCANA("ELEVATOR_MOTOR_2", 15);
					
					// Holder
					putPWMA("HOLDER_MOTOR", 0);
				}

				/*
				 * Robot B
				 */
				{
				}
			}

			/*
			 * DIO initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
				    	// Chassis
					putDIOA("CHASSIS_LEFT_ENCODER_CHANNEL_A", 0);
					putDIOA("CHASSIS_LEFT_ENCODER_CHANNEL_B", 1);

					putDIOA("CHASSIS_RIGHT_ENCODER_CHANNEL_A", 3);
					putDIOA("CHASSIS_RIGHT_ENCODER_CHANNEL_B", 2);

					// Elevator
					putDIOA("ELEVATOR_ENCODER_CHANNEL_A", 4);
					putDIOA("ELEVATOR_ENCODER_CHANNEL_B", 5);
					
					// ITS ALL FAKE
					putDIOA("ELEVATOR_BOTTOM_HE", 12);
					putDIOA("ELEVATOR_SWITCH_HE", 13);
					putDIOA("ELEVATOR_SCALE_HE", 14);
					putDIOA("ELEVATOR_TOP_HE", 15);
					putDIOA("ELEVATOR_ENCODER_A", 17);
					putDIOA("ELEVATOR_ENCODER_B", 16);
				}

				/*
				 * Robot B
				 */
				{
				}
			}

			/*
			 * AIO initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
				}

				/*
				 * Robot B
				 */
				{
				}
			}

			/*
			 * PDP initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
					putPDPA("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL", 2);
					putPDPA("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL", 3);
					putPDPA("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL", 12);
					putPDPA("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL", 13);
				}

				/*
				 * Robot B
				 */
				{
				}
			}

			/*
			 * PCM initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
				    Config.addToConstants("ELEVATOR_SHIFTER_FORWARD", 0);
				    Config.addToConstants("ELEVATOR_SHIFTER_REVERSE", 1);
				}

				/*
				 * Robot B
				 */
				{
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
