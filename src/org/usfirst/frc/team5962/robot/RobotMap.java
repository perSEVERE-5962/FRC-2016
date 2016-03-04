package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public static final int COMPETITION_ROBOT_DRIVE = 1;
	public static final int TEST_ROBOT_DRIVE = 2;
	
	public static Victor upperLaunchVictor;
	public static Victor inTakeVictor;

	public static Victor testRobotLeftVictor1;
	public static Victor testRobotLeftVictor2;
	public static Victor testRobotRightVictor1;
	public static Victor testRobotRightVictor2;
	public static Victor handVictor;

	public static Servo axisCameraServoViewHorizontal;
	public static Servo axisCameraServoViewVertical;

	public static CANTalon CANTalon1;
	public static CANTalon CANTalon2;
	public static CANTalon CANTalon3;
	public static CANTalon CANTalon4;

	public static DigitalInput manualSwitch;

	public static RobotDrive myRobot;
	
	// PWM Channels
	private final static int PWM_CHANNEL_0 = 0;
	private final static int PWM_CHANNEL_1 = 1;
	private final static int PWM_CHANNEL_2 = 2;
	private final static int PWM_CHANNEL_3 = 3;
	private final static int PWM_CHANNEL_4 = 4;
	//private final static int PWM_CHANNEL_5 = 5;
	private final static int PWM_CHANNEL_6 = 6;
	private final static int PWM_CHANNEL_7 = 7;
	private final static int PWM_CHANNEL_8 = 8;
	private final static int PWM_CHANNEL_9 = 9;
	
	// DIO Channels
	//private final static int DIO_CHANNEL_0 = 0;
	//private final static int DIO_CHANNEL_1 = 1;
	//private final static int DIO_CHANNEL_2 = 2;
	//private final static int DIO_CHANNEL_3 = 3;
	//private final static int DIO_CHANNEL_4 = 4;
	//private final static int DIO_CHANNEL_5 = 5;
	//private final static int DIO_CHANNEL_6 = 6;
	public final static int DIO_CHANNEL_7 = 7;
	public final static int DIO_CHANNEL_8 = 8;
	private final static int DIO_CHANNEL_9 = 9;
	
	public final static int ULTRASONIC_ANALOG_CHANNEL = 3;

	public static void init(int driveType) {
		manualSwitch = new DigitalInput(DIO_CHANNEL_9); // switch this to a ultrasonic

		 upperLaunchVictor = new Victor(PWM_CHANNEL_8);
		inTakeVictor = new Victor(PWM_CHANNEL_9);

		// inTakeVictor.setInverted(true);

		handVictor = new Victor(PWM_CHANNEL_4);

		axisCameraServoViewHorizontal = new Servo(PWM_CHANNEL_2);
		axisCameraServoViewVertical = new Servo(PWM_CHANNEL_3);
		
		switch (driveType) {
		case TEST_ROBOT_DRIVE:
			testRobotLeftVictor1 = new Victor(PWM_CHANNEL_0);
			testRobotLeftVictor2 = new Victor(PWM_CHANNEL_1);
			testRobotRightVictor1 = new Victor(PWM_CHANNEL_6);
			testRobotRightVictor2 = new Victor(PWM_CHANNEL_7);
			myRobot = new RobotDrive(testRobotLeftVictor1, testRobotLeftVictor2, testRobotRightVictor1, testRobotRightVictor2);	
			break;
		case COMPETITION_ROBOT_DRIVE:
		default:
			CANTalon1 = new CANTalon(10);
			CANTalon2 = new CANTalon(11);
			CANTalon3 = new CANTalon(12);
			CANTalon4 = new CANTalon(13);
			CANTalon1.setInverted(true);
			CANTalon2.setInverted(true);
			CANTalon3.setInverted(true);
			CANTalon4.setInverted(true);
			myRobot = new RobotDrive(CANTalon1,CANTalon2,CANTalon3,CANTalon4);
			break;	
		}
	}

}
