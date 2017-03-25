// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc810.SteamWorksRobot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import vision.VisionListener;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static AnalogGyro navX;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveTrainFL;
    public static SpeedController driveTrainBL;
    public static SpeedController driveTrainFR;
    public static SpeedController driveTrainBR;
    public static RobotDrive driveTrainRobotDrive41;
    public static AnalogInput driveTrainUltra;
    public static SpeedController ballintakeRoller;
    public static DoubleSolenoid hopperHopper;
    public static DoubleSolenoid gearMechanismGearPropulsionMechanism;
    public static DigitalInput gearMechanismGearPositionSensor;
    public static Servo cameraMountPan;
    public static Servo cameraMountTilt;
    public static Compressor generalCompressor1;
    public static PowerDistributionPanel generalPDP;
    public static SpeedController climberwinch;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainFL = new Spark(0);
        LiveWindow.addActuator("DriveTrain", "FL", (Spark) driveTrainFL);
        
        driveTrainBL = new Spark(1);
        LiveWindow.addActuator("DriveTrain", "BL", (Spark) driveTrainBL);
        
        driveTrainFR = new Spark(2);
        LiveWindow.addActuator("DriveTrain", "FR", (Spark) driveTrainFR);
        
        driveTrainBR = new Spark(3);
        LiveWindow.addActuator("DriveTrain", "BR", (Spark) driveTrainBR);
        
        driveTrainRobotDrive41 = new RobotDrive(driveTrainFL, driveTrainBL,
              driveTrainFR, driveTrainBR);
        
        driveTrainRobotDrive41.setSafetyEnabled(true);
        driveTrainRobotDrive41.setExpiration(0.1);
        driveTrainRobotDrive41.setSensitivity(0.5);
        driveTrainRobotDrive41.setMaxOutput(1.0);

        driveTrainRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrainRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        driveTrainUltra = new AnalogInput(3);
        LiveWindow.addSensor("DriveTrain", "Ultra", driveTrainUltra);
        
        ballintakeRoller = new Spark(4);
        LiveWindow.addActuator("Ball intake", "Roller", (Spark) ballintakeRoller);
        
        hopperHopper = new DoubleSolenoid(0, 1, 0);
        LiveWindow.addActuator("Hopper", "Hopper", hopperHopper);
        
        gearMechanismGearPropulsionMechanism = new DoubleSolenoid(0, 3, 2);
        LiveWindow.addActuator("GearMechanism", "GearPropulsionMechanism", gearMechanismGearPropulsionMechanism);
        
        gearMechanismGearPositionSensor = new DigitalInput(1); //
        LiveWindow.addSensor("GearMechanism", "GearPositionSensor", gearMechanismGearPositionSensor);
        
        cameraMountPan = new Servo(5);
        LiveWindow.addActuator("CameraMount", "Pan", cameraMountPan);
        
        cameraMountTilt = new Servo(6);
        LiveWindow.addActuator("CameraMount", "Tilt", cameraMountTilt);
        
        generalCompressor1 = new Compressor(0);
        
        
        generalPDP = new PowerDistributionPanel(1);
        LiveWindow.addSensor("General", "PDP", generalPDP);
        
        climberwinch = new Spark(7);
        LiveWindow.addActuator("Climber", "winch", (Spark) climberwinch);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        AutoPutData.addNumber("Ultrasonic", driveTrainUltra::getAverageVoltage);
        
        AutoPutData.addNumber("Intake Motor Current", ()->generalPDP.getCurrent(0));
        AutoPutData.addNumber("Total Drive Current", RobotMap::driveCurrent);
        AutoPutData.addNumber("Winch Current", ()->generalPDP.getCurrent(2));
     //   AutoPutData.addNumber("Total Current", generalPDP::getTotalCurrent);
       
        navX = new AnalogGyro(1);
        navX.setSensitivity(.007);
        navX.initGyro();
        navX.calibrate();
        
     
        
        
       // navX = new AHRS(edu.wpi.first.wpilibj.I2C.Port.kOnboard);
    
        AutoPutData.addNumber("NavX PID Input", navX::pidGet);
       
        
    //    AutoPutData.addNumber("Displacement Y", navX::getDisplacementY);
     
        AutoPutData.addNumber("NavX", navX::getAngle);
        AutoPutData.addNumber("Ultrasonic", driveTrainUltra::getAverageVoltage);
        AutoPutData.addBoolean("GearIsLeft", RobotMap.getGearPosition()::getGearIsLeft);
  //      AutoPutData.addNumber("Navx x-velocity", navX::getVelocityX);
  //      AutoPutData.addNumber("Navx Y - velocity", navX::getVelocityY);
    //    AutoPutData.addNumber("NavX Accel", navX::getWorldLinearAccelX);
    //    AutoPutData.addNumber("NavY Accel", navX::getWorldLinearAccelY);
        AutoPutData.addNumber("ExtremeX", ()->VisionListener.extremeVal);
        AutoPutData.addNumber("Start Angle", ()->Robot.startAngle);
        AutoPutData.addNumber("GearPosition", RobotMap.getGearPosition()::getVisionPosition);
    }
    
    public enum GearPosition{
    	LEFT(.5625, true),RIGHT(.51875, false);
    	
    	final double visionPosition;
    	final boolean gearIsLeft;
    	
    	GearPosition(double visionPosition, boolean gearIsLeft){
    		this.visionPosition = visionPosition;
    		this.gearIsLeft = gearIsLeft;
    	}
    	
    	public double getVisionPosition(){
    		return this.visionPosition;
    	}
    	
    	public boolean getGearIsLeft(){
    		return this.gearIsLeft;
    	}
    }
    
    
    public static double driveCurrent(){
    	double total = 0;
    	for(int i = 12; i<=15; i++) total += generalPDP.getCurrent(i);
    		return total;
    }

    public static double getGyroAngle(){
    	return navX.pidGet();
    }
    
    public static GearPosition getGearPosition(){
    	return (gearMechanismGearPositionSensor.get())?GearPosition.RIGHT:GearPosition.LEFT;
    }
}
