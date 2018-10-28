package game.item.craftingstations;

import java.awt.image.BufferedImage;

import game.TextLoader;
import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Slot;
import game.item.Item;
import game.levels.Level;
import game.obj.AlchemyTable;
import game.obj.Workbench;
import game.utils.Block;

public class ItemAlchemyTable
extends Item {
	
	public ItemAlchemyTable()
	{
		setItemID(ID_ALCHEMY_TABLE);
		setHand(BOTH_HANDS);
		setName(TextLoader.getText("item_alchemy_table"));
	}
	
	@Override
	public void use(double x, double y, Level level, Player player, Slot slot)
	{
		AlchemyTable alchemyTable = new AlchemyTable();
		alchemyTable.setPosition(x - 96, y - 96);
		level.addObject(alchemyTable);
		
		slot.removeItem(1);
		
//		for(double fy = Level.CENTER - (Level.SIZE / 2); fy < Level.CENTER + (Level.SIZE / 2); fy += Block.SIZE)
//		{
//			for(double fx = Level.CENTER - (Level.SIZE / 2); fx < Level.CENTER + (Level.SIZE / 2); fx += Block.SIZE)
//			{
//				if(x >= fx && x <= fx + Block.SIZE && y >= fy && y <= fy + Block.SIZE)
//				{
//					Workbench workbench = new Workbench();
//					workbench.setPosition(fx, fy);
//					level.addObject(workbench);
//					
//					slot.removeItem(1);
//					
//					break;
//				}
//			}
//		}
	}
	
	@Override
	public void render(Screen screen, double x, double y, double size, boolean inCam, double rot, double alpha)
	{
		if(inCam) screen.render(SpriteSheet.items.getSprite(48, 304, 16, 16), x, y, size, size, rot, alpha);
		else screen.renderGUI(SpriteSheet.items.getSprite(48, 304, 16, 16), x, y, size, size, rot, alpha);
	}
	
	@Override
	public BufferedImage getShadow()
	{
		return SpriteFilter.getShadowStanding(SpriteSheet.items.getSprite(0, 304, 16, 16));
	}

}
