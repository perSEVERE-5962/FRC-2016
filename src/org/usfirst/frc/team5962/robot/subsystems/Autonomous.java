package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * moving negative is right, positive is left gyro negative is left, positive is
 * right
 */
public class Autonomous extends Subsystem {

	boolean gyroTurn = false;
	boolean shoot = false;
	boolean armdown = false;
	boolean armup = true;
	boolean driveToCastle = false;
	boolean Linedup = false;
	boolean Lineup = false;
	boolean position34turn = false;
	boolean intake = false; 
	boolean turnAround = true;
	boolean turnAgain = false;
    final double ROBOT_LENGTH = 37;
	final double START_TO_OBSTACLE_LENGTH = 74; // 86 inches from center to
												// obstacle, start is 12 inches
												// from center
	final double DISTANCE_TO_OBSTACLE = START_TO_OBSTACLE_LENGTH - ROBOT_LENGTH; // 43
																				// inches
	int counter = 0;
	final double OBSTACLE_LENGTH = 48;
	final double DISTANCE_TO_CLEAR_OBSTACLE = OBSTACLE_LENGTH + ROBOT_LENGTH + 30;
	final double turn_Point = 210 - DISTANCE_TO_OBSTACLE - DISTANCE_TO_CLEAR_OBSTACLE;
	final int ULTRASONIC_RANGE_VALUE = 62;
	double speed = -0.7;

	int chevealDeFriseDownCount = 0;
	int chevealDeFriseUpCount = 0;

	public void setForwardSpeed(double speed) {
		this.speed = speed;
	}

