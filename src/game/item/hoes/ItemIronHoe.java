package game.item.hoes;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.environment.Farmland;
import game.environment.GrassGround;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;
import game.obj.GameObject;
import game.utils.Block;

public class ItemIronHoe
extends Item {
	
	public ItemIronHoe()
	{
		setItemID(ID_IRON_HOE);
		setHand(MAIN_HAND);
		setName(TextLoader.getText("item_iron_hoe"));
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		for(int i = 0; i < level.objects.size(); i++)
		{
			GameObject o = level.objects.get(i);
			
			if(o instanceof GrassGround && o.getHitbox().intersects(player.getInteractionArea()) &&
			   x >= o.getX() && x <= o.getX() + Block.SIZE && y >= o.getY() && y <= o.getY() + Block.SIZE)
			{
				Farmland f = new Farmland();
				f.setPosition(o.getX(), o.getY());
				level.addObject(f);
				
				level.removeObject(o);
			}
		}
	}
	
	@Override
	public void renderInHand(Screen screen, Player player, int hand)
	{
//		if(hand == MAIN_HAND)
//			screen.render(SpriteSheet.ironAxeInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
//		else if(hand == OFF_HAND)
//			screen.render(SpriteSheet.ironAxeInHand.getSprite(player.sx, player.sy, 64, 64), player.getX(), player.getY(), 256, 256, 0, 1);
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(32, 128, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(32, 128, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(32, 128, 16, 16));
	}

}
