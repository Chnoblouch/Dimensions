package game.item;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;

public class ItemWoodSword
extends Item {
	
	public ItemWoodSword()
	{
		setItemID(ID_WOOD_SWORD);
		setHand(MAIN_HAND);
		setAttackDamage(1.5);
		setName(TextLoader.getText("item_wood_sword"));
	}
	
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
		if(hand == MAIN_HAND)
			screen.render(SpriteSheet.woodSwordInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
		else if(hand == OFF_HAND)
			screen.render(SpriteSheet.woodSwordInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(0, 80, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(0, 80, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 80, 16, 16));
	}

}
