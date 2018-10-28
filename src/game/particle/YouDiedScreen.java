package game.particle;

import game.Game;
import game.TextLoader;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteSheet;

public class YouDiedScreen 
{	
	private boolean running = false;
	private boolean pulsing = false;
	
	private double alpha = 0;
	private double addAlphaSpeed = 0.025;
	private double pulsingSize;
	private boolean pulsingBigger = false;
	
	private Game game;
	
	public YouDiedScreen(Game game)
	{
		this.game = game;
	}
			
	public void render(Screen screen)
	{		
		if(!running) return;
		
		double size = 2048 * (1.0 + (1.0 - alpha)) + pulsingSize;
		
		screen.renderGUI(SpriteSheet.deathScreen.toImage(),
						 Screen.DEFAULT_WIDTH / 2 - (size / 2),
						 Screen.DEFAULT_HEIGHT / 2 - (size / 2), 
						 size, size, 0, alpha);
		
		String youDied = TextLoader.getText("you_died0");
		String respawn = TextLoader.getText("you_died1");
		
//		screen.renderFont(youDied, Screen.DEFAULT_WIDTH / 2 - (Font.getTextWidth(youDied, 64)) / 2, 
//						  Screen.DEFAULT_HEIGHT / 2 - 384, 64, Font.COLOR_WHITE, false);
		
		if(game.player.deadTime >= 8000)
		{
			screen.renderFont(respawn, Screen.DEFAULT_WIDTH / 2 - (Font.getTextWidth(respawn, 48)) / 2, 
			  		  	  	  Screen.DEFAULT_HEIGHT / 2 + 96 - 384, 48, Font.COLOR_WHITE, false);
		}
	}
	
	public void run()
	{
		running = true;
		alpha = 0;
		addAlphaSpeed = 0.025;
		pulsing = false;
		pulsingBigger = false;
	}
	
	public void hide()
	{
		running = false;
	}
	
	public void update(double tpf)
	{
		if(running)
		{			
			if(alpha < 0.99) 
			{
				double process = game.player.deadTime / 8000.0d;
				alpha = process * process * (3.0 - 2.0 * process);
			}
			else 
			{
				alpha = 1.0;
//				pulsing = true;
			}
			
			if(pulsing)
			{
				if(pulsingSize <= 0) pulsingBigger = true;
				else if(pulsingSize >= 256) pulsingBigger = false;
				
				pulsingSize += pulsingBigger ? 8 * tpf : -4 * tpf;
			}
		}
	}

}
