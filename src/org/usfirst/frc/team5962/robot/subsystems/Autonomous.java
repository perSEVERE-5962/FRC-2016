package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;
import org.usfirst.frc.team5962.robot.commands.ExternalHandControl;
import org.usfirst.frc.team5962.robot.sensors.RobotGyro;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	moving negative is right, positive is left
 *  gyro negative is left, positive is right
 */
public class Autonomous extends Subsystem {

//	boolean stop = false;
//	boolean start = false;
	boolean gyroTurn = false;
	boolean intake = false;
	boolean armdown = false;
	boolean armup = true;
	
	final double ROBOT_LENGTH = 37; 
	final double START_TO_OBSTACLE_LENGTH = 74; // 86 inches from center to obstacle, start is 12 inches from center
	final double DISTANCE_TO_OBSTACLE = START_TO_OBSTACLE_LENGTH - ROBOT_LENGTH; // 43 inches
	final double OBSTACLE_LENGTH = 48;
	final double DISTANCE_TO_CLEAR_OBSTACLE = OBSTACLE_LENGTH + ROBOT_LENGTH + 30; 
	final double POSITION_2_TURN_POINT = 210 - DISTANCE_TO_OBSTACLE - DISTANCE_TO_CLEAR_OBSTACLE;
	final double POSITION_5_TURN_POINT = POSITION_2_TURN_POINT;	// assuming the same as position 2 for now
	final int ULTRASONIC_RANGE_VALUE = 72;
//	final int testRobotCorrection = -1; // 1 or test robot, -1 for competition robot
	double speed = -0.7; 
	
	int chevealDeFriseDownCount=0;
	int chevealDeFriseUpCount=0;
	
	
	public void setForwardSpeed(double speed) {
		this.speed = speed;
	}
	
	private double getGyroAngle(){
		double angle = Robot.gyro.getGyroAngle();
		if (RobotMap.isTestRobot) {
			angle = angle*-1;
		}
		return angle;
	}
	
	private double getSpeed() {
		if (RobotMap.isTestRobot) {
			speed = -0.25;
		}
		return speed;
	}
	

	// this is for all cases where we are driving forward
	public boolean driveToObstacle() {
		boolean reachedObstacle = false;
		
		//SmartDashboard.putString("In drivetoobstacle", "true");

		double angle = getGyroAngle();
		// drive to obstacle
		if (Robot.encoder.getDistance() <= DISTANCE_TO_OBSTACLE) {
			RobotMap.myRobot.drive(getSpeed(), -angle * 0.03);
		} else {
			// we made it to the obstacle so stop and 
			// signal the command to move to the next step
			//SmartDashboard.putString("Distance to obstacle", Robot.encoder.getDistance()+"");
			RobotMap.myRobot.drive(0, 0);
			Robot.encoder.reset();
			reachedObstacle = true;
		}
		return reachedObstacle;
	}

	
	
	// special case - we approach Cheval de Frise driving backwards
	public boolean driveToChevalDeFrise() {
		boolean reachedObstacle = false;

		double angle = getGyroAngle();
		// drive to obstacle
		if (Robot.encoder.getDistance() > -(DISTANCE_TO_OBSTACLE+10)) {
			RobotMap.myRobot.drive(0.5, angle * 0.03);
		} else {
			// we made it to the obstacle so stop and 
			// signal the command to move to the next step
			RobotMap.myRobot.drive(0, 0);
			Robot.encoder.reset();
			reachedObstacle = true;
		}
		return reachedObstacle;
	}

	// this is for all cases where we are driving forward
	public boolean clearObstacle() {
		boolean clearedObstacle = false;
		
		//SmartDashboard.putString("In clearObstacle", "true");
		
		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// clear the obstacle
		if (distance <= DISTANCE_TO_CLEAR_OBSTACLE) {  
			//SmartDashboard.putString("Distance from clearObstacle", distance+"");
			RobotMap.myRobot.drive(getSpeed(), -angle * 0.03);			
		}
		// done
		else {
			RobotMap.myRobot.drive(0, 0);
			Robot.encoder.reset();
			clearedObstacle = true;			
		}
		
		return clearedObstacle;	
	}
	

