package game.particle;

import java.util.Random;

import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.utils.TimeCounter;

public class ParticleFeather 
extends Particle {
	
	private double size = 24 + (new Random().nextDouble() * 12);
	private double dirX = - 4 + (new Random().nextDouble() * 8), dirY = -1 - (new Random().nextDouble() * 6);
	private double rot = - 90 + new Random().nextInt(90);
	
	private double alpha = 1;
		
	@Override
	public int getZIndex()
	{
		return (int) (getY() + size);
	}
	
	@Override
	public void render(Screen screen)
	{
		screen.render(SpriteSheet.particles.getSprite(0, 10, 8, 8), getX(), getY(), size, size, rot, alpha);
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
