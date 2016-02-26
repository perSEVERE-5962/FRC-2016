package org.usfirst.frc.team5962.robot.sensors;

import edu.wpi.first.wpilibj.Encoder;

public class RobotEncoder {
	// encoders
	Encoder encoderLeft;
	Encoder encoderRight;

	public RobotEncoder() {
		encoderLeft = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		encoderRight = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		encoderLeft.setDistancePerPulse(0.035); // inches
	}
	
	public void reset() {
		encoderLeft.reset();
		encoderRight.reset();
	}

	public double getLeftDistance() {

		return encoderLeft.getDistance();
	}

	public double getRightDistance() {

		return encoderRight.getDistance();
	}
}
