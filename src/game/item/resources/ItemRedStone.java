package game.item.resources;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemRedStone
extends Item {
	
	public ItemRedStone()
	{
		setItemID(ID_RED_STONE);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_red_stone"));
		setDescription(TextLoader.getText("item_descr_red_stone"));
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(96, 0, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(96, 0, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(96, 0, 16, 16));
	}

}
