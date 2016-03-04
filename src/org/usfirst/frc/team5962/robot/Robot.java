
package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team5962.robot.commands.CameraControlStick;
import org.usfirst.frc.team5962.robot.commands.ReleaseBallTop;
import org.usfirst.frc.team5962.robot.commands.RunAutonomous;
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

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static Drive drive;
	public static Autonomous autonomousSubsystem;
	public TimedAutoDrive timedAutoDrive;
	public static JoystickThrottle throttle;
	
	public static UpperLaunchMotor upperLaunchMotor;
	public static InTakeMotor inTake;
	public static ReleaseBallTop releaseBallTop;
	public static ExternalHand externalHand;
	
	public static Camera camera;
	//public static CameraControlPOV cameraControl;
	public static CameraControlStick cameraControlStick;
	
	public static RobotGyro gyro;
	public static RobotEncoder encoder;
	public static RobotUltrasonicDigital ultrasonic;
	
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
		ROUCH_TERRAIN
	}
	
	RunAutonomous autonomousCommand;
	SendableChooser autoPositionChooser;
	SendableChooser autoObstacleChooser;

	public void robotInit() {	
		/*
		 * Choose the proper robot drive and then re-deploy to the rio
		 */
		RobotMap.init(RobotMap.COMPETITION_ROBOT_DRIVE);
	//	RobotMap.init(RobotMap.TEST_ROBOT_DRIVE);
		
		drive = new Drive();
		
		timedAutoDrive = new TimedAutoDrive();
		throttle = new JoystickThrottle();
		
		upperLaunchMotor = new UpperLaunchMotor();
		inTake = new InTakeMotor();
		releaseBallTop = new ReleaseBallTop();
		externalHand= new ExternalHand();
		
		gyro = new RobotGyro();
		encoder = new RobotEncoder();
		ultrasonic = new RobotUltrasonicDigital(RobotMap.DIO_CHANNEL_8, RobotMap.DIO_CHANNEL_7);
		
		oi = new OI();
		
		initAutonomousPositionChooser();
		initAutonomousObstacleChooser();
		
	}
	
	private void initAutonomousPositionChooser() {
		autoPositionChooser = new SendableChooser();
		autoPositionChooser.addDefault("Position 1", AutonomousPosition.POSITION_1);
		autoPositionChooser.addObject("Position 2", AutonomousPosition.POSITION_2);
		autoPositionChooser.addObject("Position 3", AutonomousPosition.POSITION_3);
		autoPositionChooser.addObject("Position 4", AutonomousPosition.POSITION_4);
		autoPositionChooser.addObject("Position 5", AutonomousPosition.POSITION_5);	
	}
	
	private void initAutonomousObstacleChooser() {
		autoObstacleChooser = new SendableChooser();
		autoObstacleChooser.addDefault("Low Bar", AutonomousObstacle.LOW_BAR);
		autoObstacleChooser.addObject("Portcullis", AutonomousObstacle.PORTCULLIS);
		autoObstacleChooser.addObject("Cheval de Frise", AutonomousObstacle.CHEVAL_DE_FRISE);
		autoObstacleChooser.addObject("Moat", AutonomousObstacle.MOAT);
		autoObstacleChooser.addObject("Ramparts", AutonomousObstacle.RAMPARTS);
		autoObstacleChooser.addObject("Drawbridge", AutonomousObstacle.DRAWBRIDGE);
		autoObstacleChooser.addObject("Sally Port", AutonomousObstacle.SALLY_PORT);
		autoObstacleChooser.addObject("Rock Wall", AutonomousObstacle.ROCK_WALL);
		autoObstacleChooser.addObject("Rough Terrain", AutonomousObstacle.ROUCH_TERRAIN);		
	}

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	double timeTest = 0;
	public void autonomousInit() {
		gyro.resetGyro();
		timeTest = 0;
		encoder.reset();
		
		ultrasonic.ultraStart();

		int angleInt = gyro.getGyroAngle();
		SmartDashboard.putString("Gyro Angle", "" + angleInt);
		
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
	int i = 0;
	
	public void autonomousPeriodic() {
		
		//double distance = ultrasonic.getRange();
		
		// moving negative is right, positive is left
		// gyro negative is left, positive is right
		Scheduler.getInstance().run();
		//ultrasonic.ultrasonic.ping();
//		 RobotMap.myRobot.setMaxOutput(1);
//		 drive.autoDrive();
//		SmartDashboard.putString("IntakeSpeed", "" + RobotMap.inTakeVictor.getSpeed());
//		
//		SmartDashboard.putNumber("Ultrasonic Range Autonomous", ultrasonic.getRange());
//		SmartDashboard.putString("Ultrasonic Range Valid?", ""+ultrasonic.getValid());
//		
//		// Switch out Encoders^ Time v
//		// timedAutoDrive.autoDrive(gyro);
//
//		int angleInt = gyro.getGyroAngle();
//		SmartDashboard.putString("Gyro Angle", "" + angleInt);
//		SmartDashboard.putString("Key", ""+i++);
//		
	}

	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		camera = new Camera();
		//cameraControl= new CameraControl();
		cameraControlStick = new CameraControlStick();
	}

	/**
	 * This function is called periodically during operator control6
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		//int angleInt = gyro.getGyroAngle();
		
		cameraControlStick.execute();
				
		if (oi.getCoPilotRightTrigger() >= 0.5)
		{	
		   	inTake.runUpwardSlow();
		
		}
		else {
			inTake.stop();
		}				
				
		if (oi.getCoPilotLeftTrigger() >= 0.5)
		{
			upperLaunchMotor.runUpward();
		   	inTake.runUpward();
		}
		else 
		{
			upperLaunchMotor.stop();
			inTake.stop();
		}
								
		externalHand.runDownward();		
				
		//SmartDashboard.putString("Gyro Angle", "" + angleInt);
		SmartDashboard.putString("Throttle", "" + oi.joystickRight.getThrottle());
		SmartDashboard.putString("left trigger", "" + oi.gamePad2.getRawAxis(2));
		SmartDashboard.putString("right trigger", "" + oi.gamePad2.getRawAxis(3));
		SmartDashboard.putString("Driver Mode Choose", oi.currentDriveMode);
		SmartDashboard.putNumber("Ultrasonic Range", ultrasonic.getRange());
        SmartDashboard.putString("Intake","" + RobotMap.inTakeVictor.getSpeed());	
        SmartDashboard.putString("Arm", "" + RobotMap.handVictor.getSpeed());
	 /*   SmartDashboard.putString("Talon1", "" + RobotMap.CANTalon1.getSpeed());
	    SmartDashboard.putString("Talon2", "" + RobotMap.CANTalon2.getSpeed());
	    SmartDashboard.putString("Talon3", "" + RobotMap.CANTalon3.getSpeed());
	    SmartDashboard.putString("Talon4", "" + RobotMap.CANTalon4.getSpeed());
	*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
