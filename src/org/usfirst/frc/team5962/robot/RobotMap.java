package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public static final int COMPETITION_ROBOT_DRIVE = 1;
	public static final int TEST_ROBOT_DRIVE = 2;
	
	public static Victor inTakeVictor;
	//public static Victor manipulatorVictor;

	public static Victor testRobotLeftVictor1;
	public static Victor testRobotLeftVictor2;
	public static Victor testRobotRightVictor1;
	public static Victor testRobotRightVictor2;
	public static Victor scalingVictor;
	public static Victor handVictor;
	

//	public static Servo axisCameraServoViewHorizontal;
//	public static Servo axisCameraServoViewVertical;

	public static CANTalon CANTalon1;
	public static CANTalon CANTalon2;
	public static CANTalon CANTalon3;
	public static CANTalon CANTalon4;

//	public static DigitalInput manualSwitch;
//	public static DigitalInput testSwitch;

	public static RobotDrive myRobot;
	
	public static boolean isTestRobot = false;
	
	// PWM Channels
	private final static int PWM_CHANNEL_0 = 0; 
	private final static int PWM_CHANNEL_1 = 1;
	//private final static int PWM_CHANNEL_2 = 2;
	private final static int PWM_CHANNEL_3 = 3;
	private final static int PWM_CHANNEL_4 = 4;
	private final static int PWM_CHANNEL_5 = 5;
	private final static int PWM_CHANNEL_6 = 6;
	private final static int PWM_CHANNEL_7 = 7;
	//private final static int PWM_CHANNEL_8 = 8;
	//private final static int PWM_CHANNEL_9 = 9;
	
	// DIO Channels
	//public final static int DIO_CHANNEL_0 = 0;
	//public final static int DIO_CHANNEL_1 = 1;
	//public final static int DIO_CHANNEL_2 = 2;
	//public final static int DIO_CHANNEL_3 = 3;
	//public final static int DIO_CHANNEL_4 = 4;
	public final static int DIO_CHANNEL_5 = 5;
	public final static int DIO_CHANNEL_6 = 6;
	public final static int DIO_CHANNEL_7 = 7;
	public final static int DIO_CHANNEL_8 = 8;
	public final static int DIO_CHANNEL_9 = 9;
	
	public final static int ULTRASONIC_ANALOG_CHANNEL = 3;

	public static void init() {
		inTakeVictor = new Victor(PWM_CHANNEL_3);
		inTakeVictor.setSafetyEnabled(false);

		handVictor = new Victor(PWM_CHANNEL_4);
		handVictor.setSafetyEnabled(false);
		
		scalingVictor = new Victor(PWM_CHANNEL_5);
		scalingVictor.setSafetyEnabled(false);		
		
//		manipulatorVictor = new Victor(PWM_CHANNEL_2);
//		manipulatorVictor.setSafetyEnabled(false);
		

//		axisCameraServoViewHorizontal = new Servo(PWM_CHANNEL_8);
//		axisCameraServoViewVertical = new Servo(PWM_CHANNEL_9);
		
		// configure the RobotDrive
//		if (testSwitch.get()) {
//			// in test mode
//			configureRobotDrive(COMPETITION_ROBOT_DRIVE);
//		} else {
//			// in competition mode
			configureRobotDrive(TEST_ROBOT_DRIVE);
//		}
	}
		
	private static void configureRobotDrive(int driveType) {
		
		SpeedController leftDrive;
		SpeedController rightDrive;
		
		switch (driveType) {
		case TEST_ROBOT_DRIVE:
			SmartDashboard.putString("RobotDrive Type", "Test Robot");
			testRobotLeftVictor1 = new Victor(PWM_CHANNEL_0);
			testRobotLeftVictor2 = new Victor(PWM_CHANNEL_1);
			testRobotRightVictor1 = new Victor(PWM_CHANNEL_6);
			testRobotRightVictor2 = new Victor(PWM_CHANNEL_7);
			
		    leftDrive = new MultiSpeedController(testRobotLeftVictor1, testRobotLeftVictor2);
		    rightDrive = new MultiSpeedController(testRobotRightVictor1, testRobotRightVictor2);
/*		    
			myRobot = new RobotDrive(testRobotLeftVictor1, testRobotLeftVictor2, testRobotRightVictor1, testRobotRightVictor2);	
*/
		    Robot.encoder.setNumberOfEncoders(1);
		    isTestRobot = true;
		    
			break;
		case COMPETITION_ROBOT_DRIVE:
		default:
			SmartDashboard.putString("RobotDrive Type", "Competition Robot");
			CANTalon1 = new CANTalon(10);
			CANTalon2 = new CANTalon(11);
			CANTalon3 = new CANTalon(12);
			CANTalon4 = new CANTalon(13);

			
		    leftDrive = new MultiSpeedController(CANTalon1, CANTalon2);
		    rightDrive = new MultiSpeedController(CANTalon3, CANTalon4);
		    leftDrive.setInverted(true);
		    rightDrive.setInverted(true);
		    
		    
/*		    
			CANTalon1.setInverted(true);
			CANTalon2.setInverted(true);
			CANTalon3.setInverted(true);
			CANTalon4.setInverted(true);
			myRobot = new RobotDrive(CANTalon1,CANTalon2,CANTalon3,CANTalon4);
*/
			break;	
		}		

		myRobot = new RobotDrive(leftDrive, rightDrive);
	}

}
