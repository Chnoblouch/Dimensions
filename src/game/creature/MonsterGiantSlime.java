package game.creature;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.TextLoader;
import game.environment.BlueStone;
import game.environment.DarkSpike;
import game.environment.RedStone;
import game.environment.Stone;
import game.environment.Tree;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.ItemSlime;
import game.obj.Drop;
import game.obj.GameObject;
import game.particle.BossParticle;
import game.particle.ParticleDestroying;
import game.projectiles.Slimeball;
import game.utils.Angles;
import game.utils.Block;
import game.utils.DoublePoint;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class MonsterGiantSlime 
extends Monster {
	
	private TimeCounter standTimer;
	private boolean jump = false;
	private double jumpY, jumpMotion = 0;
	private double speed = 24;
	
	private Player target;
	
	private double angle = 0;
	
	private TimeCounter shootTimer;
	
	private TimeCounter particleTimer;
	
	public MonsterGiantSlime()
	{
		setHealth(100);
		setInvulnerableTime(1500);
		
		standTimer = new TimeCounter(1000, () -> 
		{
			jump = true;
			jumpMotion = - 24;
			angle = Angles.getAngle(getHitbox().center(), target.getHitbox().center());
			standTimer.reset();
		});
		
		shootTimer = new TimeCounter(2000, () -> 
		{
			shootSlimeball();
			shootTimer.reset();
		});
		
		particleTimer = new TimeCounter(500, () -> 
		{
			for(int i = 0; i < 10; i++)
			{
				BossParticle particle = new BossParticle();
				particle.setTarget(this);
				level.addObject(particle);
			}
			
			particleTimer.reset();
		});
	}
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + 512 - 1);
	}
	
	@Override
	public boolean inCollisionSpace()
	{
		return false;
	}
	
	@Override
	public boolean updateOutside()
	{
		return true;
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX(), getY() + 256, 512, 256);
	}
	
	@Override
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(getX(), getY() + 256, 512, 256);
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), 1024, 512))
		{
			double ss = jump ? 56 : 64;
			
			BufferedImage sprite = SpriteSheet.giantSlime.getSprite(jump ? 128 : 0, 0, 128, 128);
						
//			screen.render(SpriteSheet.shadows.getSprite(16, 0, 32, 16), getX() + 48 - ss, getY() + 128 - ss, ss * 2, ss, 0, jump ? 0.5 : 0.75);
			screen.render(SpriteFilter.getShadowStanding(sprite), getX() - jumpY, getY() + jumpY, 1024, 512, 0, 0.25);
			screen.render(sprite, getX(), getY() + jumpY, 512, 512, 0, invulnerableInvisible ? 0.5 : 1);
			
			if(isMouseOn())
			{
				String name = TextLoader.getText("monster_giant_slime");
				screen.renderFont(name, getX() + 256 - (Font.getTextWidth(name, 32) / 2), getY() + jumpY, 32, Font.COLOR_WHITE, true);
			}
		}
	}
	
	@Override
	public void interactWith(Player player)
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
		
		for(int i = 0; i < 1 + new Random().nextInt(3); i++)
		{
			Drop drop = new Drop(new ItemSlime(), 1);
			drop.setPosition(getX() + (new Random().nextInt(Block.SIZE)), getY() + (new Random().nextInt(Block.SIZE)));
			level.addObject(drop);
		}
	}
	
	public void shootSlimeball()
	{
		Slimeball slimeball = new Slimeball();
		slimeball.setPosition(getX() + 128 - 64, getY() + 256 - 64);
		slimeball.setAngle(slimeball.getAngle(target));
		level.addObject(slimeball);
	}
	
	@Override
	public void update(double tpf)
	{
		super.update(tpf);
		
		target = level.player;
		
		shootTimer.count(tpf);
		particleTimer.count(tpf);
	
		if(target != null)
		{
			if(!target.rideOnDragon && target.isInRange(this) && collides(target)) 
			{
				target.damage(3, this);
				target.knockback(getAngle(target), 24);
			}
			
			for(int i = 0; i < level.objects.size(); i++)
			{
				GameObject o = level.objects.get(i);
				if(isInRange(o) && collides(o))
				{
					if(o instanceof Tree) ((Tree) o).destroy();
					else if(o instanceof Stone) ((Stone) o).destroy();
					else if(o instanceof RedStone) ((RedStone) o).destroy();
					else if(o instanceof BlueStone) ((BlueStone) o).destroy();
					else if(o instanceof DarkSpike) ((DarkSpike) o).destroy();
				}
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
		return o instanceof MonsterGiantSlime;
	}

}