package game.item.magicweapons;

import java.awt.image.BufferedImage;
import java.util.Random;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;
import game.projectiles.FlyingArrow;
import game.projectiles.FlyingTaykolos;

public class ItemTaykolos
extends Item {
	
	public ItemTaykolos()
	{
		setItemID(ID_TAYKOLOS);
		setHand(MAIN_HAND);
		setName(TextLoader.getText("item_taykolos"));
		setDescription(TextLoader.getText("item_descr_taykolos"), 
					   TextLoader.getText("ranged_damage") + ": 15.0",
					   TextLoader.getText("uses_x_mana").split("/in")[0] + "14.0" + TextLoader.getText("uses_x_mana").split("/in")[1]);
	}
	
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
		if(player.thrownSomething) return;
		
		if(hand == MAIN_HAND)
			screen.render(SpriteSheet.taykolosInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
		else if(hand == OFF_HAND)
			screen.render(SpriteSheet.taykolosInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{		
		if(player.thrownSomething || player.getMana() < 14) return;
		
		player.thrownSomething = true;
		player.removeMana(14);
		
		FlyingTaykolos taykolos = new FlyingTaykolos();
		taykolos.setPosition(player.getX() + 128 - 32, player.getY() + 128 - 32);
		taykolos.setAngle(player.getAngleToMouse());
		level.addObject(taykolos);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(0, 208, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(0, 208, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 208, 16, 16));
	}

}
