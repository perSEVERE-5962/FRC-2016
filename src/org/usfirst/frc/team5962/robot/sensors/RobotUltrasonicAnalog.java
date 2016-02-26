package org.usfirst.frc.team5962.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class RobotUltrasonicAnalog extends RobotUltrasonicBase{
	AnalogInput ultrasonic; // ultrasonic sensor
	int ultrasonicChannel = 3; // analog input pin
    final double valueToInches = 0.125; //factor to convert sensor values to a distance in inches

    public RobotUltrasonicAnalog() {
		ultrasonic = new AnalogInput(ultrasonicChannel);   
    }
    
    public double getRange() {
    	return ultrasonic.getValue()*valueToInches; //sensor returns a value from 0-4095 that is scaled to inches 
    }
}
