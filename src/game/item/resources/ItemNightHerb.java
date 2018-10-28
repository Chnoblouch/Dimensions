package game.item.resources;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemNightHerb
extends Item {
	
	public ItemNightHerb()
	{
		setItemID(ID_NIGHT_HERB);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_night_herb"));
		setDescription(TextLoader.getText("item_descr_night_herb"));
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(64, 0, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(64, 0, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(64, 0, 16, 16));
	}

}
