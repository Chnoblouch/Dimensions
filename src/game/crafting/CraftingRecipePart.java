package game.crafting;

import game.item.Item;

public class CraftingRecipePart {
	
	private Item item;
	private int count;
	
	public CraftingRecipePart(Item item, int count)
	{
		this.item = item;
		this.count = count;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public int getItemCount()
	{
		return count;
	}

}
