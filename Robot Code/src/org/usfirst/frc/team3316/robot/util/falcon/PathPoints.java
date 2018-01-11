package org.usfirst.frc.team3316.robot.util.falcon;

import java.util.*;

import org.usfirst.frc.team3316.robot.util.gen.Utils;

public class PathPoints {
	private List<double[]> path;

	public PathPoints() {
		path = new ArrayList<double[]>();
	}

	public void addPathPoint(double x, double y) {
		double[] point = new double[] { Utils.convertMeterToFoot(x), Utils.convertMeterToFoot(y) }; // convert values
																									// from meters to
																									// feet

		path.add(point);
	}

	public double[][] getPathPoints() {
		return listToArray(path);
	}

	// UTIL
	private double[][] listToArray(List list) {
		double[][] toReturn = new double[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			toReturn[i] = (double[]) list.get(i);
		}

		return toReturn;
	}
}
