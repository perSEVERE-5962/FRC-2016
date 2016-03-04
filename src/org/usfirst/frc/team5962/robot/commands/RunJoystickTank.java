package org.usfirst.frc.team5962.robot.commands;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.subsystems.JoystickThrottle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunJoystickTank extends Command {

    public RunJoystickTank() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.oi.currentDriveMode = "Joysticks in Tankdrive mode";
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.joystickTank();
    	JoystickThrottle.Speed();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 return Robot.oi.gameArcadeMode.get()
        	     || Robot.oi.gameXArcadeMode.get()
        	     || Robot.oi.gameTankMode.get()
        		 || Robot.oi.gameXTankMode.get()
        		 || Robot.oi.joystickArcadeModeRight.get()
        		 || Robot.oi.joystickArcadeModeLeft.get();
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
