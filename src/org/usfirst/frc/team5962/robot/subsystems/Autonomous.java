package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;
import org.usfirst.frc.team5962.robot.commands.ExternalHandControl;
import org.usfirst.frc.team5962.robot.sensors.RobotGyro;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Autonomous extends Subsystem {

//	boolean stop = false;
//	boolean start = false;
	boolean gyroTurn = false;
	boolean intake = false;
	boolean armdown = false;
	boolean armup = true;
	
	final double ROBOT_LENGTH = 31; 
	final double START_TO_OBSTACLE_LENGTH = 74; // 86 inches from center to obstacle, start is 12 inches from center
	final double DISTANCE_TO_OBSTACLE = START_TO_OBSTACLE_LENGTH - ROBOT_LENGTH; // 43 inches
	final double OBSTACLE_LENGTH = 48;
	final double DISTANCE_TO_CLEAR_OBSTACLE = OBSTACLE_LENGTH + ROBOT_LENGTH; // add robot length to ensure we have cleared it = 79 inches
	final double POSITION_2_TURN_POINT = 210 - DISTANCE_TO_OBSTACLE - DISTANCE_TO_CLEAR_OBSTACLE;
	final double POSITION_5_TURN_POINT = POSITION_2_TURN_POINT;	// assuming the same as position 2 for now

	// this is for all cases where we are driving forward
	public boolean driveToObstacle() {
		boolean reachedObstacle = false;

		int angleInt = Robot.gyro.getGyroAngle();
		// drive to obstacle
		if (Robot.encoder.getDistance() <= DISTANCE_TO_OBSTACLE) {
			RobotMap.myRobot.drive(-0.5, -angleInt * 0.03);
			SmartDashboard.putString("Gyro Angle", "" + angleInt);
			SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());
			SmartDashboard.putString("Right Encoder", "" + Robot.encoder.getRightDistance());
		} else {
			// we made it to the obstacle so stop and 
			// signal the command to move to the next step
			RobotMap.myRobot.drive(0, 0);;
			Robot.encoder.reset();
			reachedObstacle = true;
		}
		return reachedObstacle;
	}

	// special case - we approach Cheval de Frise driving backwards
	public boolean driveToChevalDeFrise() {
		boolean reachedObstacle = false;

		int angleInt = Robot.gyro.getGyroAngle();
		// drive to obstacle
		if (Robot.encoder.getDistance() <= DISTANCE_TO_OBSTACLE) {
			RobotMap.myRobot.drive(0.5, angleInt * 0.03);
			SmartDashboard.putString("Gyro Angle", "" + angleInt);
			SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());
			SmartDashboard.putString("Right Encoder", "" + Robot.encoder.getRightDistance());
		} else {
			// we made it to the obstacle so stop and 
			// signal the command to move to the next step
			RobotMap.myRobot.drive(0, 0);;
			Robot.encoder.reset();
			reachedObstacle = true;
		}
		return reachedObstacle;
	}

	// this is for all cases where we are driving forward
	public boolean clearObstacle() {
		boolean clearedObstacle = false;
		
		int angleInt = Robot.gyro.getGyroAngle();
		double distance = Robot.encoder.getDistance();
		SmartDashboard.putString("Gyro Angle", "" + angleInt);
		SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());
		SmartDashboard.putString("Right Encoder", "" + Robot.encoder.getRightDistance());

		// clear the obstacle
		if (distance <= DISTANCE_TO_CLEAR_OBSTACLE) {  
			RobotMap.myRobot.drive(-0.5, -angleInt * 0.03);			
		}
		// done
		else {
			RobotMap.myRobot.drive(0, 0);;
			Robot.encoder.reset();
			clearedObstacle = true;			
		}
		
		return clearedObstacle;	}

	// special case - we travel over the Cheval de Frise driving backwards
	public boolean clearChevalDeFrise() {
		boolean clearedObstacle = false;
		
		int angleInt = Robot.gyro.getGyroAngle();
		double distance = Robot.encoder.getDistance();
		SmartDashboard.putString("Gyro Angle", "" + angleInt);
		SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());
		SmartDashboard.putString("Right Encoder", "" + Robot.encoder.getRightDistance());
		
		// lower arm
		if (armdown == false) {
			ExternalHandControl externalhandcontrol = new ExternalHandControl(true);
			externalhandcontrol.start();
			armdown = true;
			armup = false;
		}

		// drive certain amount in obstacle
		else if (distance <= ROBOT_LENGTH) {
			RobotMap.myRobot.drive(0.5, angleInt * 0.03);
		} 
		
		// raise the arm
		else if (distance > ROBOT_LENGTH && armup == false) {
			ExternalHandControl externalhandcontrol = new ExternalHandControl(false);
			externalhandcontrol.start();
			armup = true;
		}
		
		// clear the obstacle
		else if (distance <= DISTANCE_TO_CLEAR_OBSTACLE) { 
			RobotMap.myRobot.drive(0.5, angleInt * 0.03);			
		}
		
		// turn robot back around
		else if (distance > DISTANCE_TO_CLEAR_OBSTACLE && angleInt <= 180) {
			RobotMap.myRobot.drive(0.25, -1);
		}
		
		// done
		else {
			RobotMap.myRobot.drive(0, 0);;
			Robot.encoder.reset();
			clearedObstacle = true;			
		}
		
		return clearedObstacle;
