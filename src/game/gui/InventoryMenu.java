package game.gui;

import java.awt.Point;

import game.gfx.Screen;
import game.utils.DoublePoint;

public class InventoryMenu {
	
	public Inventory inventory;
	
	private int id;
	public static final int MENU_HAND_CRAFTING = 0;
	public static final int MENU_WORKBENCH_CRAFTING = 1;
	public static final int MENU_OVEN_CRAFTING = 2;
	public static final int MENU_ANVIL_CRAFTING = 3;
	public static final int MENU_ALCHEMY_CRAFTING = 4;
	
	public InventoryMenu(Inventory inventory)
	{
		this.inventory = inventory;
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	public void setMenuID(int id)
	{
		this.id = id;
	}
	
	public int getMenuID()
	{
		return id;
	}
	
	public void mousePressed(DoublePoint pos, int button) {}
	public void mouseReleased(DoublePoint pos, int button) {}
	public void render(Screen screen) {}
}
