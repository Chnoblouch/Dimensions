package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import game.utils.MapLoader;

public class TextLoader {
	
	private static HashMap<String, String> texts = new HashMap<>();
//	private static HashMap<String, String> speech = new HashMap<>();
//	private static HashMap<String, String> items = new HashMap<>();
//	private static HashMap<String, String> creatures = new HashMap<>();
	
	static
	{		
		loadText("texts", texts);
//		loadText("speech", speech);
//		loadText("items", items);
//		loadText("creatures", creatures);
	}
	
	private static void loadText(String fileName, HashMap<String, String> map)
	{
		InputStream in = MapLoader.class.getResourceAsStream("/txt/"+fileName+".txt");
		
		try 
		{						
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;			
			
		    while ((line = br.readLine()) != null)
		    {			
		    	if(!line.contains("=")) continue;
		    			    	
		    	String name = line.split("=")[0];
		    	String text = line.split("=")[1];
		    	map.put(name, text);
		    }
		    
		    br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getText(String text)
	{
		return texts.get(text);
	}
	
//	public static String getSpeech(String name)
//	{
//		return speech.get(name);
//	}
//	
//	public static String getItemName(String name)
//	{
//		return items.get(name);
//	}
//	
//	public static String getCreatureName(String name)
//	{
//		return creatures.get(name);
//	}

}
