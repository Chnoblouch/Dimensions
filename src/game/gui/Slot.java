package game.gui;

import java.awt.event.KeyEvent;
import java.util.Random;

import game.gfx.Screen;
import game.item.Item;
import game.utils.DoublePoint;

public class Slot {
	
	public Inventory inventory;
	public int id;
	public int x, y;
	
	private Item item;
	private int count = new Random().nextInt(20);
	
	private int size;
		
	public Slot(Inventory inventory, int id, int x, int y)
	{
		this.inventory = inventory;
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public boolean canHold(Item item, int count)
	{
		return true;
	}
	
	public boolean isMouseOn()
	{
		double mx = inventory.game.getMouse().x;
		double my = inventory.game.getMouse().y;
		return mx >= x && mx <= x + size && my >= y && my <= y + size;
	}
	
	public void mousePressed(DoublePoint pos, int button)
	{			
		if(!inventory.visible) return;
			
		mousePressed();
		
		if(!(inventory.game.input.isPressed(KeyEvent.VK_SHIFT) && this instanceof SlotDefault))
		{
			if(!inventory.isItemOnMouse())
			{
				inventory.itemOnMouse = item;
				inventory.itemOnMouseCount = count;
				clear();
								
				return;
			} else 
			{
				if(!canHold(inventory.itemOnMouse, inventory.itemOnMouseCount)) return;
				
				if(isEmpty())
				{
					setItem(inventory.itemOnMouse, inventory.itemOnMouseCount);
					
					inventory.itemOnMouse = null;
					inventory.itemOnMouseCount = 0;
				} else 
				{
					if(item.getItemID() == inventory.itemOnMouse.getItemID())
					{
						count += inventory.itemOnMouseCount;
						
						inventory.itemOnMouse = null;
						inventory.itemOnMouseCount = 0;
					} else 
					{
						Item oldItem = getItem();
						int oldCount = getItemCount();
						
						setItem(inventory.itemOnMouse, inventory.itemOnMouseCount);
						
						inventory.itemOnMouse = oldItem;
						inventory.itemOnMouseCount = oldCount;
					}
				}
				
				return;
			}	
		} else 
		{
			Slot target = null;
			if(button == 1) target = inventory.getMainHandSlot();
			else if(button == 3) target = inventory.getOffHandSlot();
			
			if(target.isEmpty())
			{
				if(target.canHold(getItem(), getItemCount())) target.setItem(getItem(), getItemCount());
				clear();
			} else
			{
				if(target.canHold(getItem(), getItemCount()) && canHold(target.getItem(), target.getItemCount()))
				{
					Item oldItem = getItem();
					int oldCount = getItemCount();
					
					setItem(target.getItem(), target.getItemCount());
					target.setItem(oldItem, oldCount);
				}
			}
		}
	}

	public void mouseReleased(DoublePoint pos, int button)
	{
		if(!inventory.visible) return;
		mouseReleased();
	}
	
	public void setItem(Item item, int count)
	{
		this.item = item;
		this.count = count;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public void setItemCount(int count)
	{
		this.count = count;
	}
	
	public void addItem(int count)
	{
		this.count += count;
	}
	
	public void removeItem(int count)
	{
		this.count -= count;
		if(count <= 0) clear();
	}
	
	public int getItemCount()
	{
		return count;
	}
	
	public void clear()
	{
		item = null;
		count = 0;
	}
	
	public boolean isEmpty()
	{
		return item == null || count < 1;
	}
	
	public void render(Screen screen) {}
	public void renderInfo(Screen screen) {}
	public void mousePressed() {}
	public void mouseReleased() {}

}
