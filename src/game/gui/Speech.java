package game.gui;

public class Speech {
	
	private String text;
	private boolean over;
	
	public Speech(String text)
	{
		this.text = text;
	}
	
	public Speech()
	{
		
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setOver(boolean over)
	{
		this.over = over;
	}
	
	public boolean isOver()
	{
		return over;
	}

}
