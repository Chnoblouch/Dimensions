package game.item.shovels;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemIronShovel
extends Item {
	
	public ItemIronShovel()
	{
		setItemID(Item.ID_IRON_SHOVEL);
		setHand(MAIN_HAND);
		setName(TextLoader.getText("item_iron_shovel"));
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(32, 96, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(32, 96, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(32, 96, 16, 16));
	}

}
