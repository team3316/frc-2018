package org.usfirst.frc.team3316.robot.vision;

import java.net.*;

import org.json.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionServer implements Runnable {
	public static boolean isConnected = false, isObjectDetected;
	public static double azimuthAngle, distanceFromCube;

	public void run() {
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(8000);
		} catch (SocketException e) {
			System.err.println("Error with creating the UDP Socket.");
		}

		byte[] receiveData = new byte[80];

		while (true) {
			SmartDashboard.putBoolean("is connected", isConnected);

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.setSoTimeout(100);
				serverSocket.receive(receivePacket);

				isConnected = true;

				String sentence = new String(receivePacket.getData());
				JSONObject parsed = new JSONObject(sentence);

				VisionServer.azimuthAngle = parsed.getDouble("AA");
				VisionServer.distanceFromCube = parsed.getDouble("DFC");
				VisionServer.isObjectDetected = parsed.getInt("IOD") == 1;
			} catch (Exception e) {
				// TODO - Add throttling
				isConnected = false;
			}
		}
	}
}
