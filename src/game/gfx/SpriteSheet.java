package game.gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

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
	
	public static SpriteSheet farmland = new SpriteSheet(SpriteLoader.loadSprite("/img/environment/farmland.png"));
	
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
	public static SpriteSheet deathScreen = new SpriteSheet(SpriteLoader.loadSprite("/img/gui/deathScreen.png"));
	
	public static SpriteSheet skyworldbg = new SpriteSheet(SpriteLoader.loadSprite("/img/skyworld.png"));
	
	public static SpriteSheet items = new SpriteSheet(SpriteLoader.loadSprite("/img/items.png"));
	
	// Font
	public static SpriteSheet fontWhite = new SpriteSheet(SpriteLoader.loadSprite("/img/font/white.png"));
	public static SpriteSheet fontRed = new SpriteSheet(SpriteLoader.loadSprite("/img/font/red.png"));
	
	// In Hand
	public static SpriteSheet ironAxeInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/axe/iron.png"));
	
	public static SpriteSheet woodSwordInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/sword/wood.png"));
	public static SpriteSheet stoneSwordInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/sword/stone.png"));
	public static SpriteSheet ironSwordInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/sword/iron.png"));
	public static SpriteSheet goldSwordInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/sword/gold.png"));
	
	public static SpriteSheet appleInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/food/apple.png"));
	public static SpriteSheet meatInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/food/meat.png"));
	public static SpriteSheet meatCookedInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/food/meat_cooked.png"));
	
	public static SpriteSheet ironShieldInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/shield/iron.png"));
	
	public static SpriteSheet bowInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/bow/bow.png"));
	
	public static SpriteSheet taykolosInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/magic/taykolos.png"));
	public static SpriteSheet crystalStaffInHand = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/items/magic/crystal_staff.png"));
	
	// Armor
	public static SpriteSheet ironHelmetOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/helmet/iron.png"));
	public static SpriteSheet ironChestplateOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/chestplate/iron.png"));
	public static SpriteSheet ironLeggingsOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/leggings/iron.png"));
	public static SpriteSheet ironBootsOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/boots/iron.png"));
	
	public static SpriteSheet goldHelmetOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/helmet/gold.png"));
	public static SpriteSheet goldChestplateOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/chestplate/gold.png"));
	public static SpriteSheet goldLeggingsOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/leggings/gold.png"));
	public static SpriteSheet goldBootsOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/boots/gold.png"));
	
	public static SpriteSheet wizardHatOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/helmet/wizard.png"));
	public static SpriteSheet wizardRobeOnBody = new SpriteSheet(SpriteLoader.loadSprite("/img/overlays/armor/chestplate/wizard.png"));
	
	// Random
	public static SpriteSheet exclamationmarks = new SpriteSheet(SpriteLoader.loadSprite("/img/exclamationmarks.png"));
	
	private BufferedImage img;
	
	private HashMap<String, BufferedImage> cache = new HashMap<>();
	
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
		return cache.computeIfAbsent(x + "," + y + "," + width + "," + height, key -> img.getSubimage(x, y, width, height));
	}

}
