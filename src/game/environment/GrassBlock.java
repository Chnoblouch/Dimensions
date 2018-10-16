package game.environment;

import java.util.Random;

import game.gfx.Screen;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;
import game.obj.GameObject;
import game.utils.Block;
import game.utils.Hitbox;
import game.utils.HitboxFactory;

public class GrassBlock
extends GameObject {
	
	private int sx, sy;
	private double brightness = 1;
	
	private int data = 0;
	
	@Override
	public int getZIndex()
	{
		return (int) (getY());
	}
	
	@Override
	public void readData(int data)
	{
		this.data = data;
		
		switch (data) 
		{
			case 0: { sx = 0; sy = 24; } break;
			case 1: { sx = 24; sy = 24; } break;
			case 2: { sx = 48; sy = 24; } break;
			case 3: { sx = 72; sy = 24; } break;
			case 4: { sx = 96; sy = 24; } break;
			case 5: { sx = 120; sy = 24; } break;
			case 6: { sx = 144; sy = 24; } break;
			case 7: { sx = 168; sy = 24; } break;
			case 8: { sx = 192; sy = 24; } break;
			
			case 9: { sx = 0; sy = 48; } break;
			case 10: { sx = 24; sy = 48; } break;
			case 11: { sx = 48; sy = 48; } break;
			case 12: { sx = 72; sy = 48; } break;
			case 13: { sx = 96; sy = 48; } break;
			case 14: { sx = 120; sy = 48; } break;
			
			case 15: { sx = 0; sy = 72; } break;
			case 16: { sx = 24; sy = 72; } break;
			case 17: { sx = 48; sy = 72; } break;
			case 18: { sx = 72; sy = 72; } break;
			case 19: { sx = 96; sy = 72; } break;
			case 20: { sx = 120; sy = 72; } break;
			
			case 21: { sx = 0; sy = 96; } break;
			case 22: { sx = 24; sy = 96; } break;
			case 23: { sx = 48; sy = 96; } break;
			case 24: { sx = 72; sy = 96; } break;
			case 25: { sx = 96; sy = 96; } break;
			case 26: { sx = 120; sy = 96; } break;
			case 27: { sx = 144; sy = 96; } break;

			case 28: { sx = 0; sy = 120; } break;
			case 29: { sx = 24; sy = 120; } break;
		}
	}
	
	@Override
	public void brightness(double brightness)
	{
		this.brightness = brightness;
	}
	
	@Override
	public boolean inCollisionSpace()
	{
		return true;
	}

	@Override
	public Hitbox getHitbox()
	{
		if(sx == 0 && sy == 48) 
		{
			double[] xp = new double[]{getX(), getX() + Block.SIZE, getX() + Block.SIZE};
			double[] yp = new double[]{getY(), getY(),			   getY() + Block.SIZE};
			
			return HitboxFactory.create(xp, yp);
			
		} else if(sx == 0 && sy == 72)
		{
			double[] xp = new double[]{getX(), getX(), getX() + Block.SIZE};
			double[] yp = new double[]{getY(), getY() + Block.SIZE, getY()};
			
			return HitboxFactory.create(xp, yp);
		} 
		else if(sx == 0 && sy == 96) return HitboxFactory.create(getX(), getY(), Block.SIZE, Block.SIZE / 4);
		else if(sx == 24 && sy == 96) return HitboxFactory.create(getX(), getY(), Block.SIZE / 4, Block.SIZE);
		else if(sx == 48 && sy == 96) return HitboxFactory.create(getX() + Block.SIZE - (Block.SIZE / 4), getY(), Block.SIZE / 4, Block.SIZE);
		else if(sx == 72 && sy == 96)
		{			
			double b = Block.SIZE;
			
			double[] xp = new double[]{getX(), getX() + b, getX() + b, getX() + (b / 4), getX() + (b / 4), getX()};
			double[] yp = new double[]{getY(), getY(), getY() + (b / 4), getY() + (b / 4), getY() + b, getY() + b};
			
			return HitboxFactory.create(xp, yp);
		}
		else if(sx == 96 && sy == 96)
		{
			double b = Block.SIZE;
			
			double[] xp = new double[]{getX() + b, getX(), getX(), getX() + b - (b / 4), getX() + b - (b / 4), getX() + b};
			double[] yp = new double[]{getY(), getY(), getY() + (b / 4), getY() + (b / 4), getY() + b, getY() + b};
			
			return HitboxFactory.create(xp, yp);
		}
		
		return HitboxFactory.create(getX(), getY(), Block.SIZE, Block.SIZE);
	}
	
	@Override
	public boolean doUpdate()
	{
		return false;
	}
	
	@Override
	public void render(Screen screen)
	{	
		if(screen.isInside(getX(), getY(), Block.SIZE, Block.SIZE)) 
			screen.render(SpriteFilter.getDarker(SpriteSheet.grassBlock.getSprite(sx, sy, 24, 24), brightness), 
						  getX(), getY(), Block.SIZE, Block.SIZE, 0, 1);
		
//		screen.renderHitbox(getHitbox());
	}
	
	@Override
	public boolean doBlock(GameObject o)
	{
		return true;
	}
	
	@Override
	public String save()
	{
		return data+","+brightness;
	}
	
	@Override
	public void load(String data)
	{
		readData(Integer.parseInt(data.split(",")[0]));
		brightness(Double.parseDouble(data.split(",")[1]));
	}
	
	@Override
	public boolean doSave()
	{
		return true;
	}

}
