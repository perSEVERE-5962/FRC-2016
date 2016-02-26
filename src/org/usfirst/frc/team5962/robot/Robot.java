
package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team5962.robot.commands.CameraControlStick;
import org.usfirst.frc.team5962.robot.commands.ReleaseBallTop;
import org.usfirst.frc.team5962.robot.sensors.RobotEncoder;
import org.usfirst.frc.team5962.robot.sensors.RobotGyro;
import org.usfirst.frc.team5962.robot.sensors.RobotUltrasonicDigital;
import org.usfirst.frc.team5962.robot.subsystems.TimedAutoDrive;
import org.usfirst.frc.team5962.robot.subsystems.Camera;
import org.usfirst.frc.team5962.robot.subsystems.UpperLaunchMotor;
import org.usfirst.frc.team5962.robot.subsystems.Drive;
import org.usfirst.frc.team5962.robot.subsystems.InTakeMotor;
import org.usfirst.frc.team5962.robot.subsystems.ExternalHand;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static Drive driveSubSystem;
	
	public static UpperLaunchMotor upperLaunchMotor;
	public static InTakeMotor inTake;
	public static ReleaseBallTop releaseBallTop;
	public static ExternalHand externalHand;
	
	public static Camera camera;
	public static CameraControlStick cameraControlStick;
	
	public static RobotGyro gyro;
	public static RobotEncoder encoder;
	public static RobotUltrasonicDigital ultrasonic;
	
	public static OI oi;
	

	public static enum DriveType {
		COMPETITION, TEST
	}
	
	private Command autonomousCommand;
	// private SendableChooser chooser;
	private SendableChooser robotChooser;
		
	private TimedAutoDrive timedAutoDrive;
	private boolean useTimedAutoDrive = false;

	public void robotInit() {	
		RobotMap.init();
		
		// set the default
		driveSubSystem = new Drive(DriveType.COMPETITION);
		
		// create the robot chooser
 		robotChooser = new SendableChooser();
 		robotChooser.addDefault("Default Robot", new Drive(DriveType.COMPETITION));
 		robotChooser.addObject("Competition Robot", new Drive(DriveType.COMPETITION));
 		robotChooser.addObject("Test Robot", new Drive(DriveType.TEST));
 		SmartDashboard.putData("Robot Type", robotChooser);		
		
 		if (useTimedAutoDrive == true) {
 			timedAutoDrive = new TimedAutoDrive();
 		}
		
		upperLaunchMotor = new UpperLaunchMotor();
		inTake = new InTakeMotor();
		releaseBallTop = new ReleaseBallTop();
		externalHand= new ExternalHand();
		
		camera = new Camera();
		cameraControlStick = new CameraControlStick();
		
		gyro = new RobotGyro();
		encoder = new RobotEncoder();
		ultrasonic = new RobotUltrasonicDigital();
		
		oi = new OI();
	}

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	double timeTest = 0;
	public void autonomousInit() {
		driveSubSystem = (Drive) robotChooser.getSelected();
		
		gyro.resetGyro();
		timeTest = 0;
		encoder.reset();

		int angleInt = gyro.getGyroAngle();
		SmartDashboard.putString("Current Gyro Angle", "" + angleInt);

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		// moving negative is right, positive is left
		// gyro negative is left, positive is right
		Scheduler.getInstance().run();
		
		if (useTimedAutoDrive == false){
			driveSubSystem.autoDrive();
			SmartDashboard.putString("IntakeSpeed", "" + RobotMap.inTakeVictor.getSpeed());
		} else {
			// Switch out Encoders^ Time v
			timedAutoDrive.autoDrive(gyro);
		}
/*
		int angleInt = gyro.getGyroAngle();
		SmartDashboard.putString("Gyro Angle", "" + angleInt);
		
		boolean rturn = false;
		boolean ultraSensor = false;
		if(ultraSensor == false){	
			driveSubSystem.drive(-0.3, -angleInt * 0.03);
		}
		else if(ultraSensor == true && rturn == false && angleInt < 90){
			driveSubSystem.drive(0.1, -1);
		}
		else if(angleInt >= 90 && angleInt < 91){
			rturn = true;
			ultraSensor = false;
			gyro.resetGyro();
		}
		else if(ultraSensor == true && rturn == true){
			releaseBallTop.start();
		}
*/		
	}

	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		driveSubSystem = (Drive) robotChooser.getSelected();
	}

	/**
	 * This function is called periodically during operator control6
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		int angleInt = gyro.getGyroAngle();
		
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
				
		SmartDashboard.putString("Active Driver Mode", oi.currentDriveMode);
		SmartDashboard.putString("Current Gyro Angle", "" + angleInt);
		SmartDashboard.putString("Current Throttle Setting", "" + oi.joystickRight.getThrottle());
		SmartDashboard.putNumber("Current Ultrasonic Range", ultrasonic.getRange());
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public static void setThrottle(double throttle) {
		driveSubSystem.setThrottle(throttle);
	}
}
