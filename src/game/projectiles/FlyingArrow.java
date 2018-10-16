package game.projectiles;

import java.awt.image.BufferedImage;

import game.creature.Dragon;
import game.creature.Monster;
import game.environment.GrassBlock;
import game.environment.GrassGround;
import game.environment.Stone;
import game.environment.Tree;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;
import game.item.ItemArrow;
import game.obj.GameObject;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class FlyingArrow 
extends GameObject {
	
	private double angle = 0;
	private boolean flying = true;
	private TimeCounter disappearTimer;
	
	public FlyingArrow()
	{
		disappearTimer = new TimeCounter(10000, () -> level.removeObject(this));
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX(), getY() + 24, 64, 64);
	}
	
	@Override
	public boolean updateOutside()
	{
		return true;
	}
	
	@Override
	public void update(double tpf)
	{
		if(!level.game.screen.isInside(getHitbox())) level.removeObject(this);
		
		if(flying)
		{		
			for(int i = 0; i < 24; i++)
			{
				moveAlongAngle(angle, 2 * tpf);
								
				for(int j = 0; j < level.objects.size(); j++)
				{
					GameObject o = level.objects.get(j);
					if(isInRange(o) && collides(o))
					{
						if(o instanceof Monster)
						{		
							level.removeObject(this);
							((Monster) o).damage(4, level.player);
							((Monster) o).knockback(angle, 16);
							
							break;
						} 
						else if(o instanceof Dragon)
						{
							level.removeObject(this);
							((Dragon) o).damage(3, level.player);
						}
						else if((o instanceof GrassBlock && level.getID() != 2) || o instanceof Tree || o instanceof Stone)
						{
							flying = false;
							break;
						}
					}
				}
				
				if(!flying) break;
			}
		} else 
		{
			if(level.player.inventory.canHold(Item.ID_ARROW, 1) && isInRange(level.player) && collides(level.player))
			{
				level.removeObject(this);
				level.player.inventory.fillNextFreeSlot(new ItemArrow(), 1);
			}
			
			disappearTimer.count(tpf);
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
		BufferedImage sprite = SpriteSheet.projectiles.getSprite(0, 0, 16, 16);
		BufferedImage shadow = SpriteFilter.getShadowStanding(SpriteFilter.instantRotating(sprite, angle));
		
		screen.render(shadow, getX() - ((shadow.getWidth() * 2) - 64) + 24, getY() + 24,
					  shadow.getWidth() * 4, shadow.getHeight() * 4, 0, 0.25);		
		screen.render(sprite, getX(), getY(), 64, 64, angle, 1);
	}

}
