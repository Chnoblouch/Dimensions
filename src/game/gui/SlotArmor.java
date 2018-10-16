package game.gui;

import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.item.Item;

public class SlotArmor 
extends Slot {
	
	private int iconX = 0;
	private int armor;

	public SlotArmor(Inventory inventory, int id, int x, int y, int armor) 
	{
		super(inventory, id, x, y);
		setSize(80);
		this.armor = armor;
	}
	
	@Override
	public void mousePressed()
	{
		iconX = 20;
	}
	
	@Override
	public void mouseReleased()
	{
		iconX = 0;
	}
	
	@Override
	public boolean canHold(Item item, int count)
	{
		return item.isArmor() && item.getArmorID() == armor;
	}
	
	@Override
	public void render(Screen screen)
	{
		screen.renderGUI(SpriteSheet.inventory.getSprite(iconX, 192, 20, 20), x, y, 80, 80, 0, 1);
				
		if(!isEmpty())
		{
			getItem().render(screen, x + 8, y + 8, 64, false, 0, 1);
			if(getItemCount() > 1) 
				screen.renderFont(getItemCount()+"", 
								  x + 80 - 4 - (Font.getTextWidth(getItemCount() + "", 32)), 
								  y + 80 - 32, 32,
								  Font.COLOR_WHITE, 
								  false);
		} else 
		{
			screen.renderGUI(SpriteSheet.inventory.getSprite(140 + (armor * 20), 192, 20, 20), x, y, 80, 80, 0, 1);
		}
	}
	
	@Override
	public void renderInfo(Screen screen)
	{
		int mx = (int) inventory.game.getMouse().x;
		int my = (int) inventory.game.getMouse().y;
		
		int dmx = 48; // x distance to mouse
		int dmy = -48; // y distance to mouse
		int fontSize = 32;
				
		boolean nameLonger = false;
		if(getItem().getDescription() == null) nameLonger = true;
		else nameLonger = getItem().getName().length() > getItem().getDescription().length();
		
		int bx = mx + dmx; // border x;
		int by = my + dmy; // border y;
		int bw = (int) ((Font.getTextWidth(nameLonger ? getItem().getName() : getItem().getDescription(), fontSize) / 32) * 32) + 32; // border with
		int bh = getItem().getDescription() == null ? 32 : 64; // border height
		
		screen.renderGUI(SpriteSheet.itemInfo.getSprite(0, 0, 8, 8), bx, by, 32, 32, 0, 1);		
		screen.renderGUI(SpriteSheet.itemInfo.getSprite(16, 0, 8, 8), bx + bw, by, 32, 32, 0, 1);
		screen.renderGUI(SpriteSheet.itemInfo.getSprite(0, 16, 8, 8), bx, by + bh, 32, 32, 0, 1);		
		screen.renderGUI(SpriteSheet.itemInfo.getSprite(16, 16, 8, 8), bx + bw, by + bh, 32, 32, 0, 1);
		for(int i = 0; i < bw - 32; i+= 32) 
		{ 
			screen.renderGUI(SpriteSheet.itemInfo.getSprite(8, 0, 8, 8), bx + 32 + i, by, 32, 32, 0, 1); 
			screen.renderGUI(SpriteSheet.itemInfo.getSprite(8, 16, 8, 8), bx + 32 + i, by + bh, 32, 32, 0, 1); 
		}
		for(int i = 0; i < bh - 32; i+= 32) 
		{ 
			screen.renderGUI(SpriteSheet.itemInfo.getSprite(0, 8, 8, 8), bx, by + i + 32, 32, 32, 0, 1); 
			screen.renderGUI(SpriteSheet.itemInfo.getSprite(16, 8, 8, 8), bx + bw, by + 32 + i, 32, 32, 0, 1); 
		}
		for(int fy = by + 32; fy < by + bh; fy+= 32)
		{
			for(int fx = bx + 32; fx < bx + bw; fx+= 32)
			{
				screen.renderGUI(SpriteSheet.itemInfo.getSprite(8, 8, 8, 8), fx, fy, 32, 32, 0, 1); 
			}
		}
		
		screen.renderFont(getItem().getName(), bx + 16, by + 16, fontSize, Font.COLOR_WHITE, false);
		
		if(getItem().getDescription() != null)
		{
			screen.renderFont(getItem().getDescription(), bx + 16, by + 48, fontSize, Font.COLOR_WHITE, false);
		}
	}

}
