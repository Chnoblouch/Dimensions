package game.gfx;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.Game;
import game.utils.Hitbox;

public class Screen {
	
	private Game game;
	public static final int WIDTH = Game.WIDTH;
	public static final int HEIGHT = Game.HEIGHT;
	
	public static final int DEFAULT_WIDTH = 1920;
	public static final int DEFAULT_HEIGHT = 1080;
			
	private Graphics2D g;
	public int x;
	
	public double camX, camY;
		
	public Screen(Game game)
	{
		this.game = game;
	}
	
	public boolean isInside(double x, double y, double w, double h)
	{
		return x + w >= camX && 
			   x <= camX + DEFAULT_WIDTH && 
			   y + h >= camY &&
			   y <= camY + DEFAULT_HEIGHT;
	}
	
	public boolean isInside(Hitbox hb)
	{
		return isInside(hb.x, hb.y, hb.w, hb.h);
	}
	
	public void beforeRendering(Graphics2D g2d, int x)
	{
		this.g = g2d;
		this.x = x;
		
		if(game.player != null)
		{		
			camX = game.player.getX() + 128 - (DEFAULT_WIDTH / 2);
			camY = game.player.getY() + 128 - (DEFAULT_HEIGHT / 2);
						
//			camX = ((LevelSkyWorld) game.getLevel()).dragon.getX() + 384 - (WIDTH / 2);
//			camY = ((LevelSkyWorld) game.getLevel()).dragon.getY() + 384 - (HEIGHT / 2);
		}
	}
	
	public double getScale()
	{
		return 1.0 / ((double) DEFAULT_WIDTH / WIDTH);
	}
	
	public void render(BufferedImage sprite, double x, double y, double w, double h, double rot, double alpha)
	{
		if(alpha < 0) alpha = 0;
		if(alpha > 1) alpha = 1;

		AffineTransform aft = g.getTransform();
		g.scale(getScale(), getScale());
        g.rotate(Math.toRadians(rot), this.x + (x + (w / 2) - camX), y + (h / 2) - camY);
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha);
		g.setComposite(ac);
		
		g.drawImage(sprite, (int) (x - camX + this.x), (int) (y - camY), (int) w, (int) h, null);
		
		g.setTransform(aft);
		
		AlphaComposite acr = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		g.setComposite(acr);
	}
	
	public void renderGUI(BufferedImage sprite, double x, double y, double w, double h, double rot, double alpha)
	{
		if(alpha < 0) alpha = 0;
		if(alpha > 1) alpha = 1;
		
		AffineTransform aft = g.getTransform();
        g.scale(getScale(), getScale());
		g.rotate(Math.toRadians(rot), this.x + x + (w / 2), y + (h / 2));
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha);
		g.setComposite(ac);
		
		g.drawImage(sprite, (int) x + this.x, (int) y, (int) w, (int) h, null);	
		
		g.setTransform(aft);
		
		AlphaComposite acr = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		g.setComposite(acr);
	}
	
	public void renderRect(double x, double y, double w, double h)
	{
		AffineTransform transform = new AffineTransform();
		g.scale(getScale(), getScale());

		g.drawRect((int) (x - camX + this.x), (int) (y - camY), (int) w, (int) h);
		
		g.setTransform(transform);
	}
		
	public void renderHitbox(Hitbox hb)
	{
		if(hb.isRectangle()) renderRect(hb.x, hb.y, hb.w, hb.h);
		else {
			AffineTransform transform = new AffineTransform();
			transform.scale(getScale(), getScale());
			transform.translate(-camX, -camY);
			
			Shape finalShape = transform.createTransformedShape(hb.getShape());
			g.draw(finalShape);
		}
	}
	
	public void renderFont(String text, double x, double y, double size, String col, boolean inCam)
	{
		Font.render(this, text, inCam ? x - camX + this.x : x, inCam ? y - camY : y, size, col);
	}
}
