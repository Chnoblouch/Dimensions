package game.item.craftingstations;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;
import game.obj.Anvil;
import game.obj.Oven;
import game.obj.Workbench;

public class ItemAnvil
extends Item {
	
	public ItemAnvil()
	{
		setItemID(ID_ANVIL);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_anvil"));
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		Anvil anvil = new Anvil();
		anvil.setPosition(x - 96, y - 96);
		level.addObject(anvil);
		
		slot.removeItem(1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(32, 304, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(32, 304, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(32, 304, 16, 16));
	}

}
