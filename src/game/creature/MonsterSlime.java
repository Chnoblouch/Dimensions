package game.creature;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.TextLoader;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.monsterdrops.ItemSlime;
import game.obj.Drop;
import game.obj.GameObject;
import game.particle.ParticleDestroying;
import game.utils.Angles;
import game.utils.Block;
import game.utils.DoublePoint;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class MonsterSlime 
extends Monster {
	
	private TimeCounter standTimer;
	private boolean jump = false;
	private double jumpY, jumpMotion = 0;
	private double speed = 12;
	
	private Player target;
	
	private double angle = 0;
	
	public MonsterSlime()
	{
		setMaxHealth(40);
		setHealth(40);
		setInvulnerableTime(500);
		
		standTimer = new TimeCounter(1000, () -> 
		{
			jump();
			standTimer.reset();
		});
	}
	
	public void fromGiantSlime()
	{
		standTimer.setTime(100);
	}
	
	private void jump()
	{
		jump = true;
		jumpMotion = -24;
		angle = Angles.getAngle(getHitbox().center(), target.getHitbox().center());
		standTimer.reset();
	}
		
	public void giantJump(double angle)
	{
		this.angle = angle;
		jump = true;
		jumpMotion = -64;
	}
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + 96 - 1);
	}
	
	@Override
	public boolean inCollisionSpace()
	{
		return true;
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX(), getY() + 48, 96, 48);
	}
	
	@Override
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(getX(), getY() + 48, 96, 48);
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), 192, 192))
		{
			double ss = jump ? 56 : 64;
			
			BufferedImage sprite = SpriteSheet.slime.getSprite(jump ? 24 : 0, 0, 24, 24);
						
//			screen.render(SpriteSheet.shadows.getSprite(16, 0, 32, 16), getX() + 48 - ss, getY() + 128 - ss, ss * 2, ss, 0, jump ? 0.5 : 0.75);
			screen.render(SpriteFilter.getShadowStanding(sprite), getX() - jumpY, getY() + jumpY, 192, 96, 0, 0.25);
			screen.render(sprite, getX(), getY() + jumpY, 96, 96, 0, invulnerableInvisible ? 0.5 : 1);
			
			if(isMouseOn())
			{
				String name = TextLoader.getText("monster_slime");
				screen.renderFont(name, getX() + 48 - (Font.getTextWidth(name, 32) / 2), getY() + jumpY, 32, Font.COLOR_WHITE, true);
			}
		}
	}
	
	@Override
	public void interactWith(Player player, boolean mouseOn)
	{
		damage(player.getAttackDamage(), player);
		knockback(player.getAngle(this), 24);
	}
	
	@Override
	public void getDamage(GameObject attacker)
	{
		for(int i = 0; i < 4; i++)
		{
			ParticleDestroying particle = new ParticleDestroying();
			particle.setSprite(0, 0);
			particle.setPosition(getHitbox().center().x, getHitbox().center().y);
			level.addObject(particle);
		}
	}
	
	@Override
	public void die()
	{
		level.removeObject(this);
		level.monsterCounter --;
		
		for(int i = 0; i < 16; i++)
		{
			ParticleDestroying particle = new ParticleDestroying();
			particle.setSprite(0, 0);
			particle.setPosition(getHitbox().center().x, getHitbox().center().y);
			level.addObject(particle);
		}
		
		for(int i = 0; i < 3 + new Random().nextInt(3); i++)
		{
			Drop drop = new Drop(new ItemSlime(), 1);
			drop.setPosition(getX() + (new Random().nextInt(Block.SIZE)), getY() + (new Random().nextInt(Block.SIZE)));
			level.addObject(drop);
		}
	}
	
	@Override
	public void update(double tpf)
	{
		super.update(tpf);
		
		target = level.player;
	
		if(target != null)
		{
			if(!target.rideOnDragon && target.isInRange(this) && collides(target)) 
			{
				target.damage(15, this);
				target.knockback(getAngle(target), 24);
			}
			
			if(jump)
			{
				DoublePoint moveDir = Angles.getMoveDirection(angle);
				move(moveDir.x * speed * tpf, moveDir.y * speed * tpf);
				
				jumpY += jumpMotion * tpf;
				
				jumpMotion += 3 * tpf;
				if(jumpY >= 0)
				{
					jumpY = 0;
					jumpMotion = 0;
					jump = false;
				}
				
			} else standTimer.count(tpf);
		}
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return o instanceof MonsterSlime;
	}

}