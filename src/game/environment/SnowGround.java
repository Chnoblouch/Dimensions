package game.environment;

import java.util.Random;

import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.obj.GameObject;
import game.utils.Block;

public class SnowGround
extends GameObject {
	
	private int sx;
		
	@Override
	public int getZIndex()
	{
		return 0;
	}
	
	@Override
	public void readData(int data)
	{
		switch (data) 
		{
			case 0: sx = 0; break;
			case 1: sx = 24; break;
			case 2: sx = 48; break;
		}
	}
	
	@Override
	public boolean doUpdate()
	{
		return false;
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), Block.SIZE, Block.SIZE)) 
			screen.render(SpriteSheet.snowBlock.getSprite(sx, 0, 24, 24), getX(), getY(), Block.SIZE, Block.SIZE, 0, 1);
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return false;
	}
	
	@Override
	public boolean doSave()
	{
		return true;
	}

}
