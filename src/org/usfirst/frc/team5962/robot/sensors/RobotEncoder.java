package org.usfirst.frc.team5962.robot.sensors;

import edu.wpi.first.wpilibj.Encoder;

public class RobotEncoder {
	// encoders
	Encoder encoderLeft;
	Encoder encoderRight;

	public RobotEncoder() {
		encoderLeft = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		encoderRight = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		encoderLeft.setDistancePerPulse(0.027); // inches .027 for treads
		encoderRight.setDistancePerPulse(0.027); // inches .035 for treads	
	}
	
	public void reset() {
		encoderLeft.reset();
		encoderRight.reset();
	}
//Left encoder
	public double getLeftDistance() {

		return encoderLeft.getDistance();
	}

	public double getRightDistance() {

		return encoderRight.getDistance();
	}
	
	public double getDistance(){
		return ((encoderLeft.getDistance() + encoderRight.getDistance()) / 2);
	}
}
