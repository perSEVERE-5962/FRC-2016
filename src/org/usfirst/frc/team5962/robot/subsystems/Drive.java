
package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {

	boolean stop = false;
	boolean gyroTurn = false;
	boolean intake = false;
    boolean start = false;
	
    public void gameTank() {
		RobotMap.myRobot.tankDrive(Robot.oi.gamePad1.getRawAxis(1), Robot.oi.gamePad1.getRawAxis(5));
	}

	public void gameXTank() {
		RobotMap.myRobot.tankDrive(Robot.oi.gamePad2.getRawAxis(1), Robot.oi.gamePad2.getRawAxis(5));
	}

	public void joystickTank() {
		RobotMap.myRobot.tankDrive(Robot.oi.joystickLeft, Robot.oi.joystickRight);
	}

	public void arcadeJoystickRight() {
		RobotMap.myRobot.arcadeDrive(Robot.oi.joystickRight);
	}

	public void arcadeJoystickLeft() {
		RobotMap.myRobot.arcadeDrive(Robot.oi.joystickLeft);
	}
	public void arcadeGame() {
		RobotMap.myRobot.arcadeDrive(Robot.oi.gamePad1);
	}

	public void arcadeXGame() {
		RobotMap.myRobot.arcadeDrive(Robot.oi.gamePad2);
	}


////////////////////////////////////////////////////////	
// MOVED THE FOLLOWING CODE TO THE Autonomous SUBSYSTEM
////////////////////////////////////////////////////////
//
//	int timer = 0;
//	// Auto Test Code Need encoders Wired for Test DO NOT USE
//	public void autoDrive() {
//		int angleInt = Robot.gyro.getGyroAngle();
//		//if (Robot.encoder.getLeftDistance() <= 210 && start == false) {
//		if (Robot.encoder.getDistance() <= 210 && start == false) {	
//		RobotMap.myRobot.drive(-0.5, -angleInt * 0.03);
//			SmartDashboard.putString("Gyro Angle", "" + angleInt);
//			SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());
//			SmartDashboard.putString("Right Encoder", "" + Robot.encoder.getRightDistance());
//			/*SmartDashboard.putNumber("Ultrasonic Range", Robot.ultrasonic.getRange());*/
//			
//		//} else if (Robot.encoder.getLeftDistance() > 210 && stop == false && Robot.ultrasonic.getRange() <= 72)  {
//		} else if (Robot.encoder.getDistance() > 210 && stop == false && Robot.ultrasonic.getRange() <= 72)  {
//			RobotMap.myRobot.drive(0, 0);
//			start = true;
//			stop = true;
//			gyroTurn = true;
//		} else if (gyroTurn == true && angleInt <=30) {
//			RobotMap.myRobot.drive(0.25, -1);
//			intake = true;
//		} else if (intake == true) {
//			while (timer < 30) {
//				Robot.inTake.runUpward();
//				Robot.upperLaunchMotor.runUpward();
//				timer++;
//			}
//		} else {
//			Robot.inTake.stop();
//		}
//	}
//
//	public void driveTime(RobotGyro gyro) {
//		int angleInt = gyro.getGyroAngle();
//		RobotMap.myRobot.drive(-0.25, -angleInt * 0.03);
//	}


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
