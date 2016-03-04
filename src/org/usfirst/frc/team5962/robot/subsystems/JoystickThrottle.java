package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class JoystickThrottle extends Subsystem {
	
	public static void Speed(){
		 double Throttle = (((Robot.oi.joystickRight.getThrottle()*- 1) +2)/3);
		 
		 RobotMap.myRobot.setMaxOutput(Throttle);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}

