package game.crafting;

import game.item.Item;
import game.item.arrows.ItemArrow;
import game.item.axes.ItemGoldAxe;
import game.item.axes.ItemIronAxe;
import game.item.axes.ItemStoneAxe;
import game.item.axes.ItemWoodAxe;
import game.item.boots.ItemGoldBoots;
import game.item.boots.ItemIronBoots;
import game.item.bows.ItemBow;
import game.item.chestplates.ItemGoldChestplate;
import game.item.chestplates.ItemIronChestplate;
import game.item.craftingstations.ItemAlchemyTable;
import game.item.craftingstations.ItemAnvil;
import game.item.craftingstations.ItemOven;
import game.item.craftingstations.ItemWorkbench;
import game.item.food.ItemMeat;
import game.item.food.ItemMeatCooked;
import game.item.helmets.ItemGoldHelmet;
import game.item.helmets.ItemIronHelmet;
import game.item.ingots.ItemGoldIngot;
import game.item.ingots.ItemIronIngot;
import game.item.leggings.ItemGoldLeggings;
import game.item.leggings.ItemIronLeggings;
import game.item.magicweapons.ItemTaykolos;
import game.item.monsterdrops.ItemEyePiece;
import game.item.monsterdrops.ItemSlime;
import game.item.ores.ItemGoldOre;
import game.item.ores.ItemIronOre;
import game.item.pickaxes.ItemGoldPickaxe;
import game.item.pickaxes.ItemIronPickaxe;
import game.item.pickaxes.ItemStonePickaxe;
import game.item.pickaxes.ItemWoodPickaxe;
import game.item.potions.ItemHealthPotion;
import game.item.potions.ItemSpeedPotion;
import game.item.resources.ItemBlueStone;
import game.item.resources.ItemFrostPearl;
import game.item.resources.ItemMushroom;
import game.item.resources.ItemNightHerb;
import game.item.resources.ItemRedStone;
import game.item.resources.ItemStone;
import game.item.resources.ItemWood;
import game.item.shields.ItemIronShield;
import game.item.shovels.ItemGoldShovel;
import game.item.shovels.ItemIronShovel;
import game.item.shovels.ItemStoneShovel;
import game.item.shovels.ItemWoodShovel;
import game.item.special.ItemShimmeringSlime;
import game.item.swords.ItemGoldSword;
import game.item.swords.ItemIronSword;
import game.item.swords.ItemStoneSword;
import game.item.swords.ItemWoodSword;
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
		addOvenRecipe(new ItemGoldIngot(), 1, new CraftingRecipePart(new ItemGoldOre(), 1));
		addOvenRecipe(new ItemMeatCooked(), 1, new CraftingRecipePart(new ItemMeat(), 1));
		
		addAnvilRecipe(new ItemIronAxe(), 1, new CraftingRecipePart(new ItemIronIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemIronPickaxe(), 1, new CraftingRecipePart(new ItemIronIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemIronShovel(), 1, new CraftingRecipePart(new ItemIronIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemIronSword(), 1, new CraftingRecipePart(new ItemIronIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemIronHelmet(), 1, new CraftingRecipePart(new ItemIronIngot(), 5));
		addAnvilRecipe(new ItemIronChestplate(), 1, new CraftingRecipePart(new ItemIronIngot(), 10));
		addAnvilRecipe(new ItemIronLeggings(), 1, new CraftingRecipePart(new ItemIronIngot(), 8));
		addAnvilRecipe(new ItemIronBoots(), 1, new CraftingRecipePart(new ItemIronIngot(), 4));
		addAnvilRecipe(new ItemIronShield(), 1, new CraftingRecipePart(new ItemIronIngot(), 5), new CraftingRecipePart(new ItemWood(), 4));
		addAnvilRecipe(new ItemGoldAxe(), 1, new CraftingRecipePart(new ItemGoldIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemGoldPickaxe(), 1, new CraftingRecipePart(new ItemGoldIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemGoldShovel(), 1, new CraftingRecipePart(new ItemGoldIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemGoldSword(), 1, new CraftingRecipePart(new ItemGoldIngot(), 6), new CraftingRecipePart(new ItemWood(), 2));
		addAnvilRecipe(new ItemGoldHelmet(), 1, new CraftingRecipePart(new ItemGoldIngot(), 5));
		addAnvilRecipe(new ItemGoldChestplate(), 1, new CraftingRecipePart(new ItemGoldIngot(), 10));
		addAnvilRecipe(new ItemGoldLeggings(), 1, new CraftingRecipePart(new ItemGoldIngot(), 8));
		addAnvilRecipe(new ItemGoldBoots(), 1, new CraftingRecipePart(new ItemGoldIngot(), 4));
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
