package org.usfirst.frc.team5962.robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5962.robot.RobotMap;
import org.usfirst.frc.team5962.robot.Robot;

public class Scaling extends Subsystem {
	
    Victor scalingVictor = RobotMap.scalingVictor;
    RobotDrive myRobot = new RobotDrive(scalingVictor,scalingVictor);
	
	protected void initDefaultCommand() {
		
		
	}
	
	public void scaleMotor(){
		myRobot.setMaxOutput(1);
		myRobot.arcadeDrive(Robot.oi.gamePad1.getRawAxis(5), Robot.oi.gamePad1.getRawAxis(5));
	}
	
	public void runUpward() {
		myRobot.drive(-1, 0);
	}
	
	public void runDownward() {
		myRobot.drive(1, 0);
	}
	
	public void stop() {
		myRobot.drive(0, 0);
	}
	

}
