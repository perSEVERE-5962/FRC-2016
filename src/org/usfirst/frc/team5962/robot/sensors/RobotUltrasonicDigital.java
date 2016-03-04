package org.usfirst.frc.team5962.robot.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class RobotUltrasonicDigital extends RobotUltrasonicBase {
	public Ultrasonic ultrasonic;


    public RobotUltrasonicDigital(int pingChannel, int echoChannel) {
    	// creates the ultrasonic object and assigns ultra to be an ultrasonic sensor which  
    	// uses DigitalOutput 1 for the echo pulse and DigitalInput 1 for the trigger (ping) pulse
    	ultrasonic = new Ultrasonic(pingChannel, echoChannel); 												
    	ultrasonic.setEnabled(true);
    	ultrasonic.setAutomaticMode(true);
    }
    
    
    public void ultraStart() {
    	ultrasonic.setEnabled(true);
    	ultrasonic.setAutomaticMode(true);
    }
    
    public double getRange() {
    	return ultrasonic.getRangeInches();
    }
    
    public boolean getValid() {
    	return ultrasonic.isRangeValid();
    }
    
    
    
/**
    public void Ultrasonic(){
    	int angleInt = Robot.gyro.getGyroAngle();
		boolean rturn = false;
		boolean ultraSensor = false;
		if(ultraSensor == false){	
			RobotMap.myRobot.drive(-0.3, -angleInt * 0.03);
		}
		else if(ultraSensor == true && rturn == false && angleInt < 90){
			RobotMap.myRobot.drive(0.1, -1);
		}
		else if(angleInt >= 90 && angleInt < 91){
			rturn = true;
			ultraSensor = false;
			Robot.gyro.resetGyro();
		}
		else if(ultraSensor == true && rturn == true){
			new ReleaseBallTop();
		}
		
    }
*/
}
