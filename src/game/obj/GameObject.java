package game.obj;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.creature.Player;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.levels.Level;
import game.utils.Angles;
import game.utils.Block;
import game.utils.DoublePoint;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class GameObject
{
	public Level level;
	private double x, y;
	public boolean mouseOn = false;
	
	public void init(Level level)
	{
		this.level = level;
	}
	
	public void setPosition(double x, double y) 
	{
		this.x = x;
		this.y = y;
	}
		
	public void move(double x, double y)
	{
		if(x == 0 && y == 0) return;
		
		if(inCollisionSpace())
		{			
			int stepSize = 1;
			
			if(x > 0) { for(double i = 0; i < x; i+= stepSize) { step(stepSize, 0); } }
			if(x < 0) { for(double i = 0; i > x; i-= stepSize) { step(-stepSize, 0); } }
			if(y > 0) { for(double i = 0; i < y; i+= stepSize) { step(0, stepSize); } }
			if(y < 0) { for(double i = 0; i > y; i-= stepSize) { step(0, -stepSize); } }
		} else 
		{
			this.x += x; 
			this.y += y;
		}
	}
	
	private void step(double x, double y)
	{
		for(int i = 0; i < level.collisionSpace.getObjects().size(); i++)
		{
			GameObject o = level.collisionSpace.getObjects().get(i);
						
			if(o != this && isInRange(o) && collides(o) && o.doBlock(this))
			{
				CollisionResult result = collidesSides(o);
								
				if(x > 0 && result.right) x = 0;
				if(x < 0 && result.left) x = 0;
				if(y > 0 && result.bottom) y = 0;
				if(y < 0 && result.top) y = 0;
			}
		}
				
		this.x += x;
		this.y += y;
	}
	
	public double getX() 
	{
		return x;
	}
	
	public double getY() 
	{
		return y; 
	}
	
	public boolean inCollisionSpace()
	{
		return false;
	}
		
	public boolean doBlock(GameObject other)
	{
		return false;
	}
	
	public Hitbox getHitbox()
	{
		return HitboxFactory.create(x, y, 128, 128);
	}
	
	public Hitbox getClickHitbox()
	{
		return HitboxFactory.create(x, y, 128, 128);
	}
	
	public Hitbox getUpdateHitbox()
	{
		return HitboxFactory.create(x, y, 128, 128);
	}
	
	public boolean collides(GameObject other) 
	{ 
		return getHitbox().intersects(other.getHitbox());
	}
	
	public class CollisionResult
	{
		public boolean left;
		public boolean right;
		public boolean top;
		public boolean bottom;
	}
	
	public CollisionResult collidesSides(GameObject other)
	{
		CollisionResult result = new CollisionResult();
		Hitbox hb = other.getHitbox();
		result.left = hb.intersects(getHitbox().left);
		result.top = hb.intersects(getHitbox().top);
		result.right = hb.intersects(getHitbox().right);
		result.bottom = hb.intersects(getHitbox().bottom);
		
		return result;
	}
	
	public boolean isInRange(GameObject other)
	{
		if(x == other.x && y == other.y) return true;
				
		double distanceX = x > other.x ? x - other.x : other.x - x;
		double distanceY = y > other.y ? y - other.y : other.y - y;
		
		if(distanceX < 500 && distanceY < 500) return true;
		
		return false;
	}
	
	public boolean isInside(double x, double y)
	{
		return getHitbox().getShape().contains((int) x, (int) y);
	}
	
	public boolean isMouseInside(double x, double y)
	{
		return getClickHitbox().getShape().contains((int) x, (int) y);
	}
	
	public boolean isMouseOn()
	{
		return isMouseInside(level.game.getMouse().x + level.game.screen.camX, level.game.getMouse().y + level.game.screen.camY);
	}
	
	public double getAngle(GameObject other)
	{
		return Angles.getAngle(getHitbox().center(), other.getHitbox().center());
	}
	
	public double getAngle(DoublePoint point)
	{
		return Angles.getAngle(getHitbox().center(), point);
	}
	
	public DoublePoint getMoveDirection(double angle)
	{
		return Angles.getMoveDirection(angle);
	}
	
	public void moveAlongAngle(double angle, double speed)
	{
		DoublePoint dir = getMoveDirection(angle);
		move(dir.x * speed, dir.y * speed);
	}
	
	public String save() { return null; }
	public void load(String data) {}
	public boolean doSave() { return false; }
	
	public void render(Screen screen) {}
	public int getZIndex() { return (int) (y + 128); }
	public void readData(int data) {}
	public void brightness(double brightness) {}
	public void interactWith(Player player) {}
	public void update(double tpf) {}
	public boolean doUpdate() { return true; }
	public boolean updateOutside() { return false; }
}
