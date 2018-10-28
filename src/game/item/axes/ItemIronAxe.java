package game.item.axes;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemIronAxe
extends Item {
	
	public ItemIronAxe()
	{
		setItemID(ID_IRON_AXE);
		setHand(MAIN_HAND);
		setWoodDamage(4);
		setName(TextLoader.getText("item_iron_axe"));
		setDescription(TextLoader.getText("wood_damage") + ": " + getWoodDamage());
	}
	
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
		if(hand == MAIN_HAND)
			screen.render(SpriteSheet.ironAxeInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
		else if(hand == OFF_HAND)
			screen.render(SpriteSheet.ironAxeInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(32, 64, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(32, 64, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(32, 64, 16, 16));
	}

}
