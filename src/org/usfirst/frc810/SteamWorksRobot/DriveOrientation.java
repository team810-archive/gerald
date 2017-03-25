package org.usfirst.frc810.SteamWorksRobot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public enum DriveOrientation {
	IntakeFront,GearFront,ClimberFront,DumpFront;
	private static DriveOrientation currentOrientation = IntakeFront;

	
	public void setAsCurrent(){
		currentOrientation = this;
		SmartDashboard.putString("Orientation", this.name());
		System.out.println("Orientation: "+this.name());
	}
	
	public static DriveOrientation getCurrentOrientation(){
		return currentOrientation;
	}
	
	public static void driveMecanum(double x, double y, double r){
		switch(currentOrientation){
		case IntakeFront:
			Robot.driveTrain.mecanum(x, y, r);
			break;
		case GearFront:
			Robot.driveTrain.mecanum(y, -x, r);
			break;
		case ClimberFront:
			Robot.driveTrain.mecanum(-x, -y, r);
			break;
		case DumpFront:
			Robot.driveTrain.mecanum(-y, x, r);
			break;
		}
	}

}
