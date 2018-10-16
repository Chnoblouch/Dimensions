package game.gui;

import java.util.ArrayList;

import game.gfx.Screen;
import game.utils.SafeArrayList;

public class Hotbar {

	public ArrayList<Slot> slots = new SafeArrayList<>();
	
	private Slot selectedSlot;
	
	public Hotbar(Inventory inventory)
	{
		for(int x = 0; x < 9; x++)
		{
			Slot s = new Slot(inventory, x, (x * 85) + (Screen.WIDTH / 2) - (9 * 85 / 2), Screen.HEIGHT - 128);
			
			slots.add(s);
			inventory.defaultSlots.add(s);
			
			if(x == 0) selectedSlot = s;
		}
	}
	
	public void setSelectedSlot(int s)
	{
		selectedSlot = slots.get(s);
	}
	
	public void selectNext()
	{
		int i = slots.indexOf(selectedSlot)+1;
		if(i >= slots.size()) i = 0;
		
		selectedSlot = slots.get(i);
	}
	
	public void selectLast()
	{
		int i = slots.indexOf(selectedSlot)-1;
		if(i < 0) i = slots.size()-1;
		
		selectedSlot = slots.get(i);
	}
	
	public Slot getSelectedSlot()
	{
		return selectedSlot;
	}
}
