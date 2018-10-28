package game.projectiles;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.creature.Dragon;
import game.creature.Monster;
import game.environment.BlueStone;
import game.environment.GrassBlock;
import game.environment.IronOreStone;
import game.environment.RedStone;
import game.environment.Stone;
import game.environment.Tree;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.obj.GameObject;
import game.particle.ParticleTaykolos;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class FlyingTaykolos 
extends GameObject {
	
	private double angle = 0;
	private boolean returnToPlayer = false;
	private double rot = 0;
	private double speed = 48;
	
	private TimeCounter particleTimer;
	
	private TimeCounter soundTimer;
		
	public FlyingTaykolos()
	{
		particleTimer = new TimeCounter(50, () -> 
		{
			for(int i = 0; i < 4; i++)
			{
				ParticleTaykolos particle = new ParticleTaykolos();
				particle.setPosition(getX() + (Block.SIZE / 2), getY() + (Block.SIZE / 2));
				particle.setAngle(-angle - 45 + new Random().nextInt(91));
				level.addObject(particle);
			}
			
			particleTimer.reset();
		});
		
		soundTimer = new TimeCounter(150, () -> {
			playSound();
			soundTimer.reset();
		});
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	public void playSound()
	{
		Sounds.taykolos.play(15, getX() + 48, getY() + 48, level.game.getCamX(), level.game.getCamY());
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX(), getY() + 24, 96, 96);
	}
	
	@Override
	public boolean updateOutside()
	{
		return true;
	}
	
	@Override
	public void update(double tpf)
	{		
		rot += returnToPlayer ? -speed / 4: speed / 4;
		
		particleTimer.count(tpf);
		soundTimer.count(tpf);
		
		for(int j = 0; j < level.objects.size(); j++)
		{
			GameObject o = level.objects.get(j);
			if(isInRange(o) && collides(o))
			{
				if(o instanceof Monster)
				{		
//					returnToPlayer = true;
					((Monster) o).damage(15, level.player);
					((Monster) o).knockback(angle, 16);
					
					break;
				} 
				else if(o instanceof Dragon)
				{
//					returnToPlayer = true;
					((Dragon) o).damage(15, level.player);
				}
				else if((o instanceof GrassBlock && level.getID() != 2) || o instanceof Tree || o instanceof Stone ||
						o instanceof IronOreStone || o instanceof RedStone || o instanceof BlueStone)
				{
					returnToPlayer = true;
					break;
				}
			}
		}
		
		if(!returnToPlayer)
		{		
			moveAlongAngle(angle, speed * tpf);
			
			if(speed >= 0) speed -= 1.5 * tpf;
			else returnToPlayer = true;
		} else 
		{
			if(speed < 32) speed += 1.5 * tpf;
			moveAlongAngle(getAngle(level.player), speed * tpf);
			
			if(isInRange(level.player) && collides(level.player))
			{
				level.removeObject(this);
				level.player.thrownSomething = false;
			}
		}
	}
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + 64 + 24);
	}
	
	@Override
	public void render(Screen screen)
	{
		BufferedImage sprite = SpriteSheet.projectiles.getSprite(16, 0, 20, 20);
		BufferedImage shadow = SpriteFilter.getShadowStanding(SpriteFilter.instantRotating(sprite, rot));
		
		screen.render(shadow, getX() - ((shadow.getWidth() * 2) - 80) + 24, getY() + 24,
					  shadow.getWidth() * 4, shadow.getHeight() * 4, 0, 0.25);				
		screen.render(sprite, getX(), getY(), 80, 80, rot, 1);
	}

}
