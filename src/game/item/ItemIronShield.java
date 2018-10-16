package game.item;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.levels.Level;

public class ItemIronShield
extends Item {
	
	public ItemIronShield()
	{
		setItemID(ID_IRON_SHIELD);
		setHand(OFF_HAND);
		setName(TextLoader.getText("item_iron_shield"));
	}
		
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
		screen.render(SpriteSheet.ironShieldInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		player.protect(this);
	}
	
	@Override
	public void release(Player player)
	{
		player.stopProtecting();
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(32, 128, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(32, 128, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(32, 128, 16, 16));
	}

}
