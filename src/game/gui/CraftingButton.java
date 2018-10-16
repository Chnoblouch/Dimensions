package game.gui;

import game.TextLoader;
import game.crafting.CraftingRecipe;
import game.crafting.CraftingRecipePart;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.utils.DoublePoint;

public class CraftingButton {
	
	private InventoryMenu menu;
	private int x, y;
	private CraftingRecipe recipe;
	
	private int iconX = 100;
	
	public CraftingButton(InventoryMenu menu, int x, int y, CraftingRecipe recipe)
	{
		this.menu = menu;
		this.x = x;
		this.y = y;
		this.recipe = recipe;
	}
	
	public boolean isMouseOn()
	{
		double mx = menu.inventory.game.getMouse().x;
		double my = menu.inventory.game.getMouse().y;
		return mx >= x && mx <= x + 80 && my >= y && my <= y + 80;
	}
	
	public void mousePressed(DoublePoint pos, int button)
	{			
		if(!menu.getInventory().visible) return;
		
		iconX = 120;
				
		if(button == 1) craft();
		else if(button == 3)
		{
			for(int i = 0; i < 10; i++) craft();
		}
	}
	
	private void craft()
	{
		if(recipe.isPossible(menu.getInventory()))
		{
			if(menu.getInventory().itemOnMouse != null && menu.getInventory().itemOnMouse.getItemID() == recipe.getResult().getItemID()) 
			{
				menu.getInventory().itemOnMouseCount += recipe.getResultCount();
				removeIngredients();
			}
			else 
			{
				if(menu.getInventory().itemOnMouse == null)
				{
					menu.getInventory().itemOnMouse = recipe.getResult();
					menu.getInventory().itemOnMouseCount = recipe.getResultCount();
					removeIngredients();
				}
			}
		}
	}
	
	private void removeIngredients()
	{
		for(int i = 0; i < recipe.getParts().length; i++)
		{
			CraftingRecipePart part = recipe.getParts()[i];
			menu.getInventory().getSlotWithEnough(part.getItem().getItemID(), part.getItemCount()).removeItem(part.getItemCount());
		}
	}

	public void mouseReleased(DoublePoint pos, int button)
	{
		if(!menu.getInventory().visible) return;		
		iconX = 100;
	}
	
	public void render(Screen screen)
	{
		boolean possible = recipe.isPossible(menu.getInventory());
		
		screen.renderGUI(SpriteSheet.inventory.getSprite(iconX, 192, 20, 20), x, y, 80, 80, 0, possible ? 1 : 0.5);
		
		recipe.getResult().render(screen, x+8, y+8, 64, false, 0, possible ? 1 : 0.5);
		
		int c = recipe.getResultCount();
		if(c > 1) screen.renderFont(c+"", x+80-4-(Font.getTextWidth(c+"", 32)), y+80-32, 32, Font.COLOR_WHITE, false);
		
		if(isMouseOn())
		{
			String ingredients = TextLoader.getText("crafting_ingredients");
			screen.renderFont(ingredients, menu.inventory.x - 300 - 25 - Font.getTextWidth(ingredients, 32), y - 48, 32, Font.COLOR_WHITE, false);
			
			for(int i = 0; i < recipe.getParts().length; i++)
			{
				CraftingRecipePart part = recipe.getParts()[i];
				
				int x = menu.inventory.x - 300 - 85 - ((i % 3) * 85);
				int y = (int) (this.y + (Math.floor(i / 3) * 85));
				boolean enough = menu.inventory.hasEnough(part.getItem().getItemID(), part.getItemCount());
				
				screen.renderGUI(SpriteSheet.inventory.getSprite(100, 192, 20, 20), x, y, 60, 60, 0, enough ? 1 : 0.5);
				part.getItem().render(screen, x + 6, y + 6, 48, false, 0, enough ? 1 : 0.5);
				screen.renderFont(""+part.getItemCount(), 
								  x + 60 - 4 - (Font.getTextWidth(part.getItemCount()+"", 24)), 
								  y + 60 - 24, 24, 
								  Font.COLOR_WHITE, false);
			}
		}
	}

}