	// special case - we travel over the Cheval de Frise driving backwards
	public boolean clearChevalDeFrise() {
		boolean clearedObstacle = false;
		
		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();
		
		// lower arm
		if (armdown == false) {
			RobotMap.myRobot.drive(0, 0);
			Robot.externalHand.runDownward();
			chevealDeFriseDownCount++;
			if  (chevealDeFriseDownCount >100) {
				armdown = true;
				armup = false;
			}
		}
		

		// drive certain amount in obstacle
		else if (distance > -ROBOT_LENGTH) {
			RobotMap.myRobot.drive(0.5, angle * 0.03);
		} 
		
		// raise the arm
		else if (distance <= -ROBOT_LENGTH && armup == false) {
			RobotMap.myRobot.drive(0, 0);
			Robot.externalHand.runUpward();
			chevealDeFriseUpCount++;
			if  (chevealDeFriseUpCount >75) {
				armup = true;
			}
		}
		
		// clear the obstacle
		else if (distance > -DISTANCE_TO_CLEAR_OBSTACLE && armup == true) { 
			RobotMap.myRobot.drive(0.5, angle * 0.03);			
		}
		
		// turn robot back around
		else if (distance <= -DISTANCE_TO_CLEAR_OBSTACLE && angle <= 180 && armup == true) {
			RobotMap.myRobot.drive(0.25, -1);
		}
		
		// done
		else {
			RobotMap.myRobot.drive(0, 0);
			Robot.encoder.reset();
			Robot.gyro.resetGyro();
			clearedObstacle = true;			
		}
		
		return clearedObstacle;
	}
	
	// if the ultrasonic is enabled then check the range, 
	// otherwise simply return that it is within range - this
	private boolean isWithinRange() {
		boolean isWithinRange = true;
		
//		boolean ultrasonicEnabled = Robot.ultrasonic.isEnabled();
//		if (ultrasonicEnabled) {
//			isWithinRange = Robot.ultrasonic.getRange() <= ULTRASONIC_RANGE_VALUE;
//		}// otherwise just return the default (true)
		
		return isWithinRange;
	}
	
	public boolean attackFromPosition2() {
		boolean isFinished = false;
		
		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// shoot
		if (intake == true) {
			//SmartDashboard.putString("shoot", "In SHOOT!");
			RobotMap.myRobot.drive(0, 0);
			shootBall();
			isFinished = true;
		}
		// drive forward
		else if (distance <= POSITION_2_TURN_POINT && gyroTurn == false) {	
			//SmartDashboard.putString("forward", "In FORWARD!");
			RobotMap.myRobot.drive(-0.5, -angle * 0.03);
		} 
		// stop - reached turning point
		else if (distance > POSITION_2_TURN_POINT && gyroTurn == false && isWithinRange())  {
			//SmartDashboard.putString("stop", "In STOP!");
			RobotMap.myRobot.drive(0, 0);
			gyroTurn = true;
		} 
		// turn
		else if (gyroTurn == true && angle <=30) {	// this is the only line different from "attackFromPosition5"
			//SmartDashboard.putString("Turn", "In TURN!");
			RobotMap.myRobot.drive(0.25, -1);
			intake = true;
		} 
		
		return isFinished;
	}
	
	public boolean attackFromPosition3() {
		boolean isFinished = false;
		
		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();
		SmartDashboard.putString("attackFromPosition3", "true");

		// TODO: attempt to shoot?  for now, just drive forward 2 feet and stop
		if (distance <= 24) {  
			RobotMap.myRobot.drive(-0.5, angle * 0.03);			
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
		
		double angle = getGyroAngle();	
		double distance = Robot.encoder.getDistance();

		// TODO: attempt to shoot?  for now, just drive forward 2 feet and stop
		if (distance <= 24) {  
			RobotMap.myRobot.drive(-0.5, -angle * 0.03);			
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
		
		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// shoot
		if (intake == true) {
			RobotMap.myRobot.drive(0, 0);
			shootBall();
			isFinished = true;
		}
		// drive forward
		else if (distance <= POSITION_5_TURN_POINT && gyroTurn == false) {	
			RobotMap.myRobot.drive(-0.5, -angle * 0.03);
		} 
		// stop - reached turning point
		else if (distance > POSITION_5_TURN_POINT && gyroTurn == false && isWithinRange())  {
			RobotMap.myRobot.drive(0, 0);
			gyroTurn = true;
		} 
		// turn
		else if (gyroTurn == true && angle >= -30) {	// this is the only line different from "attackFromPosition2"
			RobotMap.myRobot.drive(0.25, -1);
			intake = true;
		} 
		
		return isFinished;
	}
	
	private void shootBall() {
		int timer=0;
		RobotMap.myRobot.drive(0, 0);
		while (timer < 30) {
			Robot.inTake.runDownward();
			timer++;
		}
		Robot.inTake.stop();		
	}


	public void driveTime(RobotGyro gyro) {
		double angle = gyro.getGyroAngle();
		RobotMap.myRobot.drive(-0.25, -angle * 0.03);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
