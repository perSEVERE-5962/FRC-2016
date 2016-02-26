package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ExternalHand extends Subsystem {
    
    Victor Handvictor = RobotMap.handVictor;
    RobotDrive myRobot = new RobotDrive(Handvictor,Handvictor);
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	public void runUpward (){
		Handvictor.set(-0.4);  
	}
	
//	public void runDownward (){
//		Handvictor.set(0.4);  
//	}
	
	public void runDownward (){
		myRobot.setMaxOutput(0.4);
		myRobot.arcadeDrive(Robot.oi.gamePad1);
	}
	
	public double getSpeed (){
		return Handvictor.getSpeed();
	}
	
	public void stop (){
		Handvictor.set(0);  
	}    
}

