package org.usfirst.frc.team5962.robot.commands;

import org.usfirst.frc.team5962.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunArcadeXGame extends Command {

    public RunArcadeXGame() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSubSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.oi.currentDriveMode = "Xbox Gamepad in Arcade mode";
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubSystem.arcadeXGame();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 return Robot.oi.gameArcadeMode.get()
        	     || Robot.oi.joystickArcadeMode.get()
        	     || Robot.oi.gameTankMode.get()
        		 || Robot.oi.gameXTankMode.get()
        		 || Robot.oi.joystickTankMode.get();
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