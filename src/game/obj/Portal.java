package game.obj;

import java.awt.image.BufferedImage;

import game.gfx.Screen;
import game.gfx.Sound;
import game.gfx.SoundLoader;
import game.gfx.Sounds;
import game.gfx.Sprite;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class Portal 
extends GameObject {
	
	private double rot = 0;
	
	private int destination;
	
	private boolean changeLevel = false;
	
	public void setLevel(int level)
	{
		this.destination = level;
	}
	
	public int getLevel()
	{
		return destination;
	}
	
	@Override
	public String save()
	{
		return ""+destination;
	}
	
	@Override
	public boolean doSave()
	{
		return true;
	}
	
	@Override
	public void load(String data)
	{
		destination = Integer.parseInt(data);
	}
	
	@Override
	public void update(double tpf)
	{
		rot += 3 * tpf;
		if(rot >= 360) rot -= 360;
		
		if(isInRange(level.player) && collides(level.player) && !changeLevel && !level.player.rideOnDragon)
		{
			changeLevel = true;
			level.game.changeLevelScreen();
			
			Sounds.portal.play(0, getX() + 128, getY() + 128, level.game.getCamX(), level.game.getCamY());
		}
		
		if(changeLevel && level.game.changeLevelScreen.finish)
		{
			changeLevel = false;
			level.game.changeLevel(destination);
		}
	}
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + 256);
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX(), getY(), 256, 256);
	}
	
	@Override
	public void render(Screen screen)
	{
//		screen.render(SpriteSheet.shadows.getSprite(0, 16, 32, 32), getX(), getY() + 128 - 16, 256, 256, 0, 0.75);
		if(screen.isInside(getX(), getY(), 512, 512))
		{
			BufferedImage sprite = SpriteSheet.portals.getSprite(destination * 64, 0, 64, 64);
			BufferedImage shadow = SpriteFilter.getShadowStanding(SpriteFilter.instantRotating(sprite, rot));
			
			screen.render(shadow, getX() - ((shadow.getWidth() * 2) - 256), getY(), shadow.getWidth() * 4, shadow.getHeight() * 4, 0, 0.25);
			screen.render(sprite, getX(), getY(), 256, 256, rot, 1);
		}
	}

}
