package org.usfirst.frc.team5962.robot.commands;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraControlStick extends Command {

	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private double valueX;
	private double valueY;
	
    public CameraControlStick() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.camera);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	
    	valueX = RobotMap.axisCameraServoViewHorizontal.get();
    	valueY = RobotMap.axisCameraServoViewVertical.get();
    	double RightStickXAxis = Robot.oi.getCoPilotHorizontalCameraAxis();
    	double RightStickYAxis = Robot.oi.getCoPilotVerticalCameraAxis();
    	 
    	
    	if (RightStickYAxis <= -0.6)
    	{
    		up = true;
    	}
    	
    	else if (RightStickXAxis >= 0.6 && RightStickYAxis <= -0.4)
    	{
    		right = true;
    		up = true;
    	}
    	
    	else if (RightStickXAxis >= 0.6)
    	{
    		right = true;
    	}
    	
    	else if (RightStickXAxis >= 0.6 && RightStickYAxis >= 0.6 )
    	{
    		right = true;
    		down = true;
    	}
    	
    	else if (RightStickYAxis >= 0.6)
    	{
    		down = true;
    	}
    	
    	else if (RightStickXAxis >= 0.6 && RightStickYAxis <= -0.4)
    	{
    		left = true;
    		down = true;
    	}
    	
    	else if (RightStickXAxis <= -0.6)
    	{
    		left = true;
    	}
    	
    	else if (RightStickXAxis <= -0.6 && RightStickYAxis <= -0.6)
    	{
    		left = true;
    		up = true;
    	}
    	
    	//if POV is not being pressed the camera will not move
    //	if (RightStickXAxis == 0 && RightStickYAxis == 0)
    	else
    	{
    		left = false;
    		right = false;
    		up = false;
    		down = false;
    	}
    	   	
    	if (left == true && valueX >= 0)
    	{		
    		Robot.camera.moveX(valueX-0.025);
    	}
    	if (right == true && valueX <= 1)
    	{		
    		Robot.camera.moveX(valueX+0.025);
    	}
    	
    	if (down == true && valueY >= 0)
    	{		
    		Robot.camera.moveY(valueY-0.025);
    	}
    	if (up == true && valueY <= 1)
    	{   		
    		Robot.camera.moveY(valueY+0.025);
    	}
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
