package org.usfirst.frc.team5962.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ManualSwitch extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

//	private final static DigitalInput stopSwitch = RobotMap.manualSwitch;
	
	public static boolean sensor (){
//		return stopSwitch.get();
		return false;
	  //Say when ball reaches sensor
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
}

