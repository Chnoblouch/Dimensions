package game.gui;

import java.util.ArrayList;

import game.Game;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.item.Item;
import game.item.arrows.ItemArrow;
import game.item.axes.ItemIronAxe;
import game.item.boots.ItemIronBoots;
import game.item.bows.ItemBow;
import game.item.chestplates.ItemWizardRobe;
import game.item.craftingstations.ItemAlchemyTable;
import game.item.craftingstations.ItemAnvil;
import game.item.craftingstations.ItemOven;
import game.item.craftingstations.ItemWorkbench;
import game.item.food.ItemApple;
import game.item.food.ItemMeat;
import game.item.helmets.ItemWizardHat;
import game.item.leggings.ItemIronLeggings;
import game.item.magicweapons.ItemCrystalStaff;
import game.item.magicweapons.ItemTaykolos;
import game.item.ores.ItemGoldOre;
import game.item.pickaxes.ItemIronPickaxe;
import game.item.resources.ItemStone;
import game.item.resources.ItemWood;
import game.item.shields.ItemIronShield;
import game.item.special.ItemShimmeringSlime;
import game.item.swords.ItemIronSword;
import game.saving.SaveFile;
import game.utils.DoublePoint;
import game.utils.SafeArrayList;

public class Inventory {
	
	public boolean visible = false;
	public ArrayList<Slot> defaultSlots = new SafeArrayList<>();
	public ArrayList<Slot> slots = new SafeArrayList<>();
	
	public Game game;
	
	private InventoryMenu[] menus = new InventoryMenu[5];
	private int currentMenuID = 0;
	private InventoryMenu currentMenu;
	public HandCraftingMenu handCrafting;
	public WorkbenchCraftingMenu workbenchCrafting;
	public OvenCraftingMenu ovenCrafting;
	public AnvilCraftingMenu anvilCrafting;
	public AlchemyCraftingMenu alchemyCrafting;
	
	private SelectionSlot mainHandItem;
	private SelectionSlot offHandItem;
	
	private SlotArmor armorHelmet;
	private SlotArmor armorChestplate;
	private SlotArmor armorLeggings;
	private SlotArmor armorBoots;
	
	public int x, y;
	public int firstSlotX, firstSlotY;
	
	public Item itemOnMouse;
	public int itemOnMouseCount;
	
