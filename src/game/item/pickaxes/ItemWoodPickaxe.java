package game.item.pickaxes;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemWoodPickaxe
extends Item {
	
	public ItemWoodPickaxe()
	{
		setItemID(ID_WOOD_PICKAXE);
		setHand(MAIN_HAND);
		setStoneDamage(2);
		setName(TextLoader.getText("item_wood_pickaxe"));
		setDescription(TextLoader.getText("stone_damage") + ": " + getStoneDamage());
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(0, 80, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(0, 80, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 80, 16, 16));
	}

}
