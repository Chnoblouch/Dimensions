package game.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
		
	// Creatures
	public static SpriteSheet player = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/player.png"));
	
	public static SpriteSheet dragon = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/dragon.png"));
	public static SpriteSheet dragonEldenir = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/dragoneldenir.png"));
	
	// Monsters
	public static SpriteSheet slime = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/monsters/slime.png"));
	public static SpriteSheet giantSlime = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/monsters/giantslime.png"));
	public static SpriteSheet lobire = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/monsters/lobire.png"));
	public static SpriteSheet walkingEye = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/monsters/walkingeye.png"));
	public static SpriteSheet rogo = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/monsters/rogo.png"));
	
	// NPCs
	public static SpriteSheet bruno = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/npcs/bruno.png"));
	public static SpriteSheet anyrava = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/npcs/anyrava.png"));
	public static SpriteSheet jessica = new SpriteSheet(SpriteLoader.loadSprite("/img/creatures/npcs/jessica.png"));
	
	// Environment
	public static SpriteSheet grassBlock = new SpriteSheet(SpriteLoader.loadSprite("/img/environment/grassblock.png"));
	public static SpriteSheet darkBlock = new SpriteSheet(SpriteLoader.loadSprite("/img/environment/darkblock.png"));
	public static SpriteSheet snowBlock = new SpriteSheet(SpriteLoader.loadSprite("/img/environment/snowblock.png"));
	
	public static SpriteSheet overworld = new SpriteSheet(SpriteLoader.loadSprite("/img/environment/overworld.png"));
	public static SpriteSheet darkworld = new SpriteSheet(SpriteLoader.loadSprite("/img/environment/darkworld.png"));
	public static SpriteSheet iceworld = new SpriteSheet(SpriteLoader.loadSprite("/img/environment/iceworld.png"));
	
	public static SpriteSheet portals = new SpriteSheet(SpriteLoader.loadSprite("/img/environment/portals.png"));
	
	// Objects
	public static SpriteSheet craftingstations = new SpriteSheet(SpriteLoader.loadSprite("/img/craftingstations.png")); 
	public static SpriteSheet projectiles = new SpriteSheet(SpriteLoader.loadSprite("/img/projectiles.png")); 
	
	// Particles
	public static SpriteSheet particles = new SpriteSheet(SpriteLoader.loadSprite("/img/particles/particles.png"));
	public static SpriteSheet changelevelParticle = new SpriteSheet(SpriteLoader.loadSprite("/img/particles/changelevelparticle.png"));
	
	// GUI
	public static SpriteSheet bars = new SpriteSheet(SpriteLoader.loadSprite("/img/gui/bars.png"));
	public static SpriteSheet inventory = new SpriteSheet(SpriteLoader.loadSprite("/img/gui/inventory.png"));
	public static SpriteSheet textBox = new SpriteSheet(SpriteLoader.loadSprite("/img/gui/textbox.png"));
	public static SpriteSheet itemInfo = new SpriteSheet(SpriteLoader.loadSprite("/img/gui/iteminfo.png"));
	public static SpriteSheet shimmer = new SpriteSheet(SpriteLoader.loadSprite("/img/shimmer.png"));
	public static SpriteSheet cursor = new SpriteSheet(SpriteLoader.loadSprite("/img/cursor.png"));
	
	public static SpriteSheet skyworldbg = new SpriteSheet(SpriteLoader.loadSprite("/img/skyworld.png"));
	
	public static SpriteSheet items = new SpriteSheet(SpriteLoader.loadSprite("/img/items.png"));
	
	public static SpriteSheet font = new SpriteSheet(SpriteLoader.loadSprite("/img/font/white.png"));
	
	// In Hand
	public static SpriteSheet appleInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/apple.png"));
	public static SpriteSheet meatInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/meat.png"));
	public static SpriteSheet woodSwordInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/wood_sword.png"));
	public static SpriteSheet stoneSwordInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/stone_sword.png"));
	public static SpriteSheet ironSwordInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/iron_sword.png"));
	public static SpriteSheet taykolosInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/taykolos.png"));
	public static SpriteSheet ironShieldInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/iron_shield.png"));
	
	// Armor
	public static SpriteSheet armorIronHelmet = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/iron_helmet.png"));
	public static SpriteSheet armorIronChestplate = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/iron_chestplate.png"));
	public static SpriteSheet armorIronLeggings = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/iron_leggings.png"));
	public static SpriteSheet armorIronBoots = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/iron_boots.png"));
	
	// Random
	public static SpriteSheet exclamationmarks = new SpriteSheet(SpriteLoader.loadSprite("/img/exclamationmarks.png"));
	
	private BufferedImage img;
	
	public SpriteSheet(BufferedImage img)
	{
		this.img = img;
	}
	
	public BufferedImage toImage()
	{	
		return img;
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height)
	{
		return img.getSubimage(x, y, width, height);
	}

}
