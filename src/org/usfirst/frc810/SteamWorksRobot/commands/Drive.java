// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc810.SteamWorksRobot.commands;
import org.usfirst.frc810.SteamWorksRobot.DriveOrientation;
import org.usfirst.frc810.SteamWorksRobot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_SpeedMultiplier;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Drive(double SpeedMultiplier) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_SpeedMultiplier = SpeedMultiplier;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double x = Robot.oi.driveStick.getX();
    	double y = Robot.oi.driveStick.getY();
    	double r = -Robot.oi.driveStick.getZ();
    	if(Math.abs(x)< .2){
    		x = 0;
    	}
    	if(Math.abs(y)< .2||Robot.oi.getDriveStick().getRawButton(3)){
    		y = 0;
    	}
    	if(Math.abs(r)< .2){
    		r = 0; 
    	}
    	else{
    		Robot.driveTrain.resetG();
    	}
    	if(m_SpeedMultiplier == -2){
    		double tempMultiplier = (Robot.oi.driveStick.getThrottle()*-.4)+.6;
    		SmartDashboard.putNumber("Multiplier", tempMultiplier);
    		DriveOrientation.driveMecanum(x*tempMultiplier, y*tempMultiplier, r*tempMultiplier);
    	} else{
    		DriveOrientation.driveMecanum(x*m_SpeedMultiplier, y*m_SpeedMultiplier, r*m_SpeedMultiplier);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
