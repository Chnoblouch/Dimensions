package game.item.swords;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.item.Item;

public class ItemGoldSword
extends Item {
	
	public ItemGoldSword()
	{
		setItemID(ID_GOLD_SWORD);
		setHand(MAIN_HAND);
		setAttackDamage(16);
		setName(TextLoader.getText("item_gold_sword"));
		setDescription(TextLoader.getText("melee_damage") + ": " + getAttackDamage());
	}
	
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
		if(hand == MAIN_HAND)
			screen.render(SpriteSheet.goldSwordInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
		else if(hand == OFF_HAND)
			screen.render(SpriteSheet.goldSwordInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(48, 112, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(48, 112, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(48, 112, 16, 16));
	}

}
