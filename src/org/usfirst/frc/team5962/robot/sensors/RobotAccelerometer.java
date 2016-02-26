package org.usfirst.frc.team5962.robot.sensors;

import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
//Data can't be used very inconsistent

public class RobotAccelerometer {
	Accelerometer accelerometer;
	double accelerationX;
	double accelerationY;
	double accelerationZ;
    
	public RobotAccelerometer() {
		accelerometer = new ADXL345_SPI(SPI.Port.kOnboardCS0, Accelerometer.Range.k4G);
	}
}
