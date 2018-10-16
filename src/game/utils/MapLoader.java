package game.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import game.Map;
import game.levels.Level;
import game.obj.GameObject;

public class MapLoader {
	
//	public static SpriteSheet overworld = new SpriteSheet(SpriteLoader.loadSprite("/img/map.png")); 
	
	public static Map loadMap(String name)
	{		
		Map map = new Map();
		
		InputStream in = MapLoader.class.getResourceAsStream(name);
				
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			
			while((line = reader.readLine()) != null)
			{
				String objName = line.split(",")[0];
				double x = Double.parseDouble(line.split(",")[1]);
				double y = Double.parseDouble(line.split(",")[2]);
				int data = Integer.parseInt(line.split(",")[3]);
				double brightness = Double.parseDouble(line.split(",")[4]);
				
				GameObject obj = (GameObject) Class.forName(objName).newInstance();
				obj.setPosition(x, y);
				obj.readData(data);
				obj.brightness(brightness);
				map.addObject(obj);
			}
						
			reader.close();
			
		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return map;
	}
}
