package org.usfirst.frc.team5962.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5962.robot.subsystems.Camera;


/**
 *
 */
public class CameraSwitch extends Command {
	
	Camera camera = new Camera();
	
 boolean x = true;
	
    public CameraSwitch() {
        requires(camera);
        setTimeout(0.01);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if(x == true){
    		Camera.configureCamMode(Camera.CAM_MODE_BACK);
    		x = false;
    	}
    	else if(x == false){
    		Camera.configureCamMode(Camera.CAM_MODE_FRONT);
    		x = true;
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
