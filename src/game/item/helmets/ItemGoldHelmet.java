package game.item.helmets;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;

public class ItemGoldHelmet
extends Item {
	
	public ItemGoldHelmet()
	{
		setItemID(ID_GOLD_HELMET);
		setHand(BOTH_HANDS);
		setArmor(true);
		setArmorID(ARMOR_HELMET);
		setProtection(10);
		setName(TextLoader.getText("item_gold_helmet"));
		setDescription(TextLoader.getText("protection") + ": " + getProtection());
	}
	
	@Override
	public void renderOnBody(Screen screen, Player player)
	{
		screen.render(SpriteSheet.goldHelmetOnBody.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(16, 224, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(16, 224, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(16, 224, 16, 16));
	}

}
