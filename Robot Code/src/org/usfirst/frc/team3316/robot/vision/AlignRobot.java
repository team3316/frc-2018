package org.usfirst.frc.team3316.robot.vision;

public class AlignRobot {

	public static double getCubeAngle() {
		double towerAngle = VisionServer.Data.get("AA"); // AA = Azimuthal Angle
		return (towerAngle);
	}

	/**
	 * This returns the DFC
	 */

	public static double getDistanceFromCube() {
		double distance = VisionServer.Data.get("DFC"); // DFC = Distance From
		// Camera
		return distance;
	}

	public static boolean isObjectDetected() {
		try {
			return VisionServer.Data.get("IOD") == 1.0;
		} catch (Exception e) {
			// logger.severe(e);
			return false;
		}
	}
}
