package game.creature;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.TextLoader;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.ItemSlime;
import game.obj.Drop;
import game.obj.GameObject;
import game.particle.ParticleDestroying;
import game.utils.Angles;
import game.utils.Block;
import game.utils.DoublePoint;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class MonsterRogo 
extends Monster {
	
	private double speed = 8;
	private Player target;	
	
	private boolean rolling = false;
	private TimeCounter startRollingTimer;
	private int rollStage = 0;
	private TimeCounter rollTimer;
	private int rollDir = 0;
	
	private double angle = 0;
	
	public MonsterRogo()
	{
		setHealth(10);
		setInvulnerableTime(500);
		
		startRollingTimer = new TimeCounter(100, () -> 
		{
			rolling = true;
			startRollingTimer.reset();
		});
		
		rollTimer = new TimeCounter(100, () -> {
			rollStage ++;
			
			if(rollStage >= 4)
			{
				rollStage = 0;
				rolling = false;
				Sounds.rogoRoll.play(0, getX() + (Block.SIZE / 2), getY() + (Block.SIZE / 2), level.game.getCamX(), level.game.getCamY());
			}
			
			rollTimer.reset();
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
		if(screen.isInside(getX(), getY(), 256, 128))
		{			
			BufferedImage sprite = SpriteSheet.rogo.getSprite(rolling ? 32 + (rollStage * 32) : 0, rolling ? rollDir * 32 : 0, 32, 32);
						
			screen.render(SpriteFilter.getShadowStanding(sprite), getX(), getY(), 256, 128, 0, 0.25);
			screen.render(sprite, getX(), getY(), 128, 128, 0, invulnerableInvisible ? 0.5 : 1);
			
			if(isMouseOn())
			{
				String name = TextLoader.getText("monster_rogo");
				screen.renderFont(name, getX() + 64 - (Font.getTextWidth(name, 32) / 2), getY() - 16, 32, Font.COLOR_WHITE, true);
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
		Sounds.rogoHurt.play(0, getX() + (Block.SIZE / 2), getY() + (Block.SIZE / 2), level.game.getCamX(), level.game.getCamY());
		
		for(int i = 0; i < 4; i++)
		{
			ParticleDestroying particle = new ParticleDestroying();
			particle.setSprite(24, 0);
			particle.setPosition(getHitbox().center().x, getHitbox().center().y);
			level.addObject(particle);
		}
	}
	
	@Override
	public void die()
	{
		level.removeObject(this);
		level.monsterCounter --;
		
		Sounds.rogoDie.play(0, getX() + (Block.SIZE / 2), getY() + (Block.SIZE / 2), level.game.getCamX(), level.game.getCamY());
		
		for(int i = 0; i < 16; i++)
		{
			ParticleDestroying particle = new ParticleDestroying();
			particle.setSprite(24, 0);
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
	
	@Override
	public void update(double tpf)
	{
		super.update(tpf);
		
		target = level.player;
	
		if(target != null)
		{
			if(!target.rideOnDragon && target.isInRange(this) && collides(target)) 
			{
				target.damage(3, this);
				target.knockback(getAngle(target), 24);
			}
						
			if(rolling)
			{
				angle = getAngle(target);
				
				if(angle <= 180) rollDir = 0;
				else if(angle > 180) rollDir = 1;
				
//				if((angle > 315 && angle <= 360) || (angle > 0 && angle <= 45)) rollDir = 2;
//				else if(angle > 45 && angle <= 135) rollDir = 1;
//				else if(angle > 135 && angle <= 225) rollDir = 0;
//				else if(angle > 225 && angle <= 315) rollDir = 3;
				
				DoublePoint moveDir = Angles.getMoveDirection(angle);
				move(moveDir.x * speed * tpf, moveDir.y * speed * tpf);
				
				rollTimer.count(tpf);
			} else startRollingTimer.count(tpf);
		}
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return o instanceof MonsterRogo;
	}

}