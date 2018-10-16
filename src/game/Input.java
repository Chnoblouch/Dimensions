package game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input 
implements KeyListener, MouseListener, MouseWheelListener {
	
	private Game game;
	
	public Key[] keys = new Key[27];
	
	public Input(Game game)
	{
		this.game = game;
		
		keys[0] = new Key(KeyEvent.VK_ESCAPE);
		keys[1] = new Key(KeyEvent.VK_W);
		keys[2] = new Key(KeyEvent.VK_A);
		keys[3] = new Key(KeyEvent.VK_S);
		keys[4] = new Key(KeyEvent.VK_D);
		keys[5] = new Key(KeyEvent.VK_SPACE);
		keys[6] = new Key(KeyEvent.VK_ENTER);
		keys[7] = new Key(KeyEvent.VK_E);
		keys[8] = new Key(KeyEvent.VK_1);
		keys[9] = new Key(KeyEvent.VK_2);
		keys[10] = new Key(KeyEvent.VK_3);
		keys[11] = new Key(KeyEvent.VK_4);
		keys[12] = new Key(KeyEvent.VK_5);
		keys[13] = new Key(KeyEvent.VK_6);
		keys[14] = new Key(KeyEvent.VK_7);
		keys[15] = new Key(KeyEvent.VK_8);
		keys[16] = new Key(KeyEvent.VK_9);
		keys[17] = new Key(KeyEvent.VK_0);
		keys[18] = new Key(KeyEvent.VK_UP);
		keys[19] = new Key(KeyEvent.VK_RIGHT);
		keys[20] = new Key(KeyEvent.VK_DOWN);
		keys[21] = new Key(KeyEvent.VK_LEFT);
		keys[22] = new Key(KeyEvent.VK_CONTROL);
		keys[23] = new Key(KeyEvent.VK_SHIFT);
		keys[24] = new Key(KeyEvent.VK_L);
		keys[25] = new Key(KeyEvent.VK_C);
		keys[26] = new Key(KeyEvent.VK_Q);
	}
	
	public Key getKey(int keyCode)
	{
		for(int i = 0; i < keys.length; i++)
		{
			if(keys[i].getKeyCode() == keyCode) return keys[i];
		}
		
		return null;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		Key k = getKey(e.getKeyCode());
		if(k != null && !k.isPressed())
		{
			k.setPressed(true);
			game.keyPressed(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		Key k = getKey(e.getKeyCode());
		if(k != null && k.isPressed()) 
		{
			k.setPressed(false);
			game.keyReleased(e.getKeyCode());
		}
	}
	
	class Key 
	{
		private int keyCode;
		private boolean pressed = false;
		
		public Key(int keyCode)
		{
			this.keyCode = keyCode;
		}
		
		public int getKeyCode()
		{
			return keyCode;
		}
		
		public void setPressed(boolean pressed)
		{
			this.pressed = pressed;
		}
		
		public boolean isPressed()
		{
			return pressed;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		game.mousePressed(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		game.mouseReleased(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		game.mouseWheelMoved(e.getWheelRotation());
	}

}
