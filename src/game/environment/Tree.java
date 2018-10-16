package game.environment;

import java.util.Random;

import game.creature.Player;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.ItemApple;
import game.item.ItemWood;
import game.obj.Drop;
import game.obj.GameObject;
import game.particle.ParticleDestroying;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class Tree
extends GameObject {
	
	private double health = 20;
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + (Block.SIZE * 3));
	}
	
	@Override
	public boolean doUpdate()
	{
		return false;
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), Block.SIZE * 6, Block.SIZE * 6))
		{			
			screen.render(SpriteFilter.getShadowStanding(SpriteSheet.overworld.getSprite(0, 0, 72, 72)), 
						  getX(), getY(), Block.SIZE * 6, Block.SIZE * 3, 0, 0.25);
			screen.render(SpriteSheet.overworld.getSprite(0, 0, 72, 72), getX(), getY(), Block.SIZE * 3, Block.SIZE * 3, 0, 1);
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
		return HitboxFactory.create(getX() + (Block.SIZE / 2), getY() + Block.SIZE * 2, Block.SIZE * 2, Block.SIZE);
	}
	
	@Override
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(getX(), getY(), Block.SIZE * 3, Block.SIZE * 3);
	}
	
	@Override
	public void interactWith(Player player)
	{
		health -= player.getWoodDamage();
		
		if(health <= 0) destroy();
		else 
		{
			for(int i = 0; i < 8; i++)
			{
				ParticleDestroying particle = new ParticleDestroying();
				particle.setSprite(4, 0);
				particle.setPosition(getHitbox().center().x, getHitbox().center().y);
				level.addObject(particle);
			}
			
			Sounds.wood.play(0, getX() + (Block.SIZE * 1.5), getY() + (Block.SIZE * 1.5), level.game.getCamX(), level.game.getCamY());
		}
	}
	
	public void destroy()
	{
		for(int i = 0; i < 6 + new Random().nextInt(6); i++)
		{
			Drop drop = new Drop(new ItemWood(), 1);
			drop.setPosition(getX() + (new Random().nextInt(Block.SIZE * 3)), getY() + (new Random().nextInt(Block.SIZE * 3)));
			level.addObject(drop);
		}
		
		for(int i = 0; i < 0 + new Random().nextInt(2); i++)
		{
			Drop drop = new Drop(new ItemApple(), 1);
			drop.setPosition(getX() + (new Random().nextInt(Block.SIZE * 3)), getY() + (new Random().nextInt(Block.SIZE * 3)));
			level.addObject(drop);
		}
		
		for(int i = 0; i < 32; i++)
		{
			ParticleDestroying particle = new ParticleDestroying();
			particle.setSprite(4, 0);
			particle.setPosition(getHitbox().center().x, getHitbox().center().y);
			level.addObject(particle);
		}
		
		level.removeObject(this);
		level.collisionSpace.remove(this);
		
		Sounds.woodDestroyed.play(0, getX() + (Block.SIZE * 1.5), getY() + (Block.SIZE * 1.5), level.game.getCamX(), level.game.getCamY());
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return true;
	}

	@Override
	public boolean doSave()
	{
		return true;
	}
}
