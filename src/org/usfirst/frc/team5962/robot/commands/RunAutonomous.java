package org.usfirst.frc.team5962.robot.commands;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;
import org.usfirst.frc.team5962.robot.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunAutonomous  extends Command  {

	private Robot.AutonomousPosition position;
	private Robot.AutonomousObstacle obstacle;
	private Autonomous autonomousSubsystem = Robot.autonomousSubsystem;
	
	
	private boolean reachedObstacle = false;	// stage 1
	private boolean clearedObstacle = false;	// stage 2
	private boolean isFinished = false;			// final stage
	
	public RunAutonomous(Robot.AutonomousPosition position, Robot.AutonomousObstacle obstacle) {
		this.position = position;
		this.obstacle = obstacle;
		
		SmartDashboard.putString("Autonomous Position Selected", position.toString());
		SmartDashboard.putString("Autonomous Obstacle Selected", obstacle.toString());
		
        // Use requires() here to declare subsystem dependencies
        requires(autonomousSubsystem);

	}

	@Override
	protected void initialize() {
		 Robot.encoder.reset();
		 Robot.gyro.resetGyro();
		 RobotMap.myRobot.setMaxOutput(1);
	}
	
	private void reachObstacle() {
		// run to obstacle
		switch (obstacle) {
		case CHEVAL_DE_FRISE:
			reachedObstacle = autonomousSubsystem.driveToChevalDeFrise(); 
			break;
		case LOW_BAR:
			reachedObstacle = autonomousSubsystem.driveToObstacle();
			break;
		default:
			reachedObstacle = autonomousSubsystem.driveToObstacle(); 
			break;
		}	
	}
	
	private void clearObstacle() {
		// go over obstacle
		switch (obstacle) {
		case CHEVAL_DE_FRISE:
			clearedObstacle = autonomousSubsystem.clearChevalDeFrise(); 
			break;
		case MOAT:
		case RAMPARTS:
		case ROCK_WALL:
			autonomousSubsystem.setForwardSpeed(-0.7);
			clearedObstacle = autonomousSubsystem.clearObstacle(); 
			break;
		case ROUGH_TERRAIN:
			autonomousSubsystem.setForwardSpeed(-1.0);
			clearedObstacle = autonomousSubsystem.clearObstacle(); 
			break;

		default:
			// nothing to do, we are done
			//SmartDashboard.putString("Encoder value at end of reaching obstacle", Robot.gyro.getGyroAngle()+"");
			clearedObstacle = true;
			isFinished = true;
			break;
		}		
	}
	
	private void attackCastle() {
		
		isFinished = autonomousSubsystem.attackFromPosition3();
//		// try to shoot!
//		switch (position) {
//		case POSITION_2:
//			isFinished = autonomousSubsystem.attackFromPosition2();
//			break;
//		case POSITION_3:
//			isFinished = autonomousSubsystem.attackFromPosition3();
//			break;
//		case POSITION_4:
//			isFinished = autonomousSubsystem.attackFromPosition4();
//			break;
//		case POSITION_5:
//			isFinished = autonomousSubsystem.attackFromPosition5();
//			break;
//		default:
//			// can't shoot from position 1 (low bar)
//			isFinished = true;
//			break;
//		}		
	}

	@Override
	protected void execute() {
		if (isFinished || obstacle == Robot.AutonomousObstacle.NONE) {
			//SmartDashboard.putString("execute", "FINISHED");

			RobotMap.myRobot.drive(0, 0);
		}
		else if (!reachedObstacle) {
			reachObstacle();
		} else if (!clearedObstacle) {
			clearObstacle();
		} else {
			//attackCastle();
			RobotMap.myRobot.drive(0, 0);
		}
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

}
