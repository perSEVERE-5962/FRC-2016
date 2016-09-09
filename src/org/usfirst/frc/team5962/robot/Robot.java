
package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team5962.robot.commands.RunAutonomous;
import org.usfirst.frc.team5962.robot.commands.RunGameTank;
import org.usfirst.frc.team5962.robot.commands.RunGameXTank;
import org.usfirst.frc.team5962.robot.commands.RunJoystickTank;
import org.usfirst.frc.team5962.robot.sensors.RobotEncoder;
import org.usfirst.frc.team5962.robot.sensors.RobotGyro;

import org.usfirst.frc.team5962.robot.sensors.RobotUltrasonicDigital;
import org.usfirst.frc.team5962.robot.subsystems.TimedAutoDrive;
import org.usfirst.frc.team5962.robot.subsystems.Autonomous;
import org.usfirst.frc.team5962.robot.subsystems.Camera;
import org.usfirst.frc.team5962.robot.subsystems.UpperLaunchMotor;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import org.usfirst.frc.team5962.robot.subsystems.Drive;
import org.usfirst.frc.team5962.robot.subsystems.InTakeMotor;
import org.usfirst.frc.team5962.robot.subsystems.JoystickThrottle;
import org.usfirst.frc.team5962.robot.subsystems.ManipulatorVictor;
import org.usfirst.frc.team5962.robot.subsystems.ExternalHand;
import org.usfirst.frc.team5962.robot.subsystems.Scaling;

import java.lang.Math;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static Drive drive;
	public static Autonomous autonomousSubsystem;
	public TimedAutoDrive timedAutoDrive;
	public static JoystickThrottle throttle;
	
	public static InTakeMotor inTake;
	public static ExternalHand externalHand;
	//public static ManipulatorVictor manipulatorVictor;
	public static Scaling scaling;
	public static Camera camera = new Camera();
	
	public static int currsession = 1;
	public static int sessionfront = 1;
	public static int sessionback = 0;
	public static int camMode = 1;
	public static Image frame;
	
	public static int lowDown = 0;
	

	public static RobotGyro gyro= new RobotGyro();

	
	public static RobotEncoder encoder = new RobotEncoder();

    public static RobotUltrasonicDigital ultrasonicShoot;
   // public static RobotUltrasonicDigital ultrasonicFront;+

	
	public static OI oi;
	
	
	public static double maxSpeed = 1;
	
	public static enum AutonomousPosition {
		POSITION_1,
		POSITION_2,
		POSITION_3,
		POSITION_4,
		POSITION_5
	}
	
	public static enum MaxSpeedOptions {
		ONE_QUARTER,
		HALF,
		THREE_QUARTER,
		FULL
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
	
	SendableChooser maxSpeedChooser;
	
	public void robotInit() {	
		/*
		 * Choose the proper robot drive and then re-deploy to the rio
		 */
		RobotMap.init();
		
		drive = new Drive();
		inTake = new InTakeMotor();
		externalHand = new ExternalHand();
//		manipulatorVictor = new ManipulatorVictor();
		scaling = new Scaling();
		
//		Camera.configureCamMode(Camera.CAM_MODE_FRONT);
		
		//try{
//			sessionfront = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//			sessionback = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			//}catch(Exception ex){
				
			//}
		
		


	 //ultrasonicFront = new RobotUltrasonicDigital(RobotMap.DIO_CHANNEL_8, RobotMap.DIO_CHANNEL_7);
     ultrasonicShoot = new RobotUltrasonicDigital(RobotMap.DIO_CHANNEL_8, RobotMap.DIO_CHANNEL_7);
	//ultrasonicFront = ultrasonicShoot;
       oi = new OI();
		
		gyro.resetGyro();
	
		initAutonomousPositionChooser();
		initAutonomousObstacleChooser();
		initMaxSpeedChooser();
	}
	
	private void initMaxSpeedChooser() {
		maxSpeedChooser = new SendableChooser();
		maxSpeedChooser.addDefault("1/2  Speed", MaxSpeedOptions.HALF);
		maxSpeedChooser.addObject("1/4 Speed", MaxSpeedOptions.ONE_QUARTER);
		maxSpeedChooser.addObject("3/4 Speed", MaxSpeedOptions.THREE_QUARTER);
		maxSpeedChooser.addObject("Full Speed", MaxSpeedOptions.FULL);	
		SmartDashboard.putData("Select Max Speed", maxSpeedChooser);
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

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
	public void autonomousInit() {

		encoder.reset();		
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
		
//		CameraServer.getInstance().setQuality(50);
//		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
//		currsession = sessionfront;
		
		
		
		// set the default
//		Command command = new RunJoystickTank();
		Command command = new RunGameXTank(getMaxSpeed());
		command.start();
	}
	
	private double getMaxSpeed(){
		MaxSpeedOptions maxSpeedOption = (MaxSpeedOptions) maxSpeedChooser.getSelected();
		double maxSpeed=1;
		switch (maxSpeedOption) {
			case ONE_QUARTER:
				maxSpeed=0.25;
				break;
			case HALF:
				maxSpeed=0.5;
				break;
			case THREE_QUARTER:
				maxSpeed=0.75;
				break;
			case FULL:
			default:
				maxSpeed=1;
		}
		return maxSpeed;
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
		
		// process the intake/shoot controls
		
		
		if (oi.getCoPilotRightTrigger() >= 0.5 && oi.getCoPilotLeftTrigger() < 0.5 && oi.getJoystickRightTrigger() == false && oi.getJoystickLeftTrigger() == false)
		{	
		   	inTake.runUpwardSlow();
		
		}
		else if (oi.getCoPilotLeftTrigger() >= 0.5 && oi.getJoystickRightTrigger() == false && oi.getJoystickLeftTrigger() == false)
		{
		   	inTake.runDownward();
		}
		else if (oi.getJoystickLeftTrigger() == true && oi.getJoystickRightTrigger() == false){
			inTake.runDownward();
		}
		else if (oi.getJoystickRightTrigger() == true){
			inTake.runUpwardSlow();
		}
		else 
		{
			inTake.stop();
		}
							
		
		
		
		// process the lift contols 
		double driverPOV = oi.getDriverPOV();
		if (driverPOV >= 0 && (driverPOV <= 90 || driverPOV >= 270) && Math.abs(oi.getCoPilotScalingStick()) <= 0.079) {
			// move up
			scaling.runUpward();
		} else if (driverPOV >= 0 && (driverPOV > 90 && driverPOV < 270) && Math.abs(oi.getCoPilotScalingStick()) <= 0.079) {
			// move down
			scaling.runDownward();
		} else if (Math.abs(oi.getCoPilotScalingStick()) > 0.079) {
			scaling.scaleMotor();
		} else {
			scaling.stop();
		}


		if (Math.abs(oi.getCoPilotBackArmStick()) > 0.079 && oi.getJoystickRightThree() == false && oi.getJoystickRightFive() == false)
		{
			externalHand.runDownwardTeleop();
		}
		else if(oi.getJoystickRightThree() == true && oi.getJoystickRightFive() == false){
			externalHand.runDownward();
		}
		else if(oi.getJoystickRightFive() == true){
			externalHand.runUpward();
		}
		else {
			externalHand.stop();
		}
		
		//try{
//		Camera.grabCameraImage();
//		CameraServer.getInstance().setImage(frame);
		//}catch(Exception ex){
			
		//}
		
		//externalHand2.runDownwardTeleopRightStick();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
		SmartDashboard.putString("Starting Gyro Angle", gyro.getGyroAngle()+"");
		ultrasonicShoot.getRange();
	}
}
