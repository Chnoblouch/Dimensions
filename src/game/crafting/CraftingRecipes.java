package game.crafting;

import game.item.Item;
import game.item.ItemAlchemyTable;
import game.item.ItemAnvil;
import game.item.ItemArrow;
import game.item.ItemBlueStone;
import game.item.ItemBow;
import game.item.ItemEyePiece;
import game.item.ItemFrostPearl;
import game.item.ItemHealthPotion;
import game.item.ItemIronAxe;
import game.item.ItemIronBoots;
import game.item.ItemIronChestplate;
import game.item.ItemIronHelmet;
import game.item.ItemIronIngot;
import game.item.ItemIronLeggings;
import game.item.ItemIronOre;
import game.item.ItemIronPickaxe;
import game.item.ItemIronShield;
import game.item.ItemIronShovel;
import game.item.ItemIronSword;
import game.item.ItemMushroom;
import game.item.ItemNightHerb;
import game.item.ItemOven;
import game.item.ItemRedStone;
import game.item.ItemShimmeringSlime;
import game.item.ItemSlime;
import game.item.ItemSpeedPotion;
import game.item.ItemStone;
import game.item.ItemStoneAxe;
import game.item.ItemStonePickaxe;
import game.item.ItemStoneShovel;
import game.item.ItemStoneSword;
import game.item.ItemTaykolos;
import game.item.ItemWood;
import game.item.ItemWoodAxe;
import game.item.ItemWoodPickaxe;
import game.item.ItemWoodShovel;
import game.item.ItemWoodSword;
import game.item.ItemWorkbench;
import game.utils.SafeArrayList;

public class CraftingRecipes {
	
	private static SafeArrayList<CraftingRecipe> handRecipes = new SafeArrayList<>();
	private static SafeArrayList<CraftingRecipe> workbenchRecipes = new SafeArrayList<>();
	private static SafeArrayList<CraftingRecipe> ovenRecipes = new SafeArrayList<>();
	private static SafeArrayList<CraftingRecipe> anvilRecipes = new SafeArrayList<>();
	private static SafeArrayList<CraftingRecipe> alchemyRecipes = new SafeArrayList<>();
	
