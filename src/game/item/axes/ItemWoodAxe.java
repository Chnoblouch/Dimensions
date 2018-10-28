package game.item.axes;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemWoodAxe
extends Item {
	
	public ItemWoodAxe()
	{
		setItemID(ID_WOOD_AXE);
		setHand(MAIN_HAND);
		setWoodDamage(1.5);
		setName(TextLoader.getText("item_wood_axe"));
		setDescription(TextLoader.getText("wood_damage") + ": " + getWoodDamage());
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(0, 64, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(0, 64, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 64, 16, 16));
	}

}
