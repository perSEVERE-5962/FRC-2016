package org.usfirst.frc.team5962.robot.sensors;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotEncoder {
	// encoders
	Encoder encoderLeft;
	Encoder encoderRight;
	
	int numberOfEncoders = 2;

	public RobotEncoder() {
		encoderLeft = new Encoder(0, 1, true, Encoder.EncodingType.k4X);
		encoderRight = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		encoderLeft.setDistancePerPulse(0.027); // inches .027 for treads
												// inches .035 for small wheels
												// inches .02? for big wheels
		encoderRight.setDistancePerPulse(0.027); 	
	}
	
	public void setNumberOfEncoders(int numberOfEncoders) {
		this.numberOfEncoders = numberOfEncoders;
	}
	
	public void reset() {
		encoderLeft.reset();
		encoderRight.reset();
	}
//Left encoder
//	public double getLeftDistance() {
//		double distance = encoderLeft.getDistance();
//		return distance;
//	}
//
//	public double getRightDistance() {
//		double distance = encoderRight.getDistance();
//		return distance;
//	}
	
	public double getDistance() {
		SmartDashboard.putString("Left Encoder Distance", "" + encoderLeft.getDistance());					
		SmartDashboard.putString("Right Encoder Distance", "" + encoderRight.getDistance());					
		double distance = ((encoderLeft.getDistance() + encoderRight.getDistance()) / numberOfEncoders);
		//double distance = (encoderLeft.getDistance());
		SmartDashboard.putString("Encoder Distance", "" + distance);
		return distance;
	}
}
