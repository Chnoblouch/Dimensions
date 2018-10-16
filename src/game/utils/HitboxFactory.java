package game.utils;

import java.util.HashMap;

public class HitboxFactory {
	
	private static HashMap<String, Hitbox> cache = new HashMap<>();
	
	public static Hitbox create(double x, double y, double w, double h)
	{
		String name = x+","+y+","+w+","+h;
		
		Hitbox hb = cache.get(name);
		if(hb != null) return hb;
		
		hb = new Hitbox(x, y, w, h);
		cache.put(name, hb);
				
		return hb;
	}
	
	public static Hitbox create(double[] xp, double[] yp)
	{
		String name = "";
		
		for(int i = 0; i < xp.length; i++) { name = name + xp[i]+";"+yp[i]+","; }
				
		Hitbox hb = cache.get(name);
		if(hb != null) return hb;
		
		hb = new Hitbox(xp, yp);
		cache.put(name, hb);
				
		return hb;
	}
}
