package game.crafting;

import game.gui.Inventory;
import game.item.Item;

public class CraftingRecipe {
	
	private CraftingRecipePart[] parts;
	
	private Item result;
	private int resultCount;
	
	public CraftingRecipe(Item result, int count, CraftingRecipePart... parts)
	{
		this.parts = parts;
		this.result = result;
		this.resultCount = count;
	}
	
	public boolean isPossible(Inventory inventory)
	{		
		for(int i = 0; i < getParts().length; i++)
		{
			CraftingRecipePart part = getParts()[i];
			if(!inventory.hasEnough(part.getItem().getItemID(), part.getItemCount())) return false;
		}
		
		return true;
	}
	
	public CraftingRecipePart[] getParts()
	{
		return parts;
	}
	
	public Item getResult()
	{
		return result;
	}
	
	public int getResultCount()
	{
		return resultCount;
	}

}
