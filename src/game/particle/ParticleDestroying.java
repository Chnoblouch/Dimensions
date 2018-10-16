package game.particle;

import java.util.Random;

import game.gfx.Screen;
import game.gfx.SpriteSheet;

public class ParticleDestroying 
extends Particle {
	
	private double size = 16 + (new Random().nextDouble() * 8);
	private double dirX = - 4 + (new Random().nextDouble() * 8), dirY = -1 - (new Random().nextDouble() * 6);
	
	private double alpha = 1;
	
	private int sx, sy;
		
	public void setSprite(int sx, int sy)
	{
		this.sx = sx;
		this.sy = sy;
	}
	
	@Override
	public int getZIndex()
	{
		return (int) (getY() + size + 32);
	}
	
	@Override
	public void render(Screen screen)
	{
		screen.render(SpriteSheet.particles.getSprite(sx, sy, 4, 4), getX(), getY(), size, size, 0, alpha);
	}
	
	@Override
	public void update(double tpf)
	{
		dirY += 0.2 * tpf;
		
		if(alpha > 0.015) alpha -= 0.015 * tpf;
		else level.removeObject(this);
		
		move(dirX * tpf, dirY * tpf);
	}

}
