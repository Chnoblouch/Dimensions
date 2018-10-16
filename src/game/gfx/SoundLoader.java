package game.gfx;

import java.util.HashMap;

public class SoundLoader 
{
	public static HashMap<String, Sound> cache = new HashMap<String, Sound>();
		
	public static Sound loadSound(String name)
	{
		Sound snd = cache.get(name);
		if (snd != null) return snd; //if snd loaded
		
		snd = new Sound(name);
		cache.put(name, snd);
		return snd;
	}

	public void clearCache()
	{
		cache.clear();
	}
}
