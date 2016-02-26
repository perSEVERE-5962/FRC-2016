
package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;
import org.usfirst.frc.team5962.robot.Robot.DriveType;
import org.usfirst.frc.team5962.robot.sensors.RobotGyro;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Subsystem {	
	RobotDrive myRobot;

	boolean stop = false;
	boolean gyroTurn = false;
	boolean intake = false;
	
	JoystickThrottle throttle;
	
	public Drive(DriveType type) {
		switch (type) {
		case TEST:
			initializeTestRobotDrive();
			break;
		case COMPETITION:
		default:
			initializeCompetitionRobotDrive();
			break;
		}
		throttle = new JoystickThrottle();
	}

	private void initializeCompetitionRobotDrive() {
		myRobot = new RobotDrive(RobotMap.CANTalon1, RobotMap.CANTalon2, RobotMap.CANTalon3, RobotMap.CANTalon4);
	}

	private void initializeTestRobotDrive() {
		myRobot = new RobotDrive(RobotMap.testRobotLeftVictor, RobotMap.testRobotRightVictor);	
	}

	public void gameTank() {
		myRobot.tankDrive(Robot.oi.gamePad1.getRawAxis(1), Robot.oi.gamePad1.getRawAxis(5));
		throttle.Speed();
	}

	public void gameXTank() {
		myRobot.tankDrive(Robot.oi.gamePad2.getRawAxis(1), Robot.oi.gamePad2.getRawAxis(5));
		throttle.Speed();
	}

	public void joystickTank() {
		myRobot.tankDrive(Robot.oi.joystickLeft, Robot.oi.joystickRight);
		throttle.Speed();
	}

	public void arcadeJoystick() {
		myRobot.arcadeDrive(Robot.oi.joystickRight);
		throttle.Speed();
	}

	public void arcadeGame() {
		myRobot.arcadeDrive(Robot.oi.gamePad1);
		throttle.Speed();
	}

	public void arcadeXGame() {
		myRobot.arcadeDrive(Robot.oi.gamePad2);
		throttle.Speed();
	}

	int timer = 0;
	// Auto Test Code Need encoders Wired for Test DO NOT USE
	public void autoDrive() {
		int angleInt = Robot.gyro.getGyroAngle();
		if (Robot.encoder.getLeftDistance() <= 118) {
			drive(-0.5, -angleInt * 0.03);
			SmartDashboard.putString("Gyro Angle", "" + angleInt);
			SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());

		} else if (Robot.encoder.getLeftDistance() > 118 && stop == false) {
			drive(0, 0);
			stop = true;
			gyroTurn = true;
		} else if (gyroTurn == true && angleInt > -85) {
			drive(0.25, 1);
			intake = true;
		} else if (intake == true) {
			while (timer < 30) {
				Robot.inTake.runUpward();
				timer++;
			}
		} else {
			Robot.inTake.stop();
		}
	}

	public void driveTime(RobotGyro gyro) {
		int angleInt = gyro.getGyroAngle();
		drive(-0.25, -angleInt * 0.03);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public void drive(double outputMagnitude, double curve) {
		myRobot.drive(outputMagnitude, curve);
	}
	
	public void setThrottle(double throttle) {
		myRobot.setMaxOutput(throttle);
	}
}
