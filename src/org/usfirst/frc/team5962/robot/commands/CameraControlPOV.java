package org.usfirst.frc.team5962.robot.commands;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraControlPOV extends Command {

	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	private double valueX;
	private double valueY;
	

	public CameraControlPOV() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.camera);

	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		int pov=Robot.oi.getCoPilotPOV();

//		valueX = RobotMap.axisCameraServoViewHorizontal.get();
//		valueY = RobotMap.axisCameraServoViewVertical.get();

		if (pov == 0) {
			up = true;
		}

		else if (pov == 45) {
			right = true;
			up = true;
		}

		else if (pov == 90) {
			right = true;
		}

		else if (pov == 135) {
			right = true;
			down = true;
		}

		else if (pov == 180) {
			down = true;
		}

		else if (pov == 225) {
			left = true;
			down = true;
		}

		else if (pov == 270) {
			left = true;
		}

		else if (pov == 315) {
			left = true;
			up = true;
		}

		// if POV is not being pressed the camera will not move
		else  {
			left = false;
			right = false;
			up = false;
			down = false;
		}

		if (left == true && valueX >= 0) {

			Robot.camera.moveX(valueX - 0.025);
		}
		if (right == true && valueX <= 1) {

			Robot.camera.moveX(valueX + 0.025);
		}

		if (down == true && valueY >= 0) {

			Robot.camera.moveY(valueY - 0.025);
		}
		if (up == true && valueY <= 1) {

			Robot.camera.moveY(valueY + 0.025);
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