	public Inventory(Game game)
	{
		this.game = game;
		
		x = Screen.DEFAULT_WIDTH / 2 - 384;
		y = Screen.DEFAULT_HEIGHT / 2 - 384;
		
		firstSlotX = x + 384 - ((6 * 100) / 2) + 10;
		firstSlotY = y + 384 - ((6 * 100) / 2) + 10;
		
		handCrafting = new HandCraftingMenu(this);
		workbenchCrafting = new WorkbenchCraftingMenu(this);
		ovenCrafting = new OvenCraftingMenu(this);
		anvilCrafting = new AnvilCraftingMenu(this);
		alchemyCrafting = new AlchemyCraftingMenu(this);
		
		menus[0] = handCrafting;
		menus[1] = workbenchCrafting;
		menus[2] = ovenCrafting;
		menus[3] = anvilCrafting;
		menus[4] = alchemyCrafting;
		
		currentMenuID = 0;
		currentMenu = menus[currentMenuID];
		
		mainHandItem = new SelectionSlot(this, 50, Screen.DEFAULT_WIDTH - 120 - 40, 40, Item.MAIN_HAND);
		offHandItem = new SelectionSlot(this, 51, Screen.DEFAULT_WIDTH - 120 - 40, 180, Item.OFF_HAND);
		slots.add(mainHandItem);
		slots.add(offHandItem);
		
		int id = -1;
		
		for(int y = 0; y < 6; y++)
		{
			for(int x = 0; x < 6; x++)
			{		
				id ++;
				
				SlotDefault s = new SlotDefault(this, id, (x * 100) + firstSlotX, (y * 100) + firstSlotY);
				defaultSlots.add(s);
				slots.add(s);
			}
		}
		
		armorHelmet = new SlotArmor(this, 100, x + 768 + 20, firstSlotY, Item.ARMOR_HELMET);
		armorChestplate = new SlotArmor(this, 101, x + 768 + 20, firstSlotY + 100, Item.ARMOR_CHESTPLATE);
		armorLeggings = new SlotArmor(this, 102, x + 768 + 20, firstSlotY + 200, Item.ARMOR_LEGGINGS);
		armorBoots = new SlotArmor(this, 103, x + 768 + 20, firstSlotY + 300, Item.ARMOR_BOOTS);
		
		slots.add(armorHelmet);
		slots.add(armorChestplate);
		slots.add(armorLeggings);
		slots.add(armorBoots);
		
//		armorHelmet.setItem(new ItemIronHelmet(), 1);
//		armorChestplate.setItem(new ItemIronChestplate(), 1);
//		armorBoots.setItem(new ItemIronBoots(), 1);
//		armorLeggings.setItem(new ItemIronLeggings(), 1);
		
//		armorChestplate.setItem(new ItemWizardRobe(), 1);
//		armorHelmet.setItem(new ItemWizardHat(), 1);
		
		fillNextFreeSlot(new ItemWood(), 500);
		fillNextFreeSlot(new ItemStone(), 500);
		fillNextFreeSlot(new ItemGoldOre(), 100);
		fillNextFreeSlot(new ItemMeat(), 50);
		fillNextFreeSlot(new ItemApple(), 50);
		
		fillNextFreeSlot(new ItemIronSword(), 1);
		fillNextFreeSlot(new ItemIronShield(), 1);
		fillNextFreeSlot(new ItemIronPickaxe(), 1);
		fillNextFreeSlot(new ItemIronAxe(), 1);
		
		fillNextFreeSlot(new ItemBow(), 1);
		fillNextFreeSlot(new ItemArrow(), 100);
		
		fillNextFreeSlot(new ItemWorkbench(), 1);
		fillNextFreeSlot(new ItemOven(), 1);
		fillNextFreeSlot(new ItemAnvil(), 1);
		fillNextFreeSlot(new ItemAlchemyTable(), 1);
		
		fillNextFreeSlot(new ItemShimmeringSlime(), 1);
		
		fillNextFreeSlot(new ItemTaykolos(), 1);
		
		mainHandItem.setItem(new ItemCrystalStaff(), 1);
//		mainHandItem.setItem(new ItemIronHoe(), 1);
		offHandItem.setItem(new ItemShimmeringSlime(), 1);
	}
	
	public void changeVisibility(int menu)
	{
		visible = !visible;
		currentMenuID = menu;
		currentMenu = menus[currentMenuID];
		
		if(!visible && isItemOnMouse())
		{
			game.player.drop(itemOnMouse, itemOnMouseCount);
			itemOnMouse = null;
			itemOnMouseCount = 0;
		}
	}
	
	public void setVisible(boolean visible)
	{
		this.visible = visible;
		
		if(!visible && isItemOnMouse())
		{
			game.player.drop(itemOnMouse, itemOnMouseCount);
			itemOnMouse = null;
			itemOnMouseCount = 0;
		}
	}
	
	public void fillNextFreeSlot(Item item, int count)
	{
		for(int i = 0; i < defaultSlots.size(); i++)
		{
			Slot s = defaultSlots.get(i);
			if(!s.isEmpty() && s.getItem().getItemID() == item.getItemID()) 
			{
				s.addItem(count);
				return;
			}
		}
		
		for(int i = 0; i < defaultSlots.size(); i++)
		{
			Slot s = defaultSlots.get(i);
			if(s.isEmpty()) 
			{
				s.setItem(item, count);
				return;
			}
		}
	}
	
	public boolean hasEnough(int itemID, int count)
	{
		return getSlotWithEnough(itemID, count) != null;
	}
	
	public Slot getSlotWithEnough(int itemID, int count)
	{
		for(int i = 0; i < defaultSlots.size(); i++)
		{
			Slot s = defaultSlots.get(i);
			
			if(!s.isEmpty() && s.getItem().getItemID() == itemID && s.getItemCount() >= count)
				return s;
		}
		
		return null;
	}
	
