package de.skysoldier.pacman3d.util;

public class Logger {
	
	private static boolean enabled = true;
	
	public enum Importance {
		DEFAULT, IMPORTANT
	}
	
	public static void setEnabled(boolean enabled){
		Logger.enabled = enabled;
	}
	
	public static void log(Object log){
		log(log, Importance.DEFAULT);
	}
	
	public static void log(Object log, Importance importance){
		if(enabled || importance == Importance.IMPORTANT) System.out.println(log);
	}
}
