package game.item;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.levels.Level;
import game.obj.Oven;
import game.obj.Workbench;

public class ItemOven
extends Item {
	
	public ItemOven()
	{
		setItemID(ID_OVEN);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_oven"));
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		Oven oven = new Oven();
		oven.setPosition(x - 96, y - 96);
		level.addObject(oven);
		
		slot.removeItem(1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(16, 272, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(16, 272, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(16, 272, 16, 16));
	}

}