/*		
		// the next section is for shooting
		 * 
		 * 
		// still need to find
		else if (Robot.encoder.getDistance() <= 134.7 && armdown == false) {
			RobotMap.myRobot.drive(-0.5, -angleInt * 0.03);
			SmartDashboard.putString("Gyro Angle", "" + angleInt);
			SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());
			SmartDashboard.putString("Right Encoder", "" + Robot.encoder.getRightDistance());
		} else if (Robot.encoder.getDistance() > 134.7 && armdown == false && Robot.ultrasonic.getRange() <= 72) {
			RobotMap.myRobot.drive(0, 0);

			gyroTurn = true;
		} else if (gyroTurn == true && angleInt <= -30) {
			RobotMap.myRobot.drive(0.25, -1);
			intake = true;
		} else if (intake == true) {
			while (timer < 30) {
				Robot.inTake.runUpward();
				Robot.upperLaunchMotor.runUpward();
				timer++;
			}
		} else {
			Robot.inTake.stop();
		}
*/
	}
	
	
	public boolean attackFromPosition2() {
		boolean isFinished = false;
		
		int angleInt = Robot.gyro.getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// drive forward
		if (distance <= POSITION_2_TURN_POINT && gyroTurn == false) {	
			RobotMap.myRobot.drive(-0.5, -angleInt * 0.03);
		} 
		// stop - reached turning point
		else if (distance > POSITION_2_TURN_POINT && gyroTurn == false && Robot.ultrasonic.getRange() <= 72)  {
			RobotMap.myRobot.drive(0, 0);
			gyroTurn = true;
		} 
		// turn
		else if (gyroTurn == true && angleInt <=30) {	// this is the only line different from "attackFromPosition5"
			RobotMap.myRobot.drive(0.25, -1);
			intake = true;
		} 
		// shoot
		else if (intake == true) {
			shootBall();
			isFinished = true;
		}
		
		return isFinished;
	}
	
	public boolean attackFromPosition3() {
		boolean isFinished = false;
		
		int angleInt = Robot.gyro.getGyroAngle();
		double distance = Robot.encoder.getDistance();
		SmartDashboard.putString("Gyro Angle", "" + angleInt);
		SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());
		SmartDashboard.putString("Right Encoder", "" + Robot.encoder.getRightDistance());

		// TODO: attempt to shoot?  for now, just drive forward 2 feet and stop
		if (distance <= 24) {  
			RobotMap.myRobot.drive(-0.5, -angleInt * 0.03);			
		}
		// done
		else {
			RobotMap.myRobot.drive(0, 0);;
			isFinished = true;			
		}
		
		return isFinished;
	}
	
	public boolean attackFromPosition4() {
		boolean isFinished = false;
		
		int angleInt = Robot.gyro.getGyroAngle();
		double distance = Robot.encoder.getDistance();
		SmartDashboard.putString("Gyro Angle", "" + angleInt);
		SmartDashboard.putString("Left Encoder", "" + Robot.encoder.getLeftDistance());
		SmartDashboard.putString("Right Encoder", "" + Robot.encoder.getRightDistance());

		// TODO: attempt to shoot?  for now, just drive forward 2 feet and stop
		if (distance <= 24) {  
			RobotMap.myRobot.drive(-0.5, -angleInt * 0.03);			
		}
		// done
		else {
			RobotMap.myRobot.drive(0, 0);;
			isFinished = true;			
		}
		
		return isFinished;
	}

	public boolean attackFromPosition5() {
		boolean isFinished = false;
		
		int angleInt = Robot.gyro.getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// drive forward
		if (distance <= POSITION_5_TURN_POINT && gyroTurn == false) {	
			RobotMap.myRobot.drive(-0.5, -angleInt * 0.03);
		} 
		// stop - reached turning point
		else if (distance > POSITION_5_TURN_POINT && gyroTurn == false && Robot.ultrasonic.getRange() <= 72)  {
			RobotMap.myRobot.drive(0, 0);
			gyroTurn = true;
		} 
		// turn
		else if (gyroTurn == true && angleInt <= -30) {	// this is the only line different from "attackFromPosition2"
			RobotMap.myRobot.drive(0.25, -1);
			intake = true;
		} 
		// shoot
		else if (intake == true) {
			shootBall();
			isFinished = true;
		}
		
		return isFinished;
	}
	
	private void shootBall() {
		int timer=0;
		RobotMap.myRobot.drive(0, 0);
		while (timer < 30) {
			Robot.inTake.runUpward();
			Robot.upperLaunchMotor.runUpward();
			timer++;
		}
		Robot.inTake.stop();		
	}


	public void driveTime(RobotGyro gyro) {
		int angleInt = gyro.getGyroAngle();
		RobotMap.myRobot.drive(-0.25, -angleInt * 0.03);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
