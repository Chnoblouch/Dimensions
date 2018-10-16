package game.environment;

import java.util.Random;

import game.creature.Player;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.ItemIronOre;
import game.item.ItemStone;
import game.obj.Drop;
import game.obj.GameObject;
import game.particle.ParticleDestroying;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class Stone
extends GameObject {
		
	private double health = 30;
	
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
		if(screen.isInside(getX(), getY(), Block.SIZE * 3, Block.SIZE * 2)) 
		{			
			screen.render(SpriteFilter.getShadowStanding(SpriteSheet.overworld.getSprite(0, 72, 32, 24)), 
					      getX(), getY(), Block.SIZE * 3, Block.SIZE, 0, 0.25);
			screen.render(SpriteSheet.overworld.getSprite(0, 72, 32, 24), getX(), getY(), Block.SIZE * 1.5, Block.SIZE, 0, 1);
		}
	}
	
	@Override
	public boolean inCollisionSpace()
	{
		return true;
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX(), getY() + (Block.SIZE / 2), Block.SIZE * 1.5, Block.SIZE / 2);
	}
	
	@Override
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(getX(), getY(), Block.SIZE * 1.5, Block.SIZE);
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return true;
	}
	
	@Override
	public void interactWith(Player player)
	{
		health -= player.getStoneDamage();
		
		if(health <= 0) destroy();
		else 
		{
			for(int i = 0; i < 6; i++)
			{
				ParticleDestroying particle = new ParticleDestroying();
				particle.setSprite(8, 0);
				particle.setPosition(getHitbox().center().x, getHitbox().center().y);
				level.addObject(particle);
			}
			
			Sounds.stone.play(0, getX() + (Block.SIZE * 0.75), getY() + (Block.SIZE / 2), level.game.getCamX(), level.game.getCamY());
		}
	}
	
	public void destroy()
	{
		for(int i = 0; i < 4 + new Random().nextInt(5); i++)
		{
			Drop drop = new Drop(new ItemStone(), 1);
			drop.setPosition(getX() + (new Random().nextInt((int) (Block.SIZE * 1.5))), 
							 getY() + (new Random().nextInt((int) (Block.SIZE * 1.5))));
			level.addObject(drop);
		}
		
		for(int i = 0; i < 1 + new Random().nextInt(3); i++)
		{
			Drop drop = new Drop(new ItemIronOre(), 1);
			drop.setPosition(getX() + (new Random().nextInt((int) (Block.SIZE * 1.5))), 
							 getY() + (new Random().nextInt((int) (Block.SIZE * 1.5))));
			level.addObject(drop);
		}
		
		for(int i = 0; i < 24; i++)
		{
			ParticleDestroying particle = new ParticleDestroying();
			particle.setSprite(8, 0);
			particle.setPosition(getHitbox().center().x, getHitbox().center().y);
			level.addObject(particle);
		}
		
		level.removeObject(this);
		level.collisionSpace.remove(this);
		
		Sounds.stoneDestroyed.play(0, getX() + (Block.SIZE * 0.75), getY() + (Block.SIZE / 2), level.game.getCamX(), level.game.getCamY());
	}
	
	@Override
	public boolean doSave()
	{
		return true;
	}

}
