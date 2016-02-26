package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.RobotMap;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {
	CameraServer server;

	public Camera() {
		server = CameraServer.getInstance();
		if (server != null) {
			server.setQuality(50);
			// the camera name (ex "cam0") can be found through the roborio web
			// interface
			server.startAutomaticCapture("cam0");
		}
	}

	public void moveX(double changeValue) {
		RobotMap.axisCameraServoViewHorizontal.set(changeValue);

	}

	public void moveY(double changeValue) {
		RobotMap.axisCameraServoViewVertical.set(changeValue);

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
