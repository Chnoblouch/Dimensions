package game.utils;

public class TimeCounter {

	private double time;
	private double counter = 0;
	
	private TimeListener timeListener;
	
	public TimeCounter(double time, TimeListener listener)
	{
		this.time = time;
		this.timeListener = listener;
	}
	
	public void setTime(double time)
	{
		this.time = time;
	}
		
	public void count(double tpf)
	{
		counter += 25 * tpf;
		
		if(counter >= time) timeListener.timeIsUp();
	}
	
	public void reset()
	{
		if(counter > time) counter -= time;
		else counter = 0;
	}
	
}
