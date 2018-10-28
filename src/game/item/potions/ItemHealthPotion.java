package game.item.potions;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;

public class ItemHealthPotion
extends Item {
	
	public ItemHealthPotion()
	{
		setItemID(ID_HEALTH_POTION);
		setHand(MAIN_HAND);
		setName(TextLoader.getText("item_health_potion"));
		setDescription(TextLoader.getText("item_descr_health_potion"));
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		player.heal(75);
		slot.removeItem(1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(16, 320, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(16, 320, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(16, 320, 16, 16));
	}

}
