package game.gui;

import java.awt.Point;

import game.Game;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.utils.DoublePoint;

public class TextBox {
	
	private boolean visible = false;

	private String[] texts;
	private String text;
	private int textIndex;
	
	public boolean over = true;
	
	private Speech speech;
	
	public TextBox()
	{
		
	}
	
	public void mousePressed(DoublePoint pos, int button)
	{
		if(button == 1) nextText();
	}
	
	private void nextText()
	{
		textIndex ++;
		if(texts != null && textIndex < texts.length) text = texts[textIndex];
		else 
		{
			visible = false;
			over = true;
			if(speech != null)
			{
				speech.setOver(true);
				speech = null;
			}
		}
	}
	
	public void show(Speech speech)
	{
		this.speech = speech;
		show(speech.getText());
	}
	
	public void show(String text)
	{				
		over = false;
		visible = true;
		this.texts = text.split("/np");
		textIndex = 0;
		this.text = texts[textIndex];
	}
	
	public void render(Screen screen)
	{		
		if(visible && text != null)
		{
//			screen.renderGUI(SpriteSheet.textBox.getSprite(256, 0, 64, 64), 
//							 Screen.DEFAULT_WIDTH / 2 - ((1024 + 256 + 32) / 2), 
//							 Screen.DEFAULT_HEIGHT * 0.9 - 256, 
//							 256, 256, 0, 1);
			
//			screen.renderGUI(SpriteSheet.textBox.getSprite(0, 0, 256, 64),
//							 Screen.DEFAULT_WIDTH / 2 - ((1024 + 256 + 32) / 2) + 256 + 32, 
//					         Screen.DEFAULT_HEIGHT * 0.9 - 256,
//					         1024, 256, 0, 1);
			
			screen.renderGUI(SpriteSheet.textBox.getSprite(0, 0, 256, 64),
					 		 Screen.DEFAULT_WIDTH / 2 - 512, 
					 		 Screen.DEFAULT_HEIGHT * 0.9 - 256,
					 		 1024, 256, 0, 1);
			
			String[] lines = text.split("/nl");
			
			for(int i = 0; i < lines.length; i++)
			{
//				screen.renderFont(lines[i], 
//								  Screen.DEFAULT_WIDTH / 2 - ((1024 + 256 + 32) / 2) + 256 + 32 + 512 - (Font.getTextWidth(lines[i], 32) / 2), 
//								  Screen.DEFAULT_HEIGHT * 0.9 - 256 + 128 - (lines.length * 20) + (i * 40),
//								  32, 
//								  Font.COLOR_WHITE, 
//								  false);
				
				screen.renderFont(lines[i], 
								  Screen.DEFAULT_WIDTH / 2 + 32 - (Font.getTextWidth(lines[i], 32) / 2), 
								  Screen.DEFAULT_HEIGHT * 0.9 - 256 + 128 - (lines.length * 20) + (i * 40),
								  32, 
								  Font.COLOR_WHITE, 
								  false);
			}
		}
	}

}
