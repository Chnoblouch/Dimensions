package game.item;

import java.awt.image.BufferedImage;

import game.creature.Player;
import game.gfx.Screen;
import game.gui.Slot;
import game.levels.Level;

public class Item {
	
	private static int lastID = 0;
	
	private int id;
	public static final int ID_WOOD = lastID ++;
	public static final int ID_STONE = lastID ++;
	public static final int ID_SLIME = lastID ++;
	public static final int ID_APPLE = lastID ++;
	public static final int ID_WOOD_AXE = lastID ++;
	public static final int ID_WOOD_PICKAXE = lastID ++;
	public static final int ID_WOOD_SHOVEL = lastID ++;
	public static final int ID_WOOD_SWORD = lastID ++;
	public static final int ID_STONE_AXE = lastID ++;
	public static final int ID_STONE_PICKAXE = lastID ++;
	public static final int ID_STONE_SHOVEL = lastID ++;
	public static final int ID_STONE_SWORD = lastID ++;
	public static final int ID_MEAT = lastID ++;
	public static final int ID_WORKBENCH = lastID ++;
	public static final int ID_OVEN = lastID ++;
	public static final int ID_ANVIL = lastID ++;
	public static final int ID_IRON_ORE = lastID ++;
	public static final int ID_IRON_INGOT = lastID ++;
	public static final int ID_IRON_AXE = lastID ++;
	public static final int ID_IRON_PICKAXE = lastID ++;
	public static final int ID_IRON_SHOVEL = lastID ++;
	public static final int ID_IRON_SWORD = lastID ++;
	public static final int ID_IRON_HOE = lastID ++;
	public static final int ID_IRON_HAMMER = lastID ++;
	public static final int ID_IRON_HELMET = lastID ++;
	public static final int ID_IRON_CHESTPLATE = lastID ++;
	public static final int ID_IRON_LEGGINGS = lastID ++;
	public static final int ID_IRON_BOOTS = lastID ++;
	public static final int ID_IRON_SHIELD = lastID ++;
	public static final int ID_FEATHER = lastID ++;
	public static final int ID_EYE_PIECE = 30;
	public static final int ID_BOW = lastID ++;
	public static final int ID_ARROW = lastID ++;
	public static final int ID_TAYKOLOS = lastID ++;
	public static final int ID_FROST_PEARL = lastID ++;
	public static final int ID_ALCHEMY_TABLE = lastID ++;
	public static final int ID_HEALTH_POTION = lastID ++;
	public static final int ID_NIGHT_HERB = lastID ++;
	public static final int ID_SPEED_POTION = lastID ++;
	public static final int ID_MUSHROOM = lastID ++;
	public static final int ID_RED_STONE = lastID ++;
	public static final int ID_BLUE_STONE = lastID ++;
	public static final int ID_SHIMMERING_SLIME = lastID ++;
	public static final int ID_MEAT_COOKED = lastID ++;
	public static final int ID_GOLD_ORE = lastID ++;
	public static final int ID_GOLD_INGOT = lastID ++;
	public static final int ID_GOLD_AXE = lastID ++;
	public static final int ID_GOLD_PICKAXE = lastID ++;
	public static final int ID_GOLD_SHOVEL = lastID ++;
	public static final int ID_GOLD_SWORD = lastID ++;
	public static final int ID_GOLD_HOE = lastID ++;
	public static final int ID_GOLD_HAMMER = lastID ++;
	public static final int ID_GOLD_HELMET = lastID ++;
	public static final int ID_GOLD_CHESTPLATE = lastID ++;
	public static final int ID_GOLD_LEGGINGS = lastID ++;
	public static final int ID_GOLD_BOOTS = lastID ++;
	public static final int ID_GOLD_SHIELD = lastID ++;
	public static final int ID_WIZARD_HAT = lastID ++;
	public static final int ID_WIZARD_ROBE = lastID ++;
	public static final int ID_WIZARD_TROUSERS = lastID ++;
	public static final int ID_WIZARD_BOOTS = lastID ++;
	public static final int ID_CRYSTAL_STAFF = lastID ++;
	
	private int hand = 2;
	public static final int MAIN_HAND = 0;
	public static final int OFF_HAND = 1;
	public static final int BOTH_HANDS = 2;

	private String name;
	private String[] description;
	
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
	
	public void setItemID(int id) {	this.id = id; }
	public int getItemID() { return id; }
	
	public void setHand(int hand) { this.hand = hand; }
	public int getHand() { return hand;	}
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public void setDescription(String... description) { this.description = description; }
	public String[] getDescription() { return description; }
	
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
	
	public void release(double x, double y, Level level, Player player, Slot slot) {}
	public void inHand(Player player) {}
	public void renderInHand(Screen screen, Player player, int hand) {}
	public void renderOnBody(Screen screen, Player player) {}
	public void render(Screen screen, double x, double y, double size, boolean inCam, double alpha, double rot) {}
	public BufferedImage getShadow() { return null; }
}
