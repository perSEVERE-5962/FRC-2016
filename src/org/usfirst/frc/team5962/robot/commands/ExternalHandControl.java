package org.usfirst.frc.team5962.robot.commands;

import org.usfirst.frc.team5962.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExternalHandControl extends Command {
private boolean runDownward=true;
	public ExternalHandControl(boolean runDownward)
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.externalHand);
		setTimeout(0.3);
		this.runDownward=runDownward;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if(runDownward==true){ 
			Robot.externalHand.runDownward();
		} 
		else
		{
			Robot.externalHand.runUpward();
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
		Robot.externalHand.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