	static 
	{
		addHandRecipe(new ItemWorkbench(), 1, new CraftingRecipePart(new ItemWood(), 12));
		
		addWorkbenchRecipe(new ItemWoodAxe(), 1, new CraftingRecipePart(new ItemWood(), 8));
		addWorkbenchRecipe(new ItemWoodPickaxe(), 1, new CraftingRecipePart(new ItemWood(), 8));
		addWorkbenchRecipe(new ItemWoodShovel(), 1, new CraftingRecipePart(new ItemWood(), 8));
		addWorkbenchRecipe(new ItemWoodSword(), 1, new CraftingRecipePart(new ItemWood(), 8));
		addWorkbenchRecipe(new ItemStoneAxe(), 1, new CraftingRecipePart(new ItemStone(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addWorkbenchRecipe(new ItemStonePickaxe(), 1, new CraftingRecipePart(new ItemStone(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addWorkbenchRecipe(new ItemStoneShovel(), 1, new CraftingRecipePart(new ItemStone(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addWorkbenchRecipe(new ItemStoneSword(), 1, new CraftingRecipePart(new ItemStone(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addWorkbenchRecipe(new ItemOven(), 1, new CraftingRecipePart(new ItemStone(), 18));
		addWorkbenchRecipe(new ItemAnvil(), 1, new CraftingRecipePart(new ItemIronIngot(), 12));
		addWorkbenchRecipe(new ItemAlchemyTable(), 1, new CraftingRecipePart(new ItemWood(), 14), 
													  new CraftingRecipePart(new ItemEyePiece(), 4),
													  new CraftingRecipePart(new ItemSlime(), 6));
		addWorkbenchRecipe(new ItemBow(), 1, new CraftingRecipePart(new ItemWood(), 8));
		addWorkbenchRecipe(new ItemArrow(), 1, new CraftingRecipePart(new ItemWood(), 2), new CraftingRecipePart(new ItemStone(), 1));
		addWorkbenchRecipe(new ItemShimmeringSlime(), 1, new CraftingRecipePart(new ItemSlime(), 10),
														 new CraftingRecipePart(new ItemRedStone(), 8),
														 new CraftingRecipePart(new ItemBlueStone(), 8),
														 new CraftingRecipePart(new ItemMushroom(), 3));
		
		addOvenRecipe(new ItemIronIngot(), 1, new CraftingRecipePart(new ItemIronOre(), 1));
		
		addAnvilRecipe(new ItemIronAxe(), 1, new CraftingRecipePart(new ItemIronIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemIronPickaxe(), 1, new CraftingRecipePart(new ItemIronIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemIronShovel(), 1, new CraftingRecipePart(new ItemIronIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemIronSword(), 1, new CraftingRecipePart(new ItemIronIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemIronHelmet(), 1, new CraftingRecipePart(new ItemIronIngot(), 5));
		addAnvilRecipe(new ItemIronChestplate(), 1, new CraftingRecipePart(new ItemIronIngot(), 10));
		addAnvilRecipe(new ItemIronLeggings(), 1, new CraftingRecipePart(new ItemIronIngot(), 8));
		addAnvilRecipe(new ItemIronBoots(), 1, new CraftingRecipePart(new ItemIronIngot(), 4));
		addAnvilRecipe(new ItemIronShield(), 1, new CraftingRecipePart(new ItemIronIngot(), 5), new CraftingRecipePart(new ItemWood(), 4));
		addAnvilRecipe(new ItemTaykolos(), 1, new CraftingRecipePart(new ItemRedStone(), 10), 
											  new CraftingRecipePart(new ItemBlueStone(), 10),
											  new CraftingRecipePart(new ItemIronIngot(), 3));
		
		addAlchemyRecipe(new ItemHealthPotion(), 1, new CraftingRecipePart(new ItemSlime(), 4), 
													 new CraftingRecipePart(new ItemEyePiece(), 2));
		addAlchemyRecipe(new ItemSpeedPotion(), 1, new CraftingRecipePart(new ItemNightHerb(), 6), 
				 								     new CraftingRecipePart(new ItemFrostPearl(), 1));
	}
	
	private static void addHandRecipe(Item result, int resultCount, CraftingRecipePart... parts)
	{
		handRecipes.add(new CraftingRecipe(result, resultCount, parts));
		workbenchRecipes.add(new CraftingRecipe(result, resultCount, parts));
		ovenRecipes.add(new CraftingRecipe(result, resultCount, parts));
		anvilRecipes.add(new CraftingRecipe(result, resultCount, parts));
		alchemyRecipes.add(new CraftingRecipe(result, resultCount, parts));
	}
	
	private static void addWorkbenchRecipe(Item result, int resultCount, CraftingRecipePart... parts)
	{
		workbenchRecipes.add(new CraftingRecipe(result, resultCount, parts));
	}
	
	private static void addOvenRecipe(Item result, int resultCount, CraftingRecipePart... parts)
	{
		ovenRecipes.add(new CraftingRecipe(result, resultCount, parts));
	}
	
	private static void addAnvilRecipe(Item result, int resultCount, CraftingRecipePart... parts)
	{
		anvilRecipes.add(new CraftingRecipe(result, resultCount, parts));
	}
	
	private static void addAlchemyRecipe(Item result, int resultCount, CraftingRecipePart... parts)
	{
		alchemyRecipes.add(new CraftingRecipe(result, resultCount, parts));
	}
	
	public static SafeArrayList<CraftingRecipe> getHandRecipes()
	{
		return handRecipes;
	}
	
	public static SafeArrayList<CraftingRecipe> getWorkbenchRecipes()
	{
		return workbenchRecipes;
	}
	
	public static SafeArrayList<CraftingRecipe> getOvenRecipes()
	{
		return ovenRecipes;
	}
	
	public static SafeArrayList<CraftingRecipe> getAnvilRecipes()
	{
		return anvilRecipes;
	}
	
	public static SafeArrayList<CraftingRecipe> getAlchemyRecipes()
	{
		return alchemyRecipes;
	}

}
