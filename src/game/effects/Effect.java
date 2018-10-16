package game.effects;

public class Effect {

	private int effectID = 0;
	public static final int ID_SPEED = 0;
	
	private String name;
	
	private double durationLeft;
	private boolean over = false;
	
	public Effect(double durationLeft)
	{
		this.durationLeft = durationLeft;
	}
	
	public void update(double tpf)
	{
		if(over) return;
		
		durationLeft -= tpf;
		if(durationLeft <= 0) over = true;
	}

	public void setEffectID(int id)
	{
		this.effectID = id;
	}
	
	public int getEffectID()
	{
		return effectID;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getDurationLeft()
	{
		return durationLeft;
	}
	
	public boolean isOver()
	{
		return over;
	}
}
