package game.obj;

import java.awt.image.BufferedImage;

import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.InventoryMenu;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class AlchemyTable 
extends GameObject {
	
	public AlchemyTable()
	{
		
	}
	
	@Override
	public void interactWith(Player player, boolean mouseOn)
	{
		if(!mouseOn) return;
		player.inventory.changeVisibility(InventoryMenu.MENU_ALCHEMY_CRAFTING);
	}
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + (Block.SIZE * 2) - 24);
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), Block.SIZE * 4, Block.SIZE * 4))
		{
			BufferedImage sprite = SpriteSheet.craftingstations.getSprite(144, 0, 48, 48);
						
			screen.render(SpriteFilter.getShadowStanding(sprite), getX() - 16, getY(), Block.SIZE * 4, Block.SIZE * 2, 0, 0.25);
			screen.render(sprite, getX(), getY(), Block.SIZE * 2, Block.SIZE * 2, 0, 1);
		}
	}
	
	@Override
	public boolean inCollisionSpace()
	{
		return true;
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return true;
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX() + 24, getY() + Block.SIZE, Block.SIZE * 2 - 48, Block.SIZE);
	}
	
	@Override
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(getX(), getY(), Block.SIZE * 2, Block.SIZE * 2);
	}

}
