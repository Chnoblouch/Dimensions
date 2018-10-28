package game.saving;

import java.util.Random;

import game.Game;
import game.levels.Level;
import game.obj.GameObject;
import game.utils.SafeArrayList;

public class SaveInformation {
	
	private SafeArrayList<SaveFile> files = new SafeArrayList<>();
	
	public void put(SaveFile file)
	{
		files.add(file);
	}
	
	public SafeArrayList<SaveFile> getFiles()
	{
		return files;
	}
	
	public static SaveInformation convertGame(Game game)
	{
		SaveInformation information = new SaveInformation();
		
		for(int i = 0; i < game.getLevels().length; i++)
		{
			Level level = game.getLevels()[i];
			
			StringBuilder string = new StringBuilder();
			
			string.append(level.getID()+System.lineSeparator());
			
			for(int j = 0; j < level.objects.size(); j++)
			{
				GameObject o = level.objects.get(j);
				if(o.doSave()) string.append(o.getClass().getName()+";"+o.getX()+";"+o.getY()+";"+o.save()+System.lineSeparator());
			}
			
			information.put(new SaveFile(level.getName(), string.toString()));
		}
		
		String playerData = game.getLevelID()+";"+game.player.getX()+";"+game.player.getY()+";"+game.player.getHealth();
		
		information.put(new SaveFile("player", playerData));
		information.put(game.inventory.convertToSaveFile());
		
		return information;
	}
}
