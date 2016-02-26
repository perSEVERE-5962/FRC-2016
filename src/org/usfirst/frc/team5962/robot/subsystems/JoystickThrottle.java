package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

public class JoystickThrottle extends Subsystem {
	
	public void Speed(){
		 double throttle = (((Robot.oi.joystickRight.getThrottle()*- 1) +2)/3);
		 
		 Robot.setThrottle(throttle);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}

