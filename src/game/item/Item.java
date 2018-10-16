package game.item;

import java.awt.image.BufferedImage;

import game.creature.Player;
import game.gfx.Screen;
import game.gui.Slot;
import game.levels.Level;

public class Item {
	
	private int id;
	public static final int ID_WOOD = 0;
	public static final int ID_STONE = 1;
	public static final int ID_SLIME = 2;
	public static final int ID_APPLE = 3;
	public static final int ID_WOOD_AXE = 4;
	public static final int ID_WOOD_PICKAXE = 5;
	public static final int ID_WOOD_SHOVEL = 6;
	public static final int ID_WOOD_SWORD = 7;
	public static final int ID_STONE_AXE = 8;
	public static final int ID_STONE_PICKAXE = 9;
	public static final int ID_STONE_SHOVEL = 10;
	public static final int ID_STONE_SWORD = 11;
	public static final int ID_MEAT = 12;
	public static final int ID_WORKBENCH = 13;
	public static final int ID_OVEN = 14;
	public static final int ID_ANVIL = 15;
	public static final int ID_IRON_ORE = 16;
	public static final int ID_IRON_INGOT = 17;
	public static final int ID_IRON_AXE = 18;
	public static final int ID_IRON_PICKAXE = 19;
	public static final int ID_IRON_SHOVEL = 20;
	public static final int ID_IRON_SWORD = 21;
	public static final int ID_FEATHER = 22;
	public static final int ID_EYE_PIECE = 23;
	public static final int ID_IRON_HELMET = 24;
	public static final int ID_IRON_CHESTPLATE = 25;
	public static final int ID_IRON_LEGGINGS = 26;
	public static final int ID_IRON_BOOTS = 27;
	public static final int ID_IRON_SHIELD = 28;
	public static final int ID_BOW = 29;
	public static final int ID_ARROW = 30;
	public static final int ID_TAYKOLOS = 31;
	public static final int ID_FROST_PEARL = 32;
	public static final int ID_ALCHEMY_TABLE = 33;
	public static final int ID_HEALTH_POTION = 34;
	public static final int ID_NIGHT_HERB = 35;
	public static final int ID_SPEED_POTION = 36;
	public static final int ID_MUSHROOM = 37;
	public static final int ID_RED_STONE = 38;
	public static final int ID_BLUE_STONE = 39;
	public static final int ID_SHIMMERING_SLIME = 40;
	
	private int hand = 2;
	public static final int MAIN_HAND = 0;
	public static final int OFF_HAND = 1;
	public static final int BOTH_HANDS = 2;

	private String name;
	private String description;
	
	private double attackDmg = 1;
	private double woodDmg = 1;
	private double stoneDmg = 1;
	private double protection = 0;
	
	private boolean armor = false;
	private int armorID = 0;
	public static final int ARMOR_HELMET = 0;
	public static final int ARMOR_CHESTPLATE = 1;
	public static final int ARMOR_LEGGINGS = 2;
	public static final int ARMOR_BOOTS = 3;
	
	public Item()
	{
		
	}
	
	public void setItemID(int id) {	this.id = id; }
	public int getItemID() { return id; }
	
	public void setHand(int hand) { this.hand = hand; }
	public int getHand() { return hand;	}
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public void setDescription(String description) { this.description = description; }
	public String getDescription() { return description; }
	
	public void setAttackDamage(double dmg) { this.attackDmg = dmg; }
	public double getAttackDamage() { return attackDmg; }
	public void setWoodDamage(double dmg) { this.woodDmg = dmg; }
	public double getWoodDamage() { return woodDmg; }
	public void setStoneDamage(double dmg) { this.stoneDmg = dmg; }
	public double getStoneDamage() { return stoneDmg; }
	public void setProtection(double protection) { this.protection = protection; }
	public double getProtection() { return protection; }
	
	public void setArmor(boolean armor) { this.armor = armor; }
	public boolean isArmor() { return armor; }
	public void setArmorID(int id) { this.armorID = id; }
	public int getArmorID() { return armorID; }
	
	public void use(double x, double y, Level level, Player player, Slot slot) 
	{
		player.interact(x, y, this);
	}
	
	public void release(Player player) {}
	public void inHand(Player player) {}
	public void renderInHand(Screen screen, Player player, int hand) {}
	public void renderOnBody(Screen screen, Player player) {}
	public void render(Screen screen, double x, double y, double size, boolean inCam, double alpha, double rot) {}
	public BufferedImage getShadow() { return null; }
}
