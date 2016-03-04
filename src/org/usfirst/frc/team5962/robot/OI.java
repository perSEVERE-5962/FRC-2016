package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	public JoystickButton rightJoystickButton1Index;
	public JoystickButton rightJoystickButton2Thumb;
	public JoystickButton RightJoystickButton3;
	public JoystickButton RightJoystickButton4;

	public JoystickButton gameArcadeMode;
	public JoystickButton gameXArcadeMode;
	public JoystickButton joystickArcadeModeLeft;
	public JoystickButton joystickArcadeModeRight;
	public JoystickButton joystickTankMode;
	public JoystickButton gameTankMode;
	public JoystickButton gameXTankMode;

	public String currentDriveMode = "";

	public OI() {

		// add game controllers
		gamePad1 = new Joystick(0);
		gamePad2 = new Joystick(1);
		joystickLeft = new Joystick(2);
		joystickRight = new Joystick(3);

		// buttons added to the right joystick
		rightJoystickButton1Index = new JoystickButton(joystickRight, 1);
		rightJoystickButton2Thumb = new JoystickButton(joystickRight, 2);
		RightJoystickButton3 = new JoystickButton(joystickRight, 3);
		RightJoystickButton4 = new JoystickButton(joystickRight, 4);

		// Buttons to control mode switch
		// Currently only work on the right joystick
		gameArcadeMode = new JoystickButton(joystickRight, 11);
		gameXArcadeMode = new JoystickButton(joystickRight, 9);
		joystickArcadeModeRight = new JoystickButton(joystickRight, 7);
		joystickArcadeModeLeft = new JoystickButton(joystickLeft, 7);
		joystickTankMode = new JoystickButton(joystickRight, 8);
		gameTankMode = new JoystickButton(joystickRight, 12);
		gameXTankMode = new JoystickButton(joystickRight, 10);

		// Driver mode Commands attached to the buttons
		//gameArcadeMode.whenPressed(new RunArcadeGame());
		//gameXArcadeMode.whenPressed(new RunArcadeXGame());
		joystickArcadeModeRight.whenPressed(new RunArcadeJoystick(true));
		joystickArcadeModeLeft.whenPressed(new RunArcadeJoystick(false));
		joystickTankMode.whenPressed(new RunJoystickTank());
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
		return gamePad2.getRawAxis(3);
	}
	
	public double getCoPilotLeftTrigger() {
		return gamePad2.getRawAxis(2);
	}
	
	public double getCoPilotHorizontalCameraAxis(){
		return gamePad2.getRawAxis(4);
	}
	
	public double getCoPilotVerticalCameraAxis(){
		return gamePad2.getRawAxis(5);
	}
	
	public int getCoPilotPOV(){
		return gamePad1.getPOV();
	}

}
