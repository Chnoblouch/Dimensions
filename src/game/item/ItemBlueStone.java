package game.item;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;

public class ItemBlueStone
extends Item {
	
	public ItemBlueStone()
	{
		setItemID(ID_BLUE_STONE);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_blue_stone"));
		setDescription(TextLoader.getText("item_descr_blue_stone"));
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(192, 0, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(192, 0, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(192, 0, 16, 16));
	}

}
