package game.gfx;

public class Font {
	
	public static final String COLOR_WHITE = "white";
	public static final String COLOR_RED = "red";
	
	public static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ"+
								  "abcdefghijklmnopqrstuvwxyzäöü"+
								  "0123456789"+
								  ".,:;!?\"'+-=%*_|/\\()[]{}<>¦"+
								  " ";
	
	public static int[] x = { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,
							  0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,
							  0,1,2,3,4,5,6,7,8,9,
							  0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
							  0};
	
	public static int[] y = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
							  1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
							  2,2,2,2,2,2,2,2,2,2,
							  3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,
							  4};
	
	public static void render(Screen screen, String text, double x, double y, double size, String col)
	{		
		for(int i = 0; i < text.toCharArray().length; i++)
		{									
			int c = chars.indexOf(text.charAt(i));
			
			int cx = Font.x[c] * 6;
			int cy = Font.y[c] * 10;
			
			if(col.equals(COLOR_WHITE))
				screen.renderGUI(SpriteSheet.fontWhite.getSprite(cx, cy, 5, 10), x + (i * (size / 1.5)), y, size / 2, size, 0, 1);
			else if(col.equals(COLOR_RED))
				screen.renderGUI(SpriteSheet.fontRed.getSprite(cx, cy, 5, 10), x + (i * (size / 1.5)), y, size / 2, size, 0, 1);
		}
	}
	
	public static int getTextWidth(String text, double size)
	{
		return (int) (text.length() * (size / 1.5));
	}

}
