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
	
	public void renderInfo(Screen screen)
	{
		int mx = (int) menu.inventory.game.getMouse().x;
		int my = (int) menu.inventory.game.getMouse().y;
		
		int dmx = 48; // x distance to mouse
		int dmy = -48; // y distance to mouse
		int fontSize = 32;
				
		String longestPart = recipe.getResult().getName();

		if(recipe.getResult().getDescription() != null) 
		{
			int longestPartLength = longestPart.length();
			for(int i = 0; i < recipe.getResult().getDescription().length; i++) 
			{
				String text = recipe.getResult().getDescription()[i];
				if(text.length() > longestPartLength)
				{
					longestPartLength = text.length();
					longestPart = text;
				}
			}
		}
		
		int bx = mx + dmx; // border x;
		int by = my + dmy; // border y;
		int bw = (int) Math.ceil(Font.getTextWidth(longestPart, fontSize)); // border with
		int bh = recipe.getResult().getDescription() == null ? 32 : 32 + recipe.getResult().getDescription().length * 32; // border height
		
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
		
		screen.renderFont(recipe.getResult().getName(), bx + 16, by + 16, fontSize, Font.COLOR_WHITE, false);
		
		if(recipe.getResult().getDescription() != null)
		{
			for(int i = 0; i < recipe.getResult().getDescription().length; i++)
			{
				screen.renderFont(recipe.getResult().getDescription()[i], bx + 16, by + 48 + i * 32, fontSize, Font.COLOR_WHITE, false);
			}
		}
	}

}
