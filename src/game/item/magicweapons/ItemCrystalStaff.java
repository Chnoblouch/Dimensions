package game.item.magicweapons;

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
import game.projectiles.FlyingCrystalSplinter;
import game.projectiles.FlyingTaykolos;
import game.utils.Angles;

public class ItemCrystalStaff
extends Item {
	
	public ItemCrystalStaff()
	{
		setItemID(ID_CRYSTAL_STAFF);
		setHand(MAIN_HAND);
		setName(TextLoader.getText("item_crystal_staff"));
		setDescription(TextLoader.getText("item_descr_crystal_staff"), 
					   TextLoader.getText("ranged_damage") + ": 11.0",
					   TextLoader.getText("uses_x_mana").split("/in")[0] + "7.0" + TextLoader.getText("uses_x_mana").split("/in")[1]);
	}
	
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
		if(player.thrownSomething) return;
		
		if(hand == MAIN_HAND)
			screen.render(SpriteSheet.crystalStaffInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
		else if(hand == OFF_HAND)
			screen.render(SpriteSheet.crystalStaffInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{		
		if(player.getMana() < 7) return;
				
		player.interact(x, y, this);
		player.removeMana(7);
		
		FlyingCrystalSplinter splinter = new FlyingCrystalSplinter();
		splinter.setPosition(player.getX() + 128 - 32, player.getY() + 128 - 64);
		
		double angle = Angles.getAngle(splinter.getX() - level.game.screen.camX + 32, 
									   splinter.getY() - level.game.screen.camY + 32, 
									   level.game.getMouse().x, 
									   level.game.getMouse().y);
		
		splinter.setAngle(angle);
		level.addObject(splinter);
		
		Sounds.crystal.play(0, splinter.getX() + 24, splinter.getY() + 24, level.game.getCamX(), level.game.getCamY());
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(16, 208, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(16, 208, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(16, 208, 16, 16));
	}

}
