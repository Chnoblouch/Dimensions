package game.saving;

public class SaveFile {
	
	private String name;
	private String data;
	
	public SaveFile(String name, String data)
	{
		this.name = name;
		this.data = data;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getData()
	{
		return data;
	}
}
