package game.gfx;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class SpriteLoader 
{
	public static HashMap<String, BufferedImage> cache = new HashMap<String, BufferedImage>();
		
	public static BufferedImage loadSprite(String name)
	{
		BufferedImage img = cache.get(name);
		if (img != null) return img; //if img loaded
		
		try {
			InputStream in = SpriteLoader.class.getResourceAsStream(name);
			if(in == null) System.out.println("Couldn't load image file: " + name);
			
			img = ImageIO.read(in);
//			img = new BufferedImage(readImg.getWidth(), readImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
//			Graphics2D g2d = img.createGraphics();
//			g2d.drawImage(readImg, 0, 0, readImg.getWidth(), readImg.getHeight(), null);
			
//			for(int x = 0; x < img.getWidth(); x++)
//			{
//				for(int y = 0; y < img.getHeight(); y++)
//				{
//					int rgb = img.getRGB(x, y);
//					
//					int r = (rgb >> 16) & 0xFF;
//					int g = (rgb >> 8) & 0xFF;
//					int b = (rgb >> 0) & 0xFF;
//										
//					if(r == 255 && g == 0 && b == 0) img.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
//				}
//			}
			
			cache.put(name, img);
			return img;
		} catch (IOException e) {
			System.out.println("Could not load image: "+name);
			return null;
		}
	}
	
	public static BufferedImage loadSpritePart(String name, Rectangle part)
	{
		return loadSprite(name).getSubimage(part.x, part.y, part.width, part.height);
	}
	
	public void clearCache()
	{
		cache.clear();
	}
	
	public static BufferedImage cloneImage(BufferedImage source)
	{
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    
	    return b;
	}
}
