package org.usfirst.frc810.SteamWorksRobot;

public enum DriveOrientation {
	IntakeFront,GearFront,ClimberFront;
	private static DriveOrientation currentOrientation = IntakeFront;
	public void setAsCurrent(){
		currentOrientation = this;
	}
	public static void driveMecanum(double x, double y, double r){
		switch(currentOrientation){
		case IntakeFront:
			Robot.driveTrain.mecanum(x, y, r);
			break;
		case GearFront:
			Robot.driveTrain.mecanum(-y, x, r);
			break;
		case ClimberFront:
			Robot.driveTrain.mecanum(-x, -y, r);
			break;
		}
	}

}
