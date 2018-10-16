package game.utils;

public class Time {

	public static double secondsInTicks(double sec)
	{
		return sec * 40;
	}
	
	public static double ticksInSeconds(double ticks)
	{
		return ticks / 40;
	}
	
}
