package org.usfirst.frc.team5962.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team5962.robot.commands.GrabbingMechanism;
import org.usfirst.frc.team5962.robot.commands.ManualBreakGravityMotors;
import org.usfirst.frc.team5962.robot.commands.ReleaseBallTop;
import org.usfirst.frc.team5962.robot.commands.RunArcadeGame;
import org.usfirst.frc.team5962.robot.commands.RunArcadeJoystick;
import org.usfirst.frc.team5962.robot.commands.RunArcadeXGame;
import org.usfirst.frc.team5962.robot.commands.RunGameTank;
import org.usfirst.frc.team5962.robot.commands.RunGameXTank;
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
	public JoystickButton rightJoystickButton3;
	public JoystickButton rightJoystickButton4;

	public JoystickButton gameArcadeMode;
	public JoystickButton gameXArcadeMode;
	public JoystickButton joystickArcadeMode;
	public JoystickButton joystickTankMode;
	public JoystickButton gameTankMode;
	public JoystickButton gameXTankMode;

	public String currentDriveMode = "";
	
	private boolean isDriverControllingManipulator = false;

	public OI() {

		// add game controllers
		gamePad1 = new Joystick(0);
		gamePad2 = new Joystick(1);
		joystickLeft = new Joystick(2);
		joystickRight = new Joystick(3);

		// Buttons to control mode switch
		// Currently only work on the right joystick
		gameArcadeMode = new JoystickButton(joystickRight, 11);
		gameXArcadeMode = new JoystickButton(joystickRight, 9);
		joystickArcadeMode = new JoystickButton(joystickRight, 7);
		joystickTankMode = new JoystickButton(joystickRight, 8);
		gameTankMode = new JoystickButton(joystickRight, 12);
		gameXTankMode = new JoystickButton(joystickRight, 10);
		
		// Driver mode Commands attached to the buttons
		gameArcadeMode.whenPressed(new RunArcadeGame());
		gameXArcadeMode.whenPressed(new RunArcadeXGame());
		joystickArcadeMode.whenPressed(new RunArcadeJoystick());
		joystickTankMode.whenPressed(new RunJoystickTank());
		gameTankMode.whenPressed(new RunGameTank());
		gameXTankMode.whenPressed(new RunGameXTank());
		
		// enable the joystick buttons if Pilot will be 
		// controlling the manipulator
		if (isDriverControllingManipulator == true) {
			enableJoystickButtons();
		}

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
	
	private void enableJoystickButtons() {
		// buttons added to the right joystick
		rightJoystickButton1Index = new JoystickButton(joystickRight, 1); 	// GrabbingMechanism
		rightJoystickButton2Thumb = new JoystickButton(joystickRight, 2); 	// ReleaseBallTop
		rightJoystickButton3 = new JoystickButton(joystickRight, 3);		// ManualBreakGravityMotors
		//rightJoystickButton4 = new JoystickButton(joystickRight, 4);		// CameraControlPOV
		
		// Use to control the manipulator
		// Currently only works on right Joystick
		
		// this sets it so that the index finger on the right hand grabs the
		// ball
		rightJoystickButton1Index.whenPressed(new GrabbingMechanism());
		
		// this shoots the ball out. it run both motors
		rightJoystickButton3.whenPressed(new ReleaseBallTop());

		// manual break gravity motors
		rightJoystickButton2Thumb.whenPressed(new ManualBreakGravityMotors());
		
		//RightJoystickButton4.whenPressed(new CameraControlPOV());		
		
	}

}
