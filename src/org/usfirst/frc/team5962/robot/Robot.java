
package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team5962.robot.commands.CameraControlStick;
import org.usfirst.frc.team5962.robot.commands.ReleaseBallTop;
import org.usfirst.frc.team5962.robot.commands.RunAutonomous;
import org.usfirst.frc.team5962.robot.commands.RunJoystickTank;
import org.usfirst.frc.team5962.robot.sensors.MockRobotEncoder;
import org.usfirst.frc.team5962.robot.sensors.RobotEncoder;
import org.usfirst.frc.team5962.robot.sensors.RobotGyro;

import org.usfirst.frc.team5962.robot.sensors.RobotUltrasonicDigital;
import org.usfirst.frc.team5962.robot.subsystems.TimedAutoDrive;
import org.usfirst.frc.team5962.robot.subsystems.Autonomous;
import org.usfirst.frc.team5962.robot.subsystems.Camera;
import org.usfirst.frc.team5962.robot.subsystems.UpperLaunchMotor;
import org.usfirst.frc.team5962.robot.subsystems.Drive;
import org.usfirst.frc.team5962.robot.subsystems.InTakeMotor;
import org.usfirst.frc.team5962.robot.subsystems.JoystickThrottle;
import org.usfirst.frc.team5962.robot.subsystems.ExternalHand;
import java.lang.Math;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static Drive drive;
	public static Autonomous autonomousSubsystem;
	public TimedAutoDrive timedAutoDrive;
	public static JoystickThrottle throttle;
	
	public static InTakeMotor inTake;
//	public static ReleaseBallTop releaseBallTop;
	public static ExternalHand externalHand;
	public static Camera camera = new Camera();
	//public static CameraControlPOV cameraControl;
	//public static CameraControlStick cameraControlStick;
	

	public static RobotGyro gyro= new RobotGyro();

	
	public static RobotEncoder encoder = new RobotEncoder();
//	public static RobotUltrasonicDigital ultrasonic;
	
	public static OI oi;
	
	
	public static enum AutonomousPosition {
		POSITION_1,
		POSITION_2,
		POSITION_3,
		POSITION_4,
		POSITION_5
	}
	
	public static enum AutonomousObstacle {
		LOW_BAR,
		PORTCULLIS,
		CHEVAL_DE_FRISE,
		MOAT,
		RAMPARTS,
		DRAWBRIDGE,
		SALLY_PORT,
		ROCK_WALL,
		ROUGH_TERRAIN,
		NONE
	}
	
	RunAutonomous autonomousCommand;
	SendableChooser autoPositionChooser;
	SendableChooser autoObstacleChooser;

//	enum ShooterChoices {
//		UPPER, LOWER
//	}
//	SendableChooser shooterChooser;
//	ShooterChoices shooterChoice = ShooterChoices.UPPER;
	
	public void robotInit() {	
		/*
		 * Choose the proper robot drive and then re-deploy to the rio
		 */
		RobotMap.init();
		
		drive = new Drive();
		
		inTake = new InTakeMotor();
		externalHand= new ExternalHand();
		

//		ultrasonic = new RobotUltrasonicDigital(RobotMap.DIO_CHANNEL_8, RobotMap.DIO_CHANNEL_7);

		
		oi = new OI();
		
		gyro.resetGyro();
	
		initAutonomousPositionChooser();
		initAutonomousObstacleChooser();

		
		//initShooterChooser();

		//shooterChoice = (ShooterChoices) shooterChooser.getSelected();		
	}
	
	private void initAutonomousPositionChooser() {
		autoPositionChooser = new SendableChooser();
		autoPositionChooser.addDefault("Position 1", AutonomousPosition.POSITION_1);
		autoPositionChooser.addObject("Position 2", AutonomousPosition.POSITION_2);
		autoPositionChooser.addObject("Position 3", AutonomousPosition.POSITION_3);
		autoPositionChooser.addObject("Position 4", AutonomousPosition.POSITION_4);
		autoPositionChooser.addObject("Position 5", AutonomousPosition.POSITION_5);	
		SmartDashboard.putData("Select Autonomous Start Position", autoPositionChooser);
	}
	
	private void initAutonomousObstacleChooser() {
		autoObstacleChooser = new SendableChooser();
		autoObstacleChooser.addDefault("None", AutonomousObstacle.NONE);
		autoObstacleChooser.addObject("Low Bar", AutonomousObstacle.LOW_BAR);
		autoObstacleChooser.addObject("Portcullis", AutonomousObstacle.PORTCULLIS);
		autoObstacleChooser.addObject("Cheval de Frise", AutonomousObstacle.CHEVAL_DE_FRISE);
		autoObstacleChooser.addObject("Moat", AutonomousObstacle.MOAT);
		autoObstacleChooser.addObject("Ramparts", AutonomousObstacle.RAMPARTS);
		autoObstacleChooser.addObject("Drawbridge", AutonomousObstacle.DRAWBRIDGE);
		autoObstacleChooser.addObject("Sally Port", AutonomousObstacle.SALLY_PORT);
		autoObstacleChooser.addObject("Rock Wall", AutonomousObstacle.ROCK_WALL);
		autoObstacleChooser.addObject("Rough Terrain", AutonomousObstacle.ROUGH_TERRAIN);		
		SmartDashboard.putData("Select Autonomous Start Obstacle", autoObstacleChooser);
	}

