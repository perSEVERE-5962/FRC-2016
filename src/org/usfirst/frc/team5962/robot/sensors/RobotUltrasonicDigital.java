package org.usfirst.frc.team5962.robot.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class RobotUltrasonicDigital extends RobotUltrasonicBase {
	Ultrasonic ultrasonic;

    public RobotUltrasonicDigital() {
    	// creates the ultrasonic object and assigns ultra to be an ultrasonic sensor which  
    	// uses DigitalOutput 1 for the echo pulse and DigitalInput 1 for the trigger pulse
    	ultrasonic = new Ultrasonic(8,7); 												
    	ultrasonic.setEnabled(true);
    	ultrasonic.setAutomaticMode(true);
    }
    
    public double getRange() {
    	return ultrasonic.getRangeInches();
    }

}
