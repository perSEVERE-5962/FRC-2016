package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.RobotMap;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class InTakeMotor extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Victor inTakeVictor = RobotMap.inTakeVictor;
	
	public void runUpward (){
		inTakeVictor.set(-0.5);
	  //Turn on Belt
	}
	
	public void runUpwardSlow (){
		inTakeVictor.set(-0.5);
	  //Turn on Belt
	}
	
	public void runDownward (){
		inTakeVictor.set(1);
	  //Turn on Belt
	}
	
	public void stop (){
		inTakeVictor.set(0);
	  //Turn off Belt
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

