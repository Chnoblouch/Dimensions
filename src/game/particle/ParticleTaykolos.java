package game.particle;

import java.util.Random;

import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.utils.TimeCounter;

public class ParticleTaykolos 
extends Particle {
	
	private double size = 24 + (new Random().nextDouble() * 8);
	
	private double angle = new Random().nextInt(360);
	private double speed = 2 + new Random().nextInt(6);
	
	private double alpha = 1;
		
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	@Override
	public int getZIndex()
	{
		return 1000000;
	}
	
	@Override
	public void render(Screen screen)
	{
		if(screen.isInside(getX(), getY(), size, size))
			screen.render(SpriteSheet.particles.getSprite(6, 4, 6, 6), getX(), getY(), size, size, 0, alpha);
	}
	
	@Override
	public void update(double tpf)
	{		
		if(alpha > 0.025) alpha -= 0.025 * tpf;
		else level.removeObject(this);
		
		moveAlongAngle(angle, speed * tpf);
	}

}
