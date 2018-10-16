package game.gui;

import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.item.Item;

public class SelectionSlot 
extends Slot {
	
	private int hand;
	private int iconX = 40;

	public SelectionSlot(Inventory inventory, int id, int x, int y, int hand)
	{
		super(inventory, id, x, y);
		setSize(120);
		
		this.hand = hand;
	}
	
	@Override
	public boolean canHold(Item item, int count)
	{
		return item.getHand() == Item.BOTH_HANDS || item.getHand() == hand;
	}
	
	@Override
	public void mousePressed()
	{
		iconX = 70;
	}
	
	@Override
	public void mouseReleased()
	{
		iconX = 40;
	}
	
	@Override
	public void render(Screen screen)
	{
		screen.renderGUI(SpriteSheet.inventory.getSprite(iconX, 192, 30, 30), x, y, 120, 120, 0, 1);
				
		if(!isEmpty())
		{
			getItem().render(screen, x + 12, y + 12, 96, false, 0, 1);
			if(getItemCount() > 1) 
				screen.renderFont(getItemCount() + "", 
								  x + 120 - 16 -(Font.getTextWidth(getItemCount() + "", 32)), 
								  y + 120 - 48, 32,
								  Font.COLOR_WHITE, 
								  false);
		}
	}

}
