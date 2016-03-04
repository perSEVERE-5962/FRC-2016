package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.sensors.RobotGyro;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TimedAutoDrive extends Subsystem {
	double timetest = 0;

	public void autoDrive(RobotGyro gyro) {

		SmartDashboard.putString("Time", "" + timetest);

		if (timetest <= 332) {
			/*
			 * 116.5 in 1.0 = 79 Dont use inconsistent 0.75 = 102 Slightly
			 * inconsistent 0.5 = 150 0.25 = 332
			 */
			Robot.autonomousSubsystem.driveTime(gyro);
			timetest++;
		}

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
