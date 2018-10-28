package game.item.special;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.MonsterGiantSlime;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;
import game.levels.LevelSkyWorld;
import game.obj.Anvil;
import game.obj.Oven;
import game.obj.Workbench;

public class ItemShimmeringSlime
extends Item {
	
	public ItemShimmeringSlime()
	{
		setItemID(ID_SHIMMERING_SLIME);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_shimmering_slime"));
		setDescription(TextLoader.getText("item_descr_shimmering_slime"));
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		if(level instanceof LevelSkyWorld) return;
		
		MonsterGiantSlime giantSlime = new MonsterGiantSlime();
		giantSlime.setPosition(player.getX(), player.getY() + 1024);
		level.addObject(giantSlime);
		
		player.impressed();
		
		slot.removeItem(1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(0, 336, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(0, 336, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 336, 16, 16));
	}

}
