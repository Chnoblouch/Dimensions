package game.gfx;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SpriteFilter {
	
	public static BufferedImage instantRotating(BufferedImage img, double degrees)
	{
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(degrees), img.getWidth() / 2, img.getHeight() / 2);
		
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(img, null);
	}
	
	public static BufferedImage getShadowStanding(BufferedImage img)
	{
		BufferedImage shadow = new BufferedImage(img.getWidth() * 2, img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int addX = img.getWidth();
		
		for(int y = 0; y < img.getHeight(); y++)
		{		
			addX --;
			
			for(int x = 0; x < img.getWidth(); x++)
			{
				if(img.getRGB(x, y) >> 24 != 0) shadow.setRGB(x + addX, y, Color.black.getRGB());
			}
		}
		
		return shadow;
	}
	
	public static BufferedImage getShadowLaid(BufferedImage img)
	{
		BufferedImage shadow = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int y = 0; y < img.getHeight(); y++)
		{					
			for(int x = 0; x < img.getWidth(); x++)
			{
				if(img.getRGB(x, y) >> 24 != 0) shadow.setRGB(x, y, Color.black.getRGB());
			}
		}
		
		return shadow;
	}
	
	public static BufferedImage getDarker(BufferedImage img, double brightness)
	{
		BufferedImage darker = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
				
		for(int y = 0; y < img.getHeight(); y++)
		{		
			for(int x = 0; x < img.getWidth(); x++)
			{
				int rgb = img.getRGB(x, y);
				
				if(rgb >> 24 != 0)
				{
					int r = rgb >> 16 & 0xFF;
					int g = rgb >> 8 & 0xFF;
					int b = rgb & 0xFF;
					
					r *= brightness;
					g *= brightness;
					b *= brightness;
												
					int darkerRGB = ((255 & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);										
					darker.setRGB(x, y, darkerRGB);
				}
			}
		}
		
		return darker;
	}

}
