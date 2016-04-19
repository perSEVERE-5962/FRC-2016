package org.usfirst.frc.team5962.robot.subsystems;

import org.usfirst.frc.team5962.robot.Robot;
import org.usfirst.frc.team5962.robot.RobotMap;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {
	CameraServer server;
	
	public static final int CAM_MODE_FRONT = 1;
	public static final int CAM_MODE_BACK = 2;
	
	static int camera = Robot.sessionfront;

	public Camera() {

		server = CameraServer.getInstance();
		if (server != null) {
			server.setQuality(50);
			// the camera name (ex "cam0") can be found through the roborio web
			// interface
			server.startAutomaticCapture("cam0");
		}

	}
	
	public static void configureCamMode(int cam){
		//try{
//		switch (cam) {
//		case CAM_MODE_FRONT:
//		default:
//			NIVision.IMAQdxStopAcquisition(Robot.sessionback);
//			camera = Robot.sessionfront;
//			NIVision.IMAQdxConfigureGrab(Robot.sessionfront);
//			//NIVision.IMAQdxStartAcquisition(Robot.sessionfront);
//		break;
//			
//		case CAM_MODE_BACK:
//			NIVision.IMAQdxStopAcquisition(Robot.sessionfront);
//			camera = Robot.sessionback;
//			NIVision.IMAQdxConfigureGrab(Robot.sessionback);
//			//NIVision.IMAQdxStartAcquisition(Robot.sessionback);
//		break;
//		}
		//}catch(Exception ex){
			
		//}
	}
	
	public static void grabCameraImage(){
		//try{
//		NIVision.IMAQdxGrab(camera, Robot.frame, 1);
		//}catch(Exception ex){
			
		//}
	}
	
	public void moveX(double changeValue) {
//		RobotMap.axisCameraServoViewHorizontal.set(changeValue);

	}

	public void moveY(double changeValue) {
//		RobotMap.axisCameraServoViewVertical.set(changeValue);

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
