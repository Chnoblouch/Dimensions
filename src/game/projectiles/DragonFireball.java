package game.projectiles;

import java.util.Random;

import game.creature.Creature;
import game.creature.Dragon;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.obj.GameObject;
import game.particle.ParticleFire;
import game.utils.Angles;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class DragonFireball 
extends GameObject {
	
	private double angle = 0;
	
	private TimeCounter particleTimer;
	private TimeCounter disappearTimer;
	
	private Dragon dragon;
	
	public DragonFireball()
	{
		particleTimer = new TimeCounter(50, () ->
		{
			for(int i = 0; i < 2; i++)
			{
				ParticleFire particle = new ParticleFire();
				particle.setPosition(getX() + (Block.SIZE / 2), getY() + (Block.SIZE / 2));
				particle.setAngle(-angle - 45 + new Random().nextInt(91));
				level.addObject(particle);
			}
			
			particleTimer.reset();
		});
		
		disappearTimer = new TimeCounter(3000, () -> level.removeObject(this));
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	public void setDragon(Dragon dragon)
	{
		this.dragon = dragon;
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), Block.SIZE, Block.SIZE))
		{
			screen.render(SpriteSheet.projectiles.getSprite(36, 0, 24, 24), getX(), getY(), Block.SIZE, Block.SIZE, angle, 0.9);
		}
	}
	
	@Override
	public boolean updateOutside()
	{
		return true;
	}
	
	@Override
	public void update(double tpf)
	{	
		moveAlongAngle(angle, 24 * tpf);
		
		for(int i = 0; i < level.objects.size(); i++)
		{
			GameObject o = level.objects.get(i);
			
			if(o.isInRange(this) && o instanceof Creature && collides(o) && o != dragon) 
			{
				((Creature) o).damage(20, this);
				level.removeObject(this);
				
				for(int j = 0; j < 16; j++)
				{
					ParticleFire particle = new ParticleFire();
					particle.setPosition(getX() + (Block.SIZE / 2), getY() + (Block.SIZE / 2));
					particle.setAngle(new Random().nextInt(361));
					level.addObject(particle);
				}
				
				break;
			}
		}
		
		particleTimer.count(tpf);
		disappearTimer.count(tpf);
		
		if(!level.game.screen.isInside(getX(), getY(), Block.SIZE, Block.SIZE)) level.removeObject(this);
	}

}
