package game.item.food;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;

public class ItemMeat
extends Item {
	
	public ItemMeat()
	{
		setItemID(ID_MEAT);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_meat"));
	}
	
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
		if(hand == MAIN_HAND)
			screen.render(SpriteSheet.meatInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
		else if(hand == OFF_HAND)
			screen.render(SpriteSheet.meatInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		if(player.isEating()) return;

		player.eat(this);
		player.heal(12);
		slot.removeItem(1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(32, 48, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(32, 48, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(32, 48, 16, 16));
	}

}