	public boolean canHold(int itemID, int count)
	{
		for(int i = 0; i < defaultSlots.size(); i++)
		{
			Slot s = defaultSlots.get(i);
			
			if(s.isEmpty() || s.getItem().getItemID() == itemID) return true;
		}
		
		return false;
	}
	
	public Slot getSlotForID(int id)
	{
		for(int i = 0; i < slots.size(); i++)
		{
			Slot s = slots.get(i);
			if(s.id == id) return s;
		}
		
		return null;
	}
	
	public void mousePressed(DoublePoint pos, int button)
	{
		for(int i = 0; i < slots.size(); i++)
		{
			Slot s = slots.get(i);
			if(s.isMouseOn()) s.mousePressed(pos, button);
		}
		
		currentMenu.mousePressed(pos, button);
		
//		if(isItemOnMouse() && (pos.x < x - 300 || pos.x > x + 512 + 20 + 80 || pos.y < y || pos.y > y + 512))
//		{
//			game.player.drop(itemOnMouse, itemOnMouseCount);
//			itemOnMouse = null;
//			itemOnMouseCount = 0;
//		}
	}

	public void mouseReleased(DoublePoint pos, int button)
	{
		for(int i = 0; i < slots.size(); i++) slots.get(i).mouseReleased(pos, button);
		currentMenu.mouseReleased(pos, button);
	}
	
	public void render(Screen screen)
	{
		if(visible)
		{
			screen.renderGUI(SpriteSheet.inventory.getSprite(0, 0, 192, 192), x, y, 768, 768, 0, 1);
			currentMenu.render(screen);
			
			for(int i = 0; i < defaultSlots.size(); i++)
			{
				Slot s = defaultSlots.get(i);
				s.render(screen);
			}
			
			armorHelmet.render(screen);
			armorChestplate.render(screen);
			armorLeggings.render(screen);
			armorBoots.render(screen);
			
			for(int i = 0; i < slots.size(); i++)
			{
				Slot s = slots.get(i);
				if(!s.isEmpty() && itemOnMouse == null && s.isMouseOn()) s.renderInfo(screen);
			}
			
			currentMenu.renderInfo(screen);
		}
		
		mainHandItem.render(screen);
		offHandItem.render(screen);
		
		if(visible && game.getMouse() != null)
		{
			int x = (int) game.getMouse().x;
			int y = (int) game.getMouse().y;
			
			if(isItemOnMouse())
			{				
				itemOnMouse.render(screen, x+8-game.screen.x, y+8, 64, false, 0, 1);
				if(itemOnMouseCount > 1) 
					screen.renderFont(itemOnMouseCount+"", 
									  x+80-4-screen.x-(Font.getTextWidth(itemOnMouseCount+"", 32)), 
									  y+80-32, 32, 
									  Font.COLOR_WHITE, 
									  false);
			}
		}
	}
	
	public SaveFile convertToSaveFile()
	{
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < slots.size(); i++)
		{
			Slot s = slots.get(i);
			if(!s.isEmpty()) builder.append(s.id+";"+s.getItem().getClass().getName()+";"+s.getItemCount()+System.lineSeparator());
			else builder.append(s.id+";empty;0"+System.lineSeparator());
		}
		return new SaveFile("inventory", builder.toString());
	}
	
	public Slot getMainHandSlot() { return mainHandItem;}
	public Item getMainHandItem() {	return getMainHandSlot().getItem(); }
	public Slot getOffHandSlot() { return offHandItem; }
	public Item getOffHandItem() { return getOffHandSlot().getItem(); }
	
	public Slot getHelmetSlot() { return armorHelmet; }
	public Item getHelmet() { return armorHelmet.getItem(); }
	public Slot getChestplateSlot() { return armorChestplate; }
	public Item getChestplate() { return armorChestplate.getItem(); }
	public Slot getLeggingsSlot() { return armorLeggings; }
	public Item getLeggings() { return armorLeggings.getItem(); }
	public Slot getBootsSlot() { return armorBoots; }
	public Item getBoots() { return armorBoots.getItem(); }
	
	public boolean isItemOnMouse()
	{
		return itemOnMouse != null && itemOnMouseCount != 0;
	}
}
