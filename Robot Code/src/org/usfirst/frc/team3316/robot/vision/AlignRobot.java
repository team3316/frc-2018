package org.usfirst.frc.team3316.robot.vision;

public class AlignRobot {
	/**
	 * Retrieves the azimuth angle of the robot in respect to the detected cube.
	 * @return The azimuth angle
	 */
	public static double getCubeAngle() {
		return VisionServer.Data.get("AA"); // AA = Azimuthal Angle
	}

	/**
	 * Retrieves the distance of the robot from the detected cube.
	 * @return The distance from the cube
	 */
	public static double getDistanceFromCube() {
		return VisionServer.Data.get("DFC"); // DFC = Distance From Cube
	}

	/**
	 * Checks whether the robot has detected a cube at all.
	 * @return A boolean indicating whether the robot has detected a cube
	 */
	public static boolean isObjectDetected() {
		try {
			return VisionServer.Data.get("IOD") == 1.0;
		} catch (Exception e) {
			return false;
		}
	}
}
