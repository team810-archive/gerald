package org.usfirst.frc810.SteamWorksRobot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StrafeAuto extends CommandGroup {

	public StrafeAuto(){
	//	addSequential(new StrafeForTime(.5));
		//addSequential(new AutoMove(-1,0.1,0,.5));
		addSequential(new AutoGearSequence());
		}
}
