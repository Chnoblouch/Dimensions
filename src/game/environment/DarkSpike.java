package game.environment;

import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.obj.GameObject;
import game.particle.ParticleDestroying;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class DarkSpike
extends GameObject {
		
	private int type = 0;
	
	@Override
	public void readData(int data)
	{
		type = data;
	}
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + 256);
	}
	
	@Override
	public boolean doUpdate()
	{
		return false;
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), 64, 256)) 
			screen.render(SpriteSheet.darkworld.getSprite(0, 0, 16, 64), getX(), getY(), 64, 256, 0, 1);
	}
	
	public void destroy()
	{
		for(int i = 0; i < 24; i++)
		{
			ParticleDestroying particle = new ParticleDestroying();
			particle.setSprite(8, 0);
			particle.setPosition(getHitbox().center().x, getHitbox().center().y);
			level.addObject(particle);
		}
		
		level.removeObject(this);
		level.collisionSpace.remove(this);
	}
	
	@Override
	public boolean inCollisionSpace()
	{
		return true;
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX(), getY() + 256 - 64, 64, 64);
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return true;
	}

	@Override
	public boolean doSave()
	{
		return true;
	}
}
