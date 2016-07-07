package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5962.robot.commands.CameraSwitch;
import org.usfirst.frc.team5962.robot.commands.RunArcadeJoystick;
import org.usfirst.frc.team5962.robot.commands.RunJoystickTank;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public Joystick gamePad1;
	public Joystick gamePad2;
	public Joystick joystickLeft;
	public Joystick joystickRight;

	// Buttons added to RightJoystick
	public JoystickButton RightJoystickButton1Index;
	public JoystickButton RightJoystickButton2Thumb;
	public JoystickButton RightJoystickButton3;
	public JoystickButton RightJoystickButton4;
	public JoystickButton RightJoystickButton5;
	public JoystickButton RightJoystickButton6;
	
	public JoystickButton LeftJoystickButton1Index;
	public JoystickButton LeftJoystickButton2Thumb;
	public JoystickButton LeftJoystickButton3;
	public JoystickButton LeftJoystickButton4;
	public JoystickButton LeftJoystickButton5;
	public JoystickButton LeftJoystickButton6;

	public JoystickButton gameArcadeMode;
	public JoystickButton gameXArcadeMode;
	public JoystickButton joystickArcadeModeLeft;
	public JoystickButton joystickArcadeModeRight;
	public JoystickButton joystickTankMode;
	public JoystickButton gameTankMode;
	public JoystickButton gameXTankMode;
	public JoystickButton cameraSwitch;
	//public JoystickButton joyCameraSwitch;

	private String currentDriveMode = "";

	public OI() {

		// add game controllers
		gamePad1 = new Joystick(0);
		gamePad2 = new Joystick(1);
		joystickLeft = new Joystick(2);
		joystickRight = new Joystick(3);

		// buttons added to the right joystick
		RightJoystickButton1Index = new JoystickButton(joystickRight, 1);
		RightJoystickButton2Thumb = new JoystickButton(joystickRight, 2);
		RightJoystickButton3 = new JoystickButton(joystickRight, 3);
		RightJoystickButton4 = new JoystickButton(joystickRight, 4);
		RightJoystickButton5 = new JoystickButton(joystickRight, 5);
		RightJoystickButton6 = new JoystickButton(joystickRight, 6);
		
		LeftJoystickButton1Index = new JoystickButton(joystickLeft, 1);
		LeftJoystickButton2Thumb = new JoystickButton(joystickLeft, 2);
		LeftJoystickButton3 = new JoystickButton(joystickLeft, 3);
		LeftJoystickButton4 = new JoystickButton(joystickLeft, 4);
		LeftJoystickButton5 = new JoystickButton(joystickLeft, 5);
		LeftJoystickButton6 = new JoystickButton(joystickLeft, 6);

		// Buttons to control mode switch
		// Currently only work on the right joystick
		gameArcadeMode = new JoystickButton(joystickRight, 11);
		gameXArcadeMode = new JoystickButton(joystickRight, 9);
		joystickArcadeModeRight = new JoystickButton(joystickRight, 7);
		joystickArcadeModeLeft = new JoystickButton(joystickLeft, 7);
		joystickTankMode = new JoystickButton(joystickRight, 8);
		gameTankMode = new JoystickButton(joystickRight, 12);
		gameXTankMode = new JoystickButton(joystickRight, 10);
		cameraSwitch = new JoystickButton(gamePad1, 1);
		///joyCameraSwitch = new JoystickButton(joystickRight, 3);
		
		
		// Driver mode Commands attached to the buttons
		//gameArcadeMode.whenPressed(new RunArcadeGame());
		//gameXArcadeMode.whenPressed(new RunArcadeXGame());
		joystickArcadeModeRight.whenPressed(new RunArcadeJoystick(true));
		joystickArcadeModeLeft.whenPressed(new RunArcadeJoystick(false));
		joystickTankMode.whenPressed(new RunJoystickTank());
		//cameraSwitch.whenPressed(new CameraSwitch());
		//joyCameraSwitch.whenPressed(new CameraSwitch());
		//gameTankMode.whenPressed(new RunGameTank());
		//gameXTankMode.whenPressed(new RunGameXTank());
		
		
		

		// Use to control the manipulator
		// Currently only works on right Joystick

		// this sets it so that the index finger on the right hand grabs the
		// ball
		//rightJoystickButton1Index.whenPressed(new GrabbingMechanism());
		
		
		// this shoots the ball out. it run both motors
		//RightJoystickButton3.whenPressed(new ReleaseBallTop());

		// manual break gravity motors
//		rightJoystickButton2Thumb.whenPressed(new ManualBreakGravityMotors());
		
		//RightJoystickButton4.whenPressed(new CameraControlPOV());
				
//		if (gamePad2.getTrigger(Hand.kRight) == true)
//		{
//			new GrabbingMechanism();
//		}
//				
//				
//		if (gamePad2.getTrigger(Hand.kLeft) == true)
//		{
//			new ReleaseBallTop();
//		}

	}
	
	public double getCoPilotRightTrigger() {
		double value = gamePad1.getRawAxis(3);
//		SmartDashboard.putString("right trigger", "" + value);
		return value;
	}
	
	public double getCoPilotLeftTrigger() {
		double value = gamePad1.getRawAxis(2);
//		SmartDashboard.putString("left trigger", "" + value);
		return value;
	}
	
	public boolean getJoystickRightTrigger(){
		boolean value = joystickRight.getRawButton(1);
		return value;
	}
	
	public boolean getJoystickLeftTrigger(){
		boolean value = joystickLeft.getRawButton(1);
		return value;
	}
	
	public boolean getJoystickRightThree(){
		boolean value = joystickRight.getRawButton(3);
		return value;
	}
	
	public boolean getJoystickRightFive(){
		boolean value = joystickRight.getRawButton(5);
		return value;
	}
	
//	public double getCoPilotHorizontalCameraAxis(){
//		double value = gamePad1.getRawAxis(4);
////		SmartDashboard.putString("Horizontal Camera Axis", "" + value);
//		return value;
//	}
//	
//	public double getCoPilotVerticalCameraAxis(){
//		double value = gamePad1.getRawAxis(5);
////		SmartDashboard.putString("Vertical Camera Axis", "" + value);
//		return value;
//	}
	
	public int getCoPilotPOV(){
		int value = gamePad1.getPOV();
//		SmartDashboard.putString("POV", "" + value);
		return value;
	}
	
	public int getDriverPOV(){
		int value = joystickRight.getPOV();
//		SmartDashboard.putString("POV", "" + value);
		return value;
	}
	
	public double getCoPilotScalingStick() {
		return gamePad1.getRawAxis(5);
	}
	
	public double getCoPilotBackArmStick() {
		return gamePad1.getRawAxis(1);
	}
	
	public void setCurrentDriverMode(String mode) {
		currentDriveMode = mode;
		SmartDashboard.putString("Driver Mode Choose", currentDriveMode);
	}

}
