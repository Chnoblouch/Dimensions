package game.environment;

import java.util.Random;

import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.obj.GameObject;
import game.utils.Block;

public class Farmland
extends GameObject {
		
	
	@Override
	public int getZIndex()
	{
		return 0;
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
			screen.render(SpriteSheet.farmland.getSprite(0, 0, 24, 24), getX(), getY(), Block.SIZE, Block.SIZE, 0, 1);
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
