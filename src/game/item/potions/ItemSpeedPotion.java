package game.item.potions;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.effects.EffectSpeed;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;
import game.utils.Time;

public class ItemSpeedPotion
extends Item {
	
	public ItemSpeedPotion()
	{
		setItemID(ID_SPEED_POTION);
		setHand(MAIN_HAND);
		setName(TextLoader.getText("item_speed_potion"));
		setDescription(TextLoader.getText("item_descr_speed_potion"));
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		player.effect(new EffectSpeed(Time.secondsInTicks(20)));
		slot.removeItem(1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(32, 320, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(32, 320, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(32, 320, 16, 16));
	}

}