	private double getGyroAngle() {
		double angle = Robot.gyro.getGyroAngle();
		if (RobotMap.isTestRobot) {
			angle = angle * -1;
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

		// SmartDashboard.putString("In drivetoobstacle", "true");

		double angle = getGyroAngle();
		// drive to obstacle
		if (Robot.encoder.getDistance() <= DISTANCE_TO_OBSTACLE) {
			RobotMap.myRobot.drive(getSpeed(), -angle * 0.03);
		} else {
			// we made it to the obstacle so stop and
			// signal the command to move to the next step
			// SmartDashboard.putString("Distance to obstacle",
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
		if (Robot.encoder.getDistance() > -(DISTANCE_TO_OBSTACLE + 10)) {
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

		// SmartDashboard.putString("In clearObstacle", "true");

		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// clear the obstacle
		if (distance <= DISTANCE_TO_CLEAR_OBSTACLE) {
			// SmartDashboard.putString("Distance from clearObstacle",
			// distance+"");
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
			if (chevealDeFriseDownCount > 100) {
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
			if (chevealDeFriseUpCount > 75) {
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
		boolean isWithinRange = false;

		boolean ultrasonicEnabled = Robot.ultrasonicShoot.isEnabled();
		if (ultrasonicEnabled) {
			isWithinRange = Robot.ultrasonicShoot.getRange() <= ULTRASONIC_RANGE_VALUE;
		} // otherwise just return the default (true)

		return isWithinRange;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean attackFromPosition1() {
		// Shooting from here
		boolean isFinished = false;

		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// shoot
		if (shoot == true && counter <= 50) {
			//SmartDashboard.putString("shoot", "In SHOOT!");
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runDownward();
			intake = true;
		    counter++;
		}
		else if(intake == true){
			shoot = false;
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runUpward();
		
		}
		// drive forward
		else if (distance <= turn_Point && gyroTurn == false) {
			//SmartDashboard.putString("position2turnpoint", "" + turn_Point);
			//SmartDashboard.putString("forward", "In FORWARD!" + distance);
			RobotMap.myRobot.drive(-0.75, -angle * 0.03);
		}
		// stop - reached turning point
		else if (distance > turn_Point && gyroTurn == false && isWithinRange()) {
			gyroTurn = true;
			//SmartDashboard.putString("stop", "In STOP!");
			RobotMap.myRobot.drive(0, 0);
			Lineup = true;
		}
		//Face the goal
		else if (Lineup == true && Linedup == false) {
			//SmartDashboard.putString("Lineup", "In Lineup");
			if (angle <= 47) {
				RobotMap.myRobot.drive(0.5, -1);
			} else {
				//SmartDashboard.putString("Lineup", "Done Lineup");
				Robot.encoder.reset();
				RobotMap.myRobot.drive(0, 0);
				Linedup = true;
				driveToCastle = true;
			}
		} 
		//go to the bottom of the castle
		else if (driveToCastle == true && Linedup == true) {
			//SmartDashboard.putString("drivetocastle", "In Drive To Castle!");
			if (distance < 100) {
				RobotMap.myRobot.drive(-0.5, 0);
			}
			else{
				shoot = true;
				counter = 0;
			}
			
		}

		return isFinished;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean attackFromPosition2() {
		// Shooting from here
		boolean isFinished = false;

		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// shoot
		if (shoot == true && counter <= 50) {
			//SmartDashboard.putString("shoot", "In SHOOT!");
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runDownward();
			intake = true;
		    counter++;
		}
		else if(intake == true){
			shoot = false;
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runUpward();
		
		}
		// drive forward
		else if (distance <= (turn_Point + 35) && gyroTurn == false) {
			//SmartDashboard.putString("position2turnpoint", "" + turn_Point);
			//SmartDashboard.putString("forward", "In FORWARD!" + distance);
			RobotMap.myRobot.drive(-0.75, -angle * 0.03);
		}
		// stop - reached turning point
		else if (distance > turn_Point && gyroTurn == false && isWithinRange()) {
			gyroTurn = true;
			//SmartDashboard.putString("stop", "In STOP!");
			RobotMap.myRobot.drive(0, 0);
			Lineup = true;
		}
		//Face the goal
		else if (Lineup == true && Linedup == false) {
			//SmartDashboard.putString("Lineup", "In Lineup");
			if (angle <= 42) {
				RobotMap.myRobot.drive(0.5, -1);
			} else {
				//SmartDashboard.putString("Lineup", "Done Lineup");
				Robot.encoder.reset();
				RobotMap.myRobot.drive(0, 0);
				Linedup = true;
				driveToCastle = true;
			}
		} 
		//go to the bottom of the castle
		else if (driveToCastle == true && Linedup == true) {
			//SmartDashboard.putString("drivetocastle", "In Drive To Castle!");
			if (distance < 85) {
				RobotMap.myRobot.drive(-0.5, 0);
			}
			else{
				shoot = true;
				counter = 0;
			}
			
		}

		return isFinished;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean attackFromPosition3() {
		boolean isFinished = false;

		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();
		//SmartDashboard.putString("attackFromPosition3", "true");
		double rangeGoal = Robot.ultrasonicShoot.getRange();
		double rangeWall = rangeGoal;
		
		if (shoot == true && counter <= 50) {
			//SmartDashboard.putString("shoot", "In SHOOT!");
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runDownward();
			intake = true;
		    counter++;
		}
		else if(intake == true){
			shoot = false;
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runUpward();
		
		}
		// drive forward
		else if (distance <= turn_Point && gyroTurn == false) {
			//SmartDashboard.putString("position2turnpoint", "" + turn_Point);
			SmartDashboard.putString("forward", "In FORWARD!" + distance);
			RobotMap.myRobot.drive(-0.75, -angle * 0.03);
		}
		else if (distance > (turn_Point + 30) && gyroTurn == false) {
			gyroTurn = true;
			//SmartDashboard.putString("stop", "In STOP!");
			RobotMap.myRobot.drive(0, 0);
			position34turn= true;
		}
		else if (position34turn == true){
			if (angle > -60){
				RobotMap.myRobot.drive(-0.5, -1);
				Robot.encoder.reset();
			}
			else if (distance < 50 && angle < -60){
				RobotMap.myRobot.drive(-0.75, -angle * 0.03);
			}
			else{
				Lineup = true;	
				RobotMap.myRobot.drive(0, 0);
				position34turn = false;
			}
			
		}
		//Face the goal
		else if (Lineup == true && Linedup == false) {
			//SmartDashboard.putString("Lineup", "In Lineup");
			if (angle <= 45) {
				RobotMap.myRobot.drive(0.5, -1);
			} else {
				//SmartDashboard.putString("Lineup", "Done Lineup");
				Robot.encoder.reset();
				RobotMap.myRobot.drive(0, 0);
				//SmartDashboard.putString("Ultrasonicturn", rangeWall + "");
				Linedup = true;
				driveToCastle = true;
			}
		}
		else if (driveToCastle == true && Linedup == true) {
				//SmartDashboard.putString("drivetocastle", "In Drive To Castle!");
				if (distance < 75) {
					RobotMap.myRobot.drive(-0.75, 0);
				}
				else{
					shoot = true;
					counter = 0;
				}
		}

		return isFinished;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean attackFromPosition4() {
		boolean isFinished = false;

		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();
		//SmartDashboard.putString("attackFromPosition3", "true");
		double rangeGoal = Robot.ultrasonicShoot.getRange();
		double rangeWall = rangeGoal;
		
		if (shoot == true && counter <= 50) {
			//SmartDashboard.putString("shoot", "In SHOOT!");
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runDownward();
			intake = true;
		    counter++;
		}
		else if(intake == true){
			shoot = false;
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runUpward();
		
		}
		// drive forward
		else if (distance <= turn_Point && gyroTurn == false) {
			//SmartDashboard.putString("position2turnpoint", "" + turn_Point);
			//SmartDashboard.putString("forward", "In FORWARD!" + distance);
			RobotMap.myRobot.drive(-0.75, -angle * 0.03);
		}
		else if (distance > (turn_Point + 30) && gyroTurn == false) {
			gyroTurn = true;
			//SmartDashboard.putString("stop", "In STOP!");
			RobotMap.myRobot.drive(0, 0);
			position34turn= true;
		}
		else if (position34turn == true){
			if (angle < 60){
				RobotMap.myRobot.drive(0.5, -1);
				Robot.encoder.reset();
			}
			else if (distance < 75 && angle > 60){
				RobotMap.myRobot.drive(-0.5, -angle * 0.03);
			}
			else{
				Lineup = true;	
				RobotMap.myRobot.drive(0, 0);
				position34turn = false;
			}
			
		}
		//Face the goal
		else if (Lineup == true && Linedup == false) {
			//SmartDashboard.putString("Lineup", "In Lineup");
			if (angle >= -45) {
				RobotMap.myRobot.drive(-0.5, -1);
			} else {
				//SmartDashboard.putString("Lineup", "Done Lineup");
				Robot.encoder.reset();
				RobotMap.myRobot.drive(0, 0);
				//SmartDashboard.putString("Ultrasonicturn", rangeWall + "");
				Linedup = true;
				driveToCastle = true;
			}
		}
		else if (driveToCastle == true && Linedup == true) {
				//SmartDashboard.putString("drivetocastle", "In Drive To Castle!");
				if (distance < 75) {
					RobotMap.myRobot.drive(-0.5, 0);
				}
				else{
					shoot = true;
					counter = 0;
				}
		}

		return isFinished;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean attackFromPosition5() {
		// Shooting from here
		boolean isFinished = false;

		double angle = getGyroAngle();
		double distance = Robot.encoder.getDistance();

		// shoot
		if (shoot == true && counter <= 50) {
			//SmartDashboard.putString("shoot", "In SHOOT!");
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runDownward();
			intake = true;
		    counter++;
		}
		else if(intake == true){
			shoot = false;
			RobotMap.myRobot.drive(0, 0);
			Robot.inTake.runUpward();
		
		}
		// drive forward
		else if (distance <= turn_Point + 35 && gyroTurn == false) {
			//SmartDashboard.putString("position2turnpoint", "" + turn_Point);
			//SmartDashboard.putString("forward", "In FORWARD!" + distance);
			RobotMap.myRobot.drive(-0.75, -angle * 0.03);
		}
		// stop - reached turning point
		else if (distance > turn_Point && gyroTurn == false && isWithinRange()) {
			gyroTurn = true;
			//SmartDashboard.putString("stop", "In STOP!");
			RobotMap.myRobot.drive(0, 0);
			Lineup = true;
		}
		//Face the goal
		else if (Lineup == true && Linedup == false) {
			//SmartDashboard.putString("Lineup", "In Lineup");
			if (angle >= -45) {
				RobotMap.myRobot.drive(-0.5, -1);
			} else {
				//SmartDashboard.putString("Lineup", "Done Lineup");
				Robot.encoder.reset();
				RobotMap.myRobot.drive(0, 0);
				Linedup = true;
				driveToCastle = true;
			}
		} 
		//go to the bottom of the castle
		else if (driveToCastle == true && Linedup == true) {
			//SmartDashboard.putString("drivetocastle", "In Drive To Castle!");
			if (distance < 75) {
				RobotMap.myRobot.drive(-0.5, 0);
			}
			else{
				shoot = true;
				counter = 0;
			}
			
		}

		return isFinished;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
