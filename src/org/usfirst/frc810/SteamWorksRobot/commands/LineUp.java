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
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vision.VisionListener;

import org.usfirst.frc810.SteamWorksRobot.RobotMap;
import org.usfirst.frc810.SteamWorksRobot.Robot;

/**
 *
 */
public class LineUp extends Command {
	private final double optimalDistance1 = 16;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public LineUp() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    PIDController pid;
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetG();
    	counter = 0;
    	VisionListener.newResult = false;
    	optimalX = RobotMap.getGearPosition().getVisionPosition();
    	strafeout = 0;
    	pid = new PIDController(0,0,0, RobotMap.navX, a -> {});
    	
    	pid.setContinuous(true);
    	pid.setInputRange(-180, 180);
    	pid.setOutputRange(-.5, .5);
    	pid.setAbsoluteTolerance(2);
    	pid.setSetpoint(RobotMap.getGyroAngle());
    	pid.setToleranceBuffer(10);
    	
    	
    	pid.setPID(SmartDashboard.getNumber("PID P",0), SmartDashboard.getNumber("PID I",0), SmartDashboard.getNumber("PID D",0));
    	pid.enable();
    }
   private double optimalX;

    private int counter = 0;
    double strafeout = 0;
    double strafecounter = 0;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double gd = .2;
    	double distance = RobotMap.driveTrainUltra.getAverageVoltage();//*108.515;
    	double forward =0;
    	if (distance>gd){
    		forward =-.5;
    		counter = 0;
    	}
    	
    	if(VisionListener.newResult){
    		VisionListener.VisionResult result = VisionListener.getResult();
    		strafecounter = 0;
    		if(result.found){
	    		SmartDashboard.putNumber("Visionx", result.x);
	    		double strafe = 1*(result.x-optimalX) + SmartDashboard.getNumber("Camera Offset", 0);
	    		double dist = RobotMap.driveTrainUltra.getAverageVoltage() * 108.515;
	    		//SmartDashboard.putNumber("Distance (in. avg)", dist);
	    		if(Math.abs(strafe)<.03){
	    			strafe = 0;
	    			counter++;
	    		} else{
	    			counter = 0;
	    			if(Math.abs(strafe)<.3){
	    				strafe = .18 * Math.signum(strafe);
	    			}
	    			else if(Math.abs(strafe)>=.3){
	    				strafe = .25 * Math.signum(strafe);
	    			}
	    		}
	    		SmartDashboard.putNumber("LineUpVal", strafe);
	    		
	    		
	    		strafeout = -strafe;
    		} else{
    			strafeout = 0;
    		}
    	} 
    	strafecounter++;
    	if(strafecounter > 10) strafeout = 0;
    	SmartDashboard.putNumber("StrafeVal", strafeout);
    	if(!pid.onTarget()) counter = 0;
    	SmartDashboard.putNumber("PID Output", pid.get());
    	SmartDashboard.putNumber("PID Error", pid.getError());
    	Robot.driveTrain.mecanum(forward, strafeout, pid.get());
    }
    //keeps going until hits sonar hot spot which is as close as can be without hitting point of no return
    //First move forward

    private void finalAutoForward(){
    	
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return counter>10;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
