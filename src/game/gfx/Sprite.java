package game.gfx;

import java.awt.image.BufferedImage;

public class Sprite {
	
	private BufferedImage img;
	
	public Sprite(BufferedImage img)
	{
		this.img = img;
	}
	
	public Sprite getSubSprite(int x, int y, int w, int h)
	{
		return new Sprite(img.getSubimage(x, y, w, h));
	}
	
	public BufferedImage toImage()
	{
		return img;
	}

}
