package game.creature;

import java.util.Random;

import game.environment.GrassBlock;
import game.environment.GrassGround;
import game.environment.Stone;
import game.environment.Tree;
import game.obj.GameObject;
import game.utils.Angles;
import game.utils.DoublePoint;
import game.utils.TimeCounter;

public class Creature
extends GameObject {
	
	private double health = 0;
	private double maxHealth = 0;
	private int invulnerableTime;
	private boolean invulnerable = false;
	private TimeCounter invulnerableTimer;
	
	private TimeCounter invulnerableFlashTimer;
	public boolean invulnerableInvisible = false;
	
	private double knockbackAngle = 0;
	private double knockbackSpeed = 0;
	
	public boolean rideOnDragon = false;
	public Dragon dragon;
	
	public Creature()
	{
		setHealth(20);
		
		invulnerableTimer = new TimeCounter(1000, () -> {
			invulnerable = false;
			invulnerableInvisible = false;
			invulnerableFlashTimer.reset();
			invulnerableTimer.reset();
		});
		
		invulnerableFlashTimer = new TimeCounter(125, () -> {
			invulnerableInvisible = !invulnerableInvisible;
			invulnerableFlashTimer.reset();
		});
	}
	
	public void setHealth(double h)
	{
		health = h;
	}
	
	public void setMaxHealth(double mx)
	{
		maxHealth = mx;
	}
	
	public double getMaxHealth()
	{
		return maxHealth;
	}
	
	public void heal(double h)
	{
		health += h;
		if(health > maxHealth) health = maxHealth;
	}
	
	public void damage(double h, GameObject attacker)
	{
		if(invulnerable) return;
		
		double finalDamage = h / (1 + getProtection() / 20);
		
		health -= finalDamage;
		invulnerable = true;
		
		if(health <= 0) die();
		else getDamage(attacker);
	}
	
	public double getHealth()
	{
		return health;
	}
	
	public void setInvulnerableTime(int time)
	{
		invulnerableTime = time;
		invulnerableTimer.setTime(time);
	}
	
	public int getInvulnerableTime()
	{
		return invulnerableTime;
	}
		
	public void knockback(double angle, int speed)
	{
		if(isKnockback()) return;
		
		knockbackAngle = angle - 12 + new Random().nextInt(25);
		knockbackSpeed = speed;
	}
	
	public boolean isKnockback()
	{
		return knockbackSpeed > 0;
	}
	
	public double getProtection() 
	{
		return 0;
	}
	
	public void tameDragon(Dragon dragon)
	{
		this.dragon = dragon;
		dragon.tame(this);
	}
	
	public void rideOnDragon(Dragon dragon)
	{
		if(dragon.mounted) return;
		
		rideOnDragon = true;
		this.dragon = dragon;
		dragon.mounted = true; 
	}
	
	public boolean canLeaveDragon()
	{
		if(!rideOnDragon) return false;
		
		boolean canLeave = false;
		
		for(int i = 0; i < level.objects.size(); i++)
		{
			GameObject o = level.objects.get(i);
			if(isInRange(o) && collides(o)) 
			{
				if(o instanceof GrassGround)
				{
					canLeave = true;
					break;
				}
				
				else if(o instanceof GrassBlock || o instanceof Tree || o instanceof Stone) return false;
			}
		}
		
		return canLeave;
	}
	
	public void leaveDragon()
	{
		if(canLeaveDragon())
		{
			rideOnDragon = false;
			dragon.mounted = false;
		}
	}
	
	@Override
	public void update(double tpf)
	{
		if(invulnerable) 
		{
			invulnerableTimer.count(tpf);
			invulnerableFlashTimer.count(tpf);
		}
		
		if(knockbackSpeed != 0)
		{
			moveAlongAngle(knockbackAngle, knockbackSpeed * 2 * tpf);
			
			if(knockbackSpeed >= 1) knockbackSpeed -= 2 * tpf;
			else knockbackSpeed = 0;
		} 
	}
	
	public void die() {}
	public void getDamage(GameObject attacker) {}

}
