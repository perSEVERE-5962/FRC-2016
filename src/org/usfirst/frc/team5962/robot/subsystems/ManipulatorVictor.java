package org.usfirst.frc.team5962.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 */
public class ManipulatorVictor extends Subsystem {
    
   // Victor manipulatorVictor = RobotMap.manipulatorVictor;

    //RobotDrive myRobot = new RobotDrive(manipulatorVictor, manipulatorVictor);

    public void initDefaultCommand() {
       
    }
    public void run(){
   // 	myRobot.arcadeDrive(Robot.oi.gamePad1);
    }
    
    public void runUp(){
    //	manipulatorVictor.set(1);
    }
    
    public void runDown(){
    //	manipulatorVictor.set(-1);
    	
    }
    
    public void stop(){
    	
    }
}

