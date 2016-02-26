package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.RobotMap;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class UpperLaunchMotor extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Victor victor = RobotMap.upperLaunchVictor;

	
	public void runUpward (){
		victor.set(-1);
	  //Turn on Belt
	}
	
	public void runDownward (){
		victor.set(1);
	  //Turn on Belt
	}
	
	public void stop (){
		victor.set(0);
	  //Turn off Belt
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

