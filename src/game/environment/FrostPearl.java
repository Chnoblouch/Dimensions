package game.environment;

import java.util.Random;

import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.item.resources.ItemFrostPearl;
import game.obj.Drop;
import game.obj.GameObject;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class FrostPearl
extends GameObject {
	
	private boolean empty = false;
	
	@Override
	public int getZIndex()
	{
		return (int) (getY());
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
			screen.render(SpriteSheet.iceworld.getSprite(empty ? 24 : 0, 0, 24, 24), getX(), getY(), Block.SIZE, Block.SIZE, 0, 1);
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
		if(empty || !mouseOn) return;
		
		empty = true;
		
		Drop drop = new Drop(new ItemFrostPearl(), 1);
		drop.setPosition(getX() + (new Random().nextInt((int) (Block.SIZE))), 
						 getY() + (new Random().nextInt((int) (Block.SIZE))));
		level.addObject(drop);
	}
	
	@Override
	public boolean doSave()
	{
		return true;
	}

}
