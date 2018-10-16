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

public class NPCJessica 
extends NPC {
	
	public int sx, sy;
	
	private TimeCounter changeWalkTimer;
	
	private Speech explanation;
	
	private boolean attack = false;
	private int attackStage = 0;
	private TimeCounter attackTimer;
	private TimeCounter startAttackingTimer;
	
	public NPCJessica()
	{
		changeWalkTimer = new TimeCounter(2000, () ->
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
		
		attackTimer = new TimeCounter(100, () ->
		{
			attackStage ++;
			if(attackStage >= 4) 
			{
				attackStage = 0;
				attack = false;
			}
			
			attackTimer.reset();
		});
		
		startAttackingTimer = new TimeCounter(1000, () -> 
		{
			attack();
			startAttackingTimer.reset();
		});
		
		explanation = new Speech(TextLoader.getText("speech_jessica_explanation"));
	}
	
	private void attack()
	{
		if(attack) return;
		
		attack = true;
		attackStage = 0;
		changeWalkTimer.reset();
		attackTimer.reset();
		
		down = false;
		right = false;
		up = false;
		left = false;
		startAttackingTimer.setTime(2000 + new Random().nextInt(3000 + 1));
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
			if(!attack)
			{
				if(!walk) sx = 0;
				else sx = walkSprites[walkStage];
			} else sx = 320 + (attackStage * 64);
//		} else sx = 576;
	}
	
	@Override
	public void interactWith(Player player)
	{
		if(explanation.isOver()) level.game.textBox.show(TextLoader.getText("speech_jessica"+new Random().nextInt(3)));
		else level.game.textBox.show(explanation);
	}
	
	@Override
	public void render(Screen screen)
	{
		screen.render(SpriteFilter.getShadowStanding(SpriteSheet.jessica.getSprite(sx, sy, 64, 64)), getX() - 64, getY(), 512, 256, 0, 0.25);
		screen.render(SpriteSheet.jessica.getSprite(sx, sy, 64, 64), getX(), getY(), 256, 256, 0, invulnerableInvisible ? 0.5 : 1);
	
		screen.render(SpriteSheet.ironSwordInHand.getSprite(sx, sy, 64, 64), getX(), getY(), 256, 256, 0, 1);
		
		if(isMouseOn())
		{
			String name = TextLoader.getText("npc_jessica");
			String part1 = name.split(" ")[0];
			String part2 = name.split(" ")[1];
			
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
	public boolean doBlock(GameObject o)
	{
		return o instanceof Player;
	}
	
	@Override
	public void update(double tpf)
	{
		super.update(tpf);
		
		changeWalkTimer.count(tpf);
		if(!walk && !attack) startAttackingTimer.count(tpf);
		attackTimer.count(tpf);
		
		resetImage();
	}

}
