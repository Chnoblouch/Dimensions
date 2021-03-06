package game.item.ingots;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemIronIngot
extends Item {
	
	public ItemIronIngot()
	{
		setItemID(ID_IRON_INGOT);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_iron_ingot"));
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(0, 288, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(0, 288, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 288, 16, 16));
	}

}
