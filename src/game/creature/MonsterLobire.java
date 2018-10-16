package game.creature;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.TextLoader;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.ItemFeather;
import game.item.ItemMeat;
import game.obj.Drop;
import game.obj.GameObject;
import game.particle.ParticleFeather;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class MonsterLobire 
extends Monster {
	
	private int lookDir = 0;
	private double speed = 4;
	
	private Player target;
	
	private double angle = 0;
	
	private TimeCounter walkTimer;
	private int walkStage;
	private int[] walkSprites = {32, 64, 32, 0, 96, 128, 96, 0};
	
	public MonsterLobire()
	{
		setHealth(10);
		setInvulnerableTime(500);
		
		walkTimer = new TimeCounter(75, () -> 
		{
			walkStage ++;
			if(walkStage >= walkSprites.length) walkStage = 0;
			
			walkTimer.reset();
		});
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
		return HitboxFactory.create(getX(), getY() + 64, 128, 64);
	}
	
	@Override
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(getX(), getY(), 128, 128);
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), 256, 256))
		{
			int sx = 0, sy = 0;
			
			sy = lookDir * 32;
			sx = walkSprites[walkStage];
			
			BufferedImage sprite = SpriteSheet.lobire.getSprite(sx, sy, 32, 32);
			
//			screen.render(SpriteSheet.shadows.getSprite(0, 0, 16, 16), getX() + 32, getY() + 96, 64, 64, 0, 0.75);
			screen.render(SpriteFilter.getShadowStanding(sprite), getX(), getY(), 256, 128, 0, 0.25);
			screen.render(sprite, getX(), getY(), 128, 128, 0, invulnerableInvisible ? 0.5 : 1);
			
			if(isMouseOn())
			{
				String name = TextLoader.getText("monster_lobire");
				screen.renderFont(name, getX() + 64 - (Font.getTextWidth(name, 32) / 2), getY() - 48, 32, Font.COLOR_WHITE, true);
			}
		}
	}
	
	@Override
	public void interactWith(Player player)
	{
		damage(player.getAttackDamage(), player);
		knockback(player.getAngle(this), 32);
	}
	
	@Override
	public void getDamage(GameObject attacker)
	{
		Sounds.lobireHurt.play(0, getX() + 64, getY() + 64, level.game.getCamX(), level.game.getCamY());
		
		for(int i = 0; i < 4; i++)
		{
			ParticleFeather particle = new ParticleFeather();
			particle.setPosition(getHitbox().center().x, getHitbox().center().y);
			level.addObject(particle);
		}
	}
	
	@Override
	public void die()
	{
		level.removeObject(this);
		level.monsterCounter --;
		
		Sounds.lobireDie.play(0, getX() + 64, getY() + 64, level.game.getCamX(), level.game.getCamY());
		
		for(int i = 0; i < 16; i++)
		{
			ParticleFeather particle = new ParticleFeather();
			particle.setPosition(getHitbox().center().x, getHitbox().center().y);
			level.addObject(particle);
		}
		
		for(int i = 0; i < 1 + new Random().nextInt(3); i++)
		{
			Drop drop = new Drop(new ItemFeather(), 1);
			drop.setPosition(getX() + (new Random().nextInt(128)), getY() + (new Random().nextInt(128)));
			level.addObject(drop);
		}
		
		for(int i = 0; i < 1 + new Random().nextInt(2); i++)
		{
			Drop drop = new Drop(new ItemMeat(), 1);
			drop.setPosition(getX() + (new Random().nextInt(128)), getY() + (new Random().nextInt(128)));
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
			walkTimer.count(tpf);
			
			if(!target.rideOnDragon && target.isInRange(this) && collides(target)) 
			{
				target.damage(3, this);
				target.knockback(getAngle(target), 32);
			}
			
			angle = getAngle(target);
			moveAlongAngle(angle, speed * tpf);
			
			if((angle > 315 && angle <= 360) || (angle > 0 && angle <= 45)) lookDir = 2;
			else if(angle > 45 && angle <= 135) lookDir = 1;
			else if(angle > 135 && angle <= 225) lookDir = 0;
			else if(angle > 225 && angle <= 315) lookDir = 3;
		}
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return o instanceof MonsterLobire;
	}

}