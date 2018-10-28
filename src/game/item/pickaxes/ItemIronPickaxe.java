package game.item.pickaxes;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemIronPickaxe
extends Item {
	
	public ItemIronPickaxe()
	{
		setItemID(ID_IRON_PICKAXE);
		setHand(MAIN_HAND);
		setStoneDamage(4.75);
		setName(TextLoader.getText("item_iron_pickaxe"));
		setDescription(TextLoader.getText("stone_damage") + ": " + getStoneDamage());
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(32, 80, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(32, 80, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(32, 80, 16, 16));
	}

}
