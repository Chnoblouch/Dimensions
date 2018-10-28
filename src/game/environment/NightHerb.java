package game.environment;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.creature.Player;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.resources.ItemNightHerb;
import game.obj.Drop;
import game.obj.GameObject;
import game.particle.ParticleDestroying;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class NightHerb
extends GameObject {
		
	private double health = 5;
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + Block.SIZE);
	}
	
	@Override
	public boolean doUpdate()
	{
		return false;
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), Block.SIZE * 2, Block.SIZE)) 
		{			
			BufferedImage sprite = SpriteSheet.darkworld.getSprite(16, 0, 24, 24);
			
			screen.render(SpriteFilter.getShadowStanding(sprite), getX(), getY(), Block.SIZE * 2, Block.SIZE, 0, 0.25);
			screen.render(sprite, getX(), getY(), Block.SIZE, Block.SIZE, 0, 1);
		}
	}
	
	@Override
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(getX(), getY(), Block.SIZE, Block.SIZE);
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return true;
	}
	
	@Override
	public void interactWith(Player player, boolean mouseOn)
	{
		if(!mouseOn) return;
		
		health -= player.getAttackDamage();
		
		if(health <= 0)
		{
			for(int i = 0; i < 2 + new Random().nextInt(3); i++)
			{
				Drop drop = new Drop(new ItemNightHerb(), 1);
				drop.setPosition(getX() + (new Random().nextInt((int) (Block.SIZE * 1.5))), 
								 getY() + (new Random().nextInt((int) (Block.SIZE * 1.5))));
				level.addObject(drop);
			}
			
			for(int i = 0; i < 10; i++)
			{
				ParticleDestroying particle = new ParticleDestroying();
				particle.setSprite(16, 0);
				particle.setPosition(getHitbox().center().x, getHitbox().center().y);
				level.addObject(particle);
			}
			
			level.removeObject(this);
			level.collisionSpace.remove(this);
			
			Sounds.plantDestroyed.play(0, getX() + (Block.SIZE /2 ), getY() + (Block.SIZE / 2), level.game.getCamX(), level.game.getCamY());
		} else 
		{
			for(int i = 0; i < 4; i++)
			{
				ParticleDestroying particle = new ParticleDestroying();
				particle.setSprite(16, 0);
				particle.setPosition(getHitbox().center().x, getHitbox().center().y);
				level.addObject(particle);
			}
			
			Sounds.plant.play(0, getX() + (Block.SIZE /2 ), getY() + (Block.SIZE / 2), level.game.getCamX(), level.game.getCamY());
		}
	}
	
	@Override
	public boolean doSave()
	{
		return true;
	}

}
