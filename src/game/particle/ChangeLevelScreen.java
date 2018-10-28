package game.particle;

import game.gfx.Screen;
import game.gfx.SpriteSheet;

public class ChangeLevelScreen 
{	
	private boolean running = false;
	public boolean finish = false;
	
	private double s1 = 0;
	private double a1 = 0, a2 = 0;
		
	public void render(Screen screen)
	{
		if(!running && !finish) return;
		
		screen.renderGUI(SpriteSheet.changelevelParticle.getSprite(0, 0, 128, 128), 
						(Screen.DEFAULT_WIDTH / 2) - (s1 / 2), 
						(Screen.DEFAULT_HEIGHT / 2) - (s1 / 2),
						s1, s1, 0, a1);
		
		screen.renderGUI(SpriteSheet.changelevelParticle.getSprite(128, 0, 128, 128), 0, 0, Screen.DEFAULT_WIDTH, Screen.DEFAULT_HEIGHT, 0, a2);
	}
	
	public void run()
	{
		running = true;
		finish = false;
		s1 = 0;
		a1 = 0;
		a2 = 0;
	}
	
	public void update(double tpf)
	{
		if(running)
		{
			s1 += 30 * tpf;
			if(a1 < 0.95) a1 += 0.05 * tpf;
			if(a2 < 0.98) a2 += 0.02 * tpf;
			else
			{
				running = false;
				finish = true;
				
				s1 = 0;
				a1 = 0;
			}
		}
		
		if(finish)
		{
			if(a2 > 0.02) a2 -= 0.02 * tpf;
			else 
			{
				finish = false;
				a2 = 0;
			}
		}
	}

}