//	private void initShooterChooser() {
//		shooterChooser = new SendableChooser();
//		shooterChooser.addDefault("Upper", ShooterChoices.UPPER);
//		shooterChooser.addObject("Lower", ShooterChoices.LOWER);
//		SmartDashboard.putData("Select How Scorpio Will Shoot", shooterChooser);
//
//	}
	

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
	public void autonomousInit() {

		encoder.reset();		
//		ultrasonic.ultraStart();
		SmartDashboard.putString("Starting Gyro Angle", gyro.getGyroAngle()+"");
		
		autonomousSubsystem = new Autonomous();
		
		AutonomousPosition position = (AutonomousPosition) autoPositionChooser.getSelected();
		AutonomousObstacle obstacle = (AutonomousObstacle) autoObstacleChooser.getSelected();
		autonomousCommand = new RunAutonomous(position, obstacle);


		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
	
		Scheduler.getInstance().run();

	}

	public void teleopInit() {	
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
//		cameraControlStick = new CameraControlStick();
		
		// set the default
		Command command = new RunJoystickTank();
//		Command command = new RunGameTank();
		command.start();
	}

	/**
	 * This function is called periodically during operator control6
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		double LeftJoystickStick = Math.abs(oi.gamePad1.getRawAxis(1));
		double RightJoystickStick = Math.abs(oi.gamePad1.getRawAxis(5));
		SmartDashboard.putString("Rightjoystick", RightJoystickStick+"");
		SmartDashboard.putString("Leftjoystick", LeftJoystickStick+"");
		SmartDashboard.putString("handVictorSpeed", RobotMap.handVictor.getSpeed()+"");
//		cameraControlStick.execute();
				
		if (oi.getCoPilotRightTrigger() >= 0.5)
		{	
		   	inTake.runUpwardSlow();
		
		}
		else if (oi.getCoPilotLeftTrigger() >= 0.5)
		{
		   	inTake.runDownward();
		}
		else 
		{
			inTake.stop();
		}
							
		
		if (Math.abs(oi.gamePad1.getRawAxis(5)) > Math.abs(oi.gamePad1.getRawAxis(1)))
		{	
			if (Math.abs(oi.gamePad1.getRawAxis(5)) > 0.079)
			{
				externalHand.DriveHandAtFull();
			}
		}
		else if (Math.abs(oi.gamePad1.getRawAxis(5)) < Math.abs(oi.gamePad1.getRawAxis(1)))
		{
			if (Math.abs(oi.gamePad1.getRawAxis(1)) > 0.079)
			{
				externalHand.runDownwardTeleop();
			}
		}
		
		
		
		
		
		//externalHand2.runDownwardTeleopRightStick();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
		SmartDashboard.putString("Starting Gyro Angle", gyro.getGyroAngle()+"");

	}
}
