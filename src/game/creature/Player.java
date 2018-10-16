package game.creature;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Random;

import game.Game;
import game.effects.Effect;
import game.effects.EffectHandling;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Inventory;
import game.gui.InventoryMenu;
import game.item.Item;
import game.obj.Drop;
import game.obj.GameObject;
import game.projectiles.Slimeball;
import game.saving.SaveInformation;
import game.saving.SaveManager;
import game.utils.Angles;
import game.utils.DoublePoint;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.Time;
import game.utils.TimeCounter;

public class Player
extends Creature {
	
	private boolean right, left, up, down, walk;
	private int lookDir = 0;
	private double speed = 12;
	
	private TimeCounter walkTimer;
	private int walkStage;
	private int[] walkSprites = {64, 128, 64, 0, 192, 256, 192, 0};
	
	private boolean interact = false;
	private int interactStage;
	private Item interactItem;
	private TimeCounter interactTimer;
	
	private boolean eat = false;
	private int eatStage;
	private int biteCounter = 0;
	private Item eatItem;
	private TimeCounter eatTimer;
	
	private boolean protect = false;
	private Item protectItem;
	
	private boolean impressed = false;
	private double exclamationMark1 = 0;
	private double exclamationMark2 = 0;
	
	private Game game;
	
	public Inventory inventory;
	
	public boolean thrownSomething = false;
	
	private EffectHandling effectHandling;
	
	public int sx, sy;

	public Player(Game game)
	{		
		this.game = game;
		this.inventory = game.inventory;
		
		setInvulnerableTime(1000);
		setMaxHealth(20);
		
		effectHandling = new EffectHandling();
				
		walkTimer = new TimeCounter(75, () -> 
		{
			walkStage ++;
			if(walkStage >= walkSprites.length) walkStage = 0;
			
			walkTimer.reset();
		});
		
		interactTimer = new TimeCounter(100, () -> {
			interactStage ++;
			if(interactStage >= 4) 
			{
				interactStage = 0;
				interact = false;
			}
			
			interactTimer.reset();
		});
		
		eatTimer = new TimeCounter(150, () -> {
			if(eatStage == 0) eatStage = 1;
			else if(eatStage == 1) 
			{
				eatStage = 0;
				biteCounter ++;
				Sounds.eat.play(1, 0);
			}
			
			if(biteCounter >= 5)
			{
				eat = false;
				eatStage = 0;
				biteCounter = 0;
			}
			
			eatTimer.reset();
		});
	}
	
	@Override
	public boolean doSave()
	{
		return false;
	}
	
	@Override
	public boolean inCollisionSpace()
	{
		return true;
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(getX() + 128 - 40, getY() + 128 + 32, 80, 32);
	}
	
	private int nextHurtSound = new Random().nextInt(3);
	
	@Override
	public void damage(double h, GameObject attacker)
	{		
		if(protect && attacker.getHitbox().intersects(getProtectionArea())) 
		{
			if(attacker instanceof Creature) ((Creature) attacker).knockback(getAngle(attacker), 30);
		}
		else super.damage(h, attacker);
	}
	
	@Override
	public void getDamage(GameObject attacker)
	{		
		switch (nextHurtSound) 
		{
			case 0: 
			{
				Sounds.hurt0.play(1, 0);
				nextHurtSound = new Random().nextBoolean() ? 1 : 2;
			} break;
			
			case 1:
			{
				Sounds.hurt1.play(1, 0);
				nextHurtSound = new Random().nextBoolean() ? 0 : 2;
			} break;
			
			case 2: 
			{
				Sounds.hurt2.play(1, 0);
				nextHurtSound = new Random().nextBoolean() ? 0 : 1;
			} break;
		}
	}
	
	@Override
	public void die()
	{
		game.changeLevel(0);
		setHealth(20);
	}
	
	public Hitbox getInteractionArea()
	{		
		double size = 192;
		
		double x = getHitbox().x;
		double y = getHitbox().y;
		double w = getHitbox().w;
		double h = getHitbox().h;
		
		switch (lookDir) 
		{
			case 0: return HitboxFactory.create(x + (w / 2) - (size / 2), y + h, size, size);
			case 1: return HitboxFactory.create(x + w, y + (h / 2) - (size / 2), size, size);
			case 2: return HitboxFactory.create(x + (w / 2) - (size / 2), y - size, size, size);
			case 3: return HitboxFactory.create(x - size, y + (h / 2) - (size / 2), size, size);
			default: return null;
		}
	}
	
	public Hitbox getProtectionArea()
	{
		double size = 64;
		
		double x = getHitbox().x;
		double y = getHitbox().y;
		double w = getHitbox().w;
		double h = getHitbox().h;
		
		switch (lookDir) 
		{
			case 0: return HitboxFactory.create(x + (w / 2) - (size / 2), y + h, size, size);
			case 1: return HitboxFactory.create(x + w, y + (h / 2) - (size / 2), size, size);
			case 2: return HitboxFactory.create(x + (w / 2) - (size / 2), y - size, size, size);
			case 3: return HitboxFactory.create(x - size, y + (h / 2) - (size / 2), size, size);
			default: return null;
		}
	}
	
	private void resetImage()
	{		
		sy = lookDir * 64;
				
		if(!rideOnDragon)
		{
			if(!protect)
			{
				if(!eat)
				{
					if(!interact)
					{
						if(!walk) sx = 0;
						else sx = walkSprites[walkStage];
					} else sx = 320 + (interactStage * 64);
				} else sx = 640 + (eatStage * 64);
			} else sx = 768;
		} else sx = 576;
	}
	
	public void interact(double x, double y, Item item)
	{
		if(interact) return;
		
		interact = true;
		interactTimer.reset();
		interactItem = item;
		
		for(int i = 0; i < level.objects.size(); i++)
		{
			GameObject o = level.objects.get(i);
			if(o.isMouseInside(x, y) && o.getClickHitbox().intersects(getInteractionArea()) && o != this) o.interactWith(this);
		} 
	}
	
	public void eat(Item item)
	{
		if(eat || protect) return;
		
		eat = true;
		eatItem = item;
		eatTimer.reset();
	}
	
	public void protect(Item item)
	{
		if(protect || eat) return;
		
		protect = true;
		protectItem = item;
	}
	
	public void stopProtecting()
	{
		protect = false;
	}
	
	public void impressed()
	{
		impressed = true;
		exclamationMark1 = 1;
		exclamationMark2 = 0;
	}

	public void effect(Effect effect)
	{
		effectHandling.addEffect(effect);
	}
	
	public void drop(Item item, int count)
	{
		Drop drop = new Drop(item, count);
		drop.setPosition(getX() + 128 - 32, getY() + 256);
		level.addObject(drop);
	}
	
	public double getAttackDamage()
	{
		if(!inventory.getMainHandSlot().isEmpty()) return inventory.getMainHandItem().getAttackDamage();
		return 1;
	}

	public double getWoodDamage()
	{		
		if(!inventory.getMainHandSlot().isEmpty()) return inventory.getMainHandItem().getWoodDamage();
		return 1; 
	}
	
	public double getStoneDamage()
	{
		if(!inventory.getMainHandSlot().isEmpty()) return inventory.getMainHandItem().getStoneDamage();
		return 1;
	}
	
	@Override
	public double getProtection()
	{
		double protection = 0;
		if(!inventory.getHelmetSlot().isEmpty()) protection += inventory.getHelmet().getProtection();
		if(!inventory.getChestplateSlot().isEmpty()) protection += inventory.getChestplate().getProtection();
		if(!inventory.getLeggingsSlot().isEmpty()) protection += inventory.getLeggings().getProtection();
		if(!inventory.getBootsSlot().isEmpty()) protection += inventory.getBoots().getProtection();
		
		return protection;
	}
	
	public double getAngleToMouse()
	{
		return Angles.getAngle(getX() - game.screen.camX + 128, getY() - game.screen.camY + 128, game.getMouse().x, game.getMouse().y);
	}
	
	@Override
	public void render(Screen screen)
	{
		screen.render(SpriteFilter.getShadowStanding(SpriteSheet.player.getSprite(sx, sy, 64, 64)), getX() - 64, getY(), 512, 256, 0, 0.25);
//		screen.render(SpriteSheet.shadows.getSprite(0, 0, 16, 16), getX() + 128 - 32, getY() + 160, 64, 64, 0, 0.75);
		screen.render(SpriteSheet.player.getSprite(sx, sy, 64, 64), getX(), getY(), 256, 256, 0, invulnerableInvisible ? 0.5 : 1);
			
		if(impressed)
		{
			screen.render(SpriteSheet.exclamationmarks.getSprite(0, 0, 4, 16), getX() + 168, getY() + 8, 16, 64, 20, exclamationMark1);
			screen.render(SpriteSheet.exclamationmarks.getSprite(4, 0, 4, 16), getX() + 200, getY() + 24, 16, 64, 40, exclamationMark2);
		}
		
		for(int i = 0; i < effectHandling.getEffects().size(); i++)
		{
			Effect e = effectHandling.getEffects().get(i);
			String txt = e.getName() + ": " + (int) Time.ticksInSeconds(e.getDurationLeft()) + " Sekunden";
			
			screen.renderFont(txt, getX() + 128 - (Font.getTextWidth(txt, 32) / 2), getY() - (i * 32), 32, Font.COLOR_WHITE, true);
		}
		
		if(!inventory.getHelmetSlot().isEmpty()) inventory.getHelmet().renderOnBody(screen, this);
		if(!inventory.getChestplateSlot().isEmpty()) inventory.getChestplate().renderOnBody(screen, this);
		if(!inventory.getLeggingsSlot().isEmpty()) inventory.getLeggings().renderOnBody(screen, this);
		if(!inventory.getBootsSlot().isEmpty()) inventory.getBoots().renderOnBody(screen, this);
		
		if(lookDir != 3)
		{
			if(inventory.getOffHandItem() != null)
				inventory.getOffHandItem().renderInHand(screen, this, Item.MAIN_HAND);
		}
		
		if(!interact && !eat && inventory.getMainHandItem() != null)
			inventory.getMainHandItem().renderInHand(screen, this, Item.MAIN_HAND);
		else if(interact && interactItem != null)
			interactItem.renderInHand(screen, this, Item.MAIN_HAND);
		else if(eat && eatItem != null)
			eatItem.renderInHand(screen, this, Item.MAIN_HAND);
		
		if(lookDir == 3)
		{
			if(inventory.getOffHandItem() != null)
				inventory.getOffHandItem().renderInHand(screen, this, Item.MAIN_HAND);
		}
			
//		if(inventory.getOffHandItem() != null) inventory.getOffHandItem().renderInHand(screen, this, Item.OFF_HAND);
		
//		screen.renderHitbox(getHitbox());
//		screen.renderHitbox(getInteractionArea());
	}
	
	@Override
	public int getZIndex()
	{
		if(rideOnDragon) return dragon.getZIndex() + 1;
		else return (int) (getY() + 256 - 64 - 1);
	}
	
	private int creature = 3;
	
	public void keyPressed(int key)
	{
		if(key == KeyEvent.VK_W) up = true;
		if(key == KeyEvent.VK_A) left = true;
		if(key == KeyEvent.VK_S) down = true;  
		if(key == KeyEvent.VK_D) right = true;
		if(key == KeyEvent.VK_E) inventory.changeVisibility(InventoryMenu.MENU_HAND_CRAFTING);
		if(key == KeyEvent.VK_SHIFT)
		{
			if(rideOnDragon) leaveDragon();
		}
		
		if(key == KeyEvent.VK_ENTER) SaveManager.save(SaveInformation.convertGame(game));
		if(key == KeyEvent.VK_C) setPosition(level.getPlayerSpawn().x, level.getPlayerSpawn().y);
		
		if(key == KeyEvent.VK_RIGHT) creature ++;
		if(key == KeyEvent.VK_LEFT) creature --;
		
		if(key == KeyEvent.VK_Q && !inventory.getMainHandSlot().isEmpty())
		{
			drop(inventory.getMainHandItem(), inventory.getMainHandSlot().getItemCount());
			inventory.getMainHandSlot().clear();
		}
		
		if(key == KeyEvent.VK_SPACE)
		{
			if(!rideOnDragon)
			{
//				Creature c = null;
//				
//				switch (creature) {
//					case 0: c = new MonsterSlime(); break;
//					case 1: c = new MonsterLobire(); break;
//					case 2: c = new MonsterWalkingEye(); break;
//					case 3: c = new MonsterRogo(); break;
//					case 4: c = new Dragon(); break;
//				}
//				
//				c.setPosition(getX(), getY() + 200);
//				level.addObject(c);
				
			} else dragon.shootFireball();
		}
	}
	
	public void keyReleased(int key)
	{
		if(key == KeyEvent.VK_W) up = false;
		if(key == KeyEvent.VK_A) left = false;
		if(key == KeyEvent.VK_S) down = false;
		if(key == KeyEvent.VK_D) right = false;
	}
	
	public void mousePressed(DoublePoint pos, int button)
	{
		double xInCam = pos.x + level.game.screen.camX;
		double yInCam = pos.y + level.game.screen.camY;
		
		if(button == 1) 
		{
			if(!inventory.getMainHandSlot().isEmpty()) inventory.getMainHandItem().use(xInCam, yInCam, level, this, inventory.getMainHandSlot());
			else interact(xInCam, yInCam, inventory.getMainHandItem());
		} else if(button == 3)
		{			
			if(!inventory.getOffHandSlot().isEmpty()) inventory.getOffHandItem().use(xInCam, yInCam, level, this, inventory.getOffHandSlot());
			else interact(xInCam, yInCam, inventory.getOffHandItem());
		}
	}

	public void mouseReleased(DoublePoint pos, int button)
	{
		if(button == 1) 
		{
			if(!inventory.getMainHandSlot().isEmpty()) inventory.getMainHandItem().release(this);
		} else if(button == 3)
		{			
			if(!inventory.getOffHandSlot().isEmpty()) inventory.getOffHandItem().release(this);
		}
	}
	
	@Override
	public void update(double tpf)
	{		
		super.update(tpf);
		
		speed = 12;
		if(effectHandling.hasEffect(Effect.ID_SPEED)) speed = 18;
		
		effectHandling.updateEffects(tpf);
		
		if(!rideOnDragon && !eat)
		{
			if(right) 
			{
				if(!protect) move(speed * tpf, 0);
				lookDir = 1;
			}
			else if(left) 
			{
				if(!protect) move(-speed * tpf, 0);
				lookDir = 3;
			}
			
			if(up) 
			{
				if(!protect) move(0, -speed * tpf);
				lookDir = 2;
			}
			else if(down) 
			{
				if(!protect) move(0, speed * tpf);
				lookDir = 0;
			}
		} else if(rideOnDragon)
		{
			setPosition(dragon.getX() + 384 - 128 + (getMoveDirection(dragon.angle).x * 32), 
						dragon.getY() + 384 - 128 + (getMoveDirection(dragon.angle).y * 32));
			
			if((dragon.angle > 315 && dragon.angle <= 360) || (dragon.angle > 0 && dragon.angle <= 45)) lookDir = 2;
			else if(dragon.angle > 45 && dragon.angle <= 135) lookDir = 1;
			else if(dragon.angle > 135 && dragon.angle <= 225) lookDir = 0;
			else if(dragon.angle > 225 && dragon.angle <= 315) lookDir = 3;
		}
		
		if(impressed)
		{
			if(exclamationMark1 > 0) exclamationMark1 -= 0.015 * tpf;
			
			if(exclamationMark1 <= 0.95 && exclamationMark2 == 0) exclamationMark2 = 1.0;
			if(exclamationMark1 <= 0.95 && exclamationMark2 > 0) exclamationMark2 -= 0.015 * tpf;
			
			if(exclamationMark1 <= 0.95 && exclamationMark2 <= 0) impressed = false;
		}
				
		walk = left || right || up || down;
		
		if(walk) walkTimer.count(tpf);
		if(interact) interactTimer.count(tpf);
		if(eat) eatTimer.count(tpf);
				
//		level.cam.lookAt(center().x, center().y);
		
		resetImage();
	}
	
}
