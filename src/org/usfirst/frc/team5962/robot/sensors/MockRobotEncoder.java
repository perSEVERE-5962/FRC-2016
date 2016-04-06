package org.usfirst.frc.team5962.robot.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
public class MockRobotEncoder extends RobotEncoder {
	double distance = 0.0;
	public double getDistance() {
		distance++;
		SmartDashboard.putString("Encoder Distance", "" + distance);
		return distance;
	}
	public void reset() {
		distance=0;
	}

}
