package game.gui;

import java.util.ArrayList;

import game.crafting.CraftingRecipes;
import game.gfx.Screen;
import game.utils.DoublePoint;
import game.utils.SafeArrayList;

public class AlchemyCraftingMenu 
extends InventoryMenu {
	
	private ArrayList<CraftingButton> buttons = new SafeArrayList<>();
		
	public AlchemyCraftingMenu(Inventory inventory)
	{
		super(inventory);
		setMenuID(MENU_ALCHEMY_CRAFTING);
		
		int x = -1;
		int y = 0;
		
		for(int i = 0; i < CraftingRecipes.getAlchemyRecipes().size(); i++)
		{
			x ++;
			if(x >= 3) 
			{
				x = 0;
				y ++;
			}
			
			buttons.add(new CraftingButton(this, inventory.x - 300 + (x * 85), inventory.firstSlotY + (y * 85), 
										   CraftingRecipes.getAlchemyRecipes().get(i)));
		}
	}
	
	@Override
	public void mousePressed(DoublePoint pos, int button)
	{
		for(int i = 0; i < buttons.size(); i++) 
		{
			CraftingButton b = buttons.get(i);
			if(b.isMouseOn()) b.mousePressed(pos, button);
		}
	}
	
	@Override
	public void mouseReleased(DoublePoint pos, int button)
	{
		for(int i = 0; i < buttons.size(); i++) { buttons.get(i).mouseReleased(pos, button); }
	}
	
	@Override
	public void render(Screen screen)
	{
		for(int i = 0; i < buttons.size(); i++) buttons.get(i).render(screen);
	}
}
