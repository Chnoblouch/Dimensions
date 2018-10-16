package game.projectiles;

import java.util.Random;

import game.creature.Creature;
import game.creature.Dragon;
import game.creature.MonsterGiantSlime;
import game.creature.MonsterSlime;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.obj.GameObject;
import game.particle.ParticleFire;
import game.utils.Angles;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class Slimeball 
extends GameObject {
	
	private double angle = 0;
	
	private TimeCounter disappearTimer;
			
	public Slimeball()
	{
		disappearTimer = new TimeCounter(3000, () -> level.removeObject(this));
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), 128, 128))
		{
			screen.render(SpriteSheet.projectiles.getSprite(60, 0, 32, 32), getX(), getY(), 128, 128, angle, 0.9);
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
			
			if(o.isInRange(this) && o instanceof Creature && collides(o) && !(o instanceof MonsterGiantSlime) && !(o instanceof MonsterSlime)) 
			{
				((Creature) o).damage(5, this);
				level.removeObject(this);
				
				break;
			}
		}
		
		disappearTimer.count(tpf);
		
		if(!level.game.screen.isInside(getX(), getY(), 128, 128)) level.removeObject(this);
	}

}
