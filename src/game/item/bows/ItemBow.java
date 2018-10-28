package game.item.bows;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;
import game.projectiles.FlyingArrow;

public class ItemBow
extends Item {
	
	public ItemBow()
	{
		setItemID(ID_BOW);
		setHand(MAIN_HAND);
		setName(TextLoader.getText("item_bow"));
		setDescription(TextLoader.getText("ranged_damage") + ": 12");
	}
	
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
		screen.render(SpriteSheet.bowInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		if(!player.inventory.hasEnough(Item.ID_ARROW, 1) || player.shooting) return;
		
		player.aiming = true;
	}
	
	@Override
	public void release(double x, double y, Level level, Player player, Slot slot)
	{
		if(!player.inventory.hasEnough(Item.ID_ARROW, 1) || player.shooting) return;
		
		FlyingArrow arrow = new FlyingArrow();
		arrow.setPosition(player.getX() + 128 - 32, player.getY() + 128 - 32);
		arrow.setAngle(player.getAngleToMouse());
		level.addObject(arrow);
		
		Sounds.shoot.play(1, arrow.getX() + 32, arrow.getY() + 32, level.game.getCamX(), level.game.getCamY());
		
		player.aiming = false;
		player.shooting = true;
		
		player.inventory.getSlotWithEnough(Item.ID_ARROW, 1).removeItem(1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(0, 176, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(0, 176, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 176, 16, 16));
	}

}
