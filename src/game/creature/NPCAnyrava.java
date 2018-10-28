package game.creature;

import java.util.Random;

import game.TextLoader;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.gui.Speech;
import game.obj.GameObject;
import game.utils.Hitbox;
import game.utils.HitboxFactory;
import game.utils.TimeCounter;

public class NPCAnyrava 
extends NPC {
	
	public int sx, sy;
	
	private TimeCounter changeWalkTimer;
	
	private boolean seePlayerFirstTime = true;
	
	private Speech classic;
	private Speech greetings;
	private Speech speechWarning;
	private Speech speechAngry;
	private Speech explanation;
	
	private double warningDragonHealth;
	
	private boolean warning = false;
	private boolean angry = false;
	
	public NPCAnyrava()
	{
		changeWalkTimer = new TimeCounter(2500, () ->
		{
			if(walk)
			{
				down = false;
				right = false;
				up = false;
				left = false;
			} else
			{
				int randomDir = new Random().nextInt(4);
				
				switch(randomDir)
				{
					case 0: down = true; break;
					case 1: right = true; break;
					case 2: up = true; break;
					case 3: left = true; break;
				}
			}
			
			changeWalkTimer.reset();
		});
		
		speechWarning = new Speech(TextLoader.getText("speech_anyrava_warning"));
		speechAngry = new Speech(TextLoader.getText("speech_anyrava_angry"));
		explanation = new Speech(TextLoader.getText("speech_anyrava_explanation"));
	}
	
	@Override
	public int getZIndex()
	{
		if(rideOnDragon) return dragon.getZIndex() + 1;
		else return (int) (getY() + 256 - 64 - 1);
	}
	
	private void resetImage()
	{		
		sy = lookDir * 64;
				
//		if(!rideOnDragon)
//		{
//			if(!interact)
//			{
//				if(!walk) sx = 0;
//				else sx = walkSprites[walkStage];
//			} else sx = 320 + (interactStage * 64);
//		} else sx = 576;
		
		if(!rideOnDragon)
		{
			if(!walk) sx = 0;
			else sx = walkSprites[walkStage];
		} else sx = 576;
	}
	
	@Override
	public void interactWith(Player player, boolean mouseOn)
	{
		if(!mouseOn) return;
		
		classic = new Speech(TextLoader.getText("speech_anyrava"+new Random().nextInt(3)));
		level.game.textBox.show(classic);
	}
	
	@Override
	public void render(Screen screen)
	{
		screen.render(SpriteFilter.getShadowStanding(SpriteSheet.anyrava.getSprite(sx, sy, 64, 64)), getX() - 64, getY(), 512, 256, 0, 0.25);
		screen.render(SpriteSheet.anyrava.getSprite(sx, sy, 64, 64), getX(), getY(), 256, 256, 0, invulnerableInvisible ? 0.5 : 1);
		
		if(isMouseOn())
		{
			String name = TextLoader.getText("npc_anyrava");
			String part1 = name.split("/nl")[0];
			String part2 = name.split("/nl")[1];
			
			screen.renderFont(part1, getX() + 128 - (Font.getTextWidth(part1, 32) / 2), getY() - 16, 32, Font.COLOR_WHITE, true);
			screen.renderFont(part2, getX() + 128 - (Font.getTextWidth(part2, 32) / 2), getY() + 16, 32, Font.COLOR_WHITE, true);
		}
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
	
	@Override
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(getX() + 64, getY() + 64, 128, 128);
	}
	
	@Override
	public boolean updateOutside()
	{
		return true;
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return o instanceof Player;
	}
	
	@Override
	public void update(double tpf)
	{
		super.update(tpf);
		
		changeWalkTimer.count(tpf);
		resetImage();
		
		if(level.getID() == 2)
		{
			if(!warning && dragon.getHealth() != dragon.getMaxHealth())
			{
				warning = true;
				warningDragonHealth = dragon.getHealth();
				level.game.textBox.show(speechWarning);
			}
			
			if(warning && !angry && dragon.getHealth() != warningDragonHealth)
			{
				angry = true;
				level.game.textBox.show(speechAngry);
			}
			
			if(!angry)
			{
				if(seePlayerFirstTime)
				{
					seePlayerFirstTime = false;
					
					greetings = new Speech(TextLoader.getText("speech_anyrava_greetings"));
					level.game.textBox.show(greetings);
								
				} else if(!seePlayerFirstTime && level.game.screen.isInside(getClickHitbox()) && rideOnDragon)
				{
					dragon.tryStopMoving = true;
					dragon.newAngle = getAngle(level.player);
					
					if(greetings.isOver())
					{
						dragon.tryStopMoving = false;
						if(canLeaveDragon()) leaveDragon();
						
						if(!rideOnDragon) level.game.textBox.show(explanation);
					}
				}
			}
		}
	}

}
