package game.item;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.levels.Level;
import game.projectiles.FlyingArrow;

public class ItemBow
extends Item {
	
	public ItemBow()
	{
		setItemID(ID_BOW);
		setHand(MAIN_HAND);
		setName(TextLoader.getText("item_bow"));
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		if(!player.inventory.hasEnough(Item.ID_ARROW, 1)) return;
		
		FlyingArrow arrow = new FlyingArrow();
		arrow.setPosition(player.getX() + 128 - 32, player.getY() + 128 - 32);
		arrow.setAngle(player.getAngleToMouse());
		level.addObject(arrow);
		
		player.inventory.getSlotWithEnough(Item.ID_ARROW, 1).removeItem(1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(0, 144, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(0, 144, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 144, 16, 16));
	}

}
