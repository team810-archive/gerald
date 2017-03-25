package org.usfirst.frc810.SteamWorksRobot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class PrintCommand extends Command {
	
	private String message;
	
	public PrintCommand(String m){
		this.message = m;
	}

	@Override public void initialize(){
		System.out.println(message);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
