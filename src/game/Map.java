package game;

import game.levels.Level;
import game.obj.GameObject;
import game.utils.SafeArrayList;

public class Map {
	
	public SafeArrayList<GameObject> objects = new SafeArrayList<>();
	
	public void addObject(GameObject obj)
	{
		objects.add(obj);
	}
	
	public void removeObject(GameObject obj)
	{
		objects.remove(obj);
	}

	public void onLevel(Level level)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			level.addObject(objects.get(i));
		}
	}
}
