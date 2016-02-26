package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static Victor upperLaunchVictor;
	public static Victor inTakeVictor;

	public static Victor handVictor;

	public static Servo axisCameraServoViewHorizontal;
	public static Servo axisCameraServoViewVertical;
	
	public static Victor testRobotLeftVictor;
	public static Victor testRobotRightVictor;
	public static CANTalon CANTalon1;
	public static CANTalon CANTalon2;
	public static CANTalon CANTalon3;
	public static CANTalon CANTalon4;

	public static DigitalInput manualSwitch;

	public static void init() {
		manualSwitch = new DigitalInput(9); // switch this to a ultrasonic

		upperLaunchVictor= new Victor(8);
		inTakeVictor = new Victor(9);
		// inTakeVictor.setInverted(true);

		handVictor = new Victor(5);

		CANTalon1 = new CANTalon(10);
		CANTalon2 = new CANTalon(11);
		CANTalon3 = new CANTalon(12);
		CANTalon4 = new CANTalon(13);
		testRobotLeftVictor = new Victor(0);
		testRobotRightVictor = new Victor(1);

		axisCameraServoViewHorizontal = new Servo(2);
		axisCameraServoViewVertical = new Servo(3);	
	}

}
