package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.creature.Player;
import game.gfx.Font;
import game.gfx.Screen;
import game.gfx.Sounds;
import game.gfx.SpriteSheet;
import game.gui.Inventory;
import game.gui.TextBox;
import game.levels.Level;
import game.levels.LevelDarkWorld;
import game.levels.LevelFrostWorld;
import game.levels.LevelOverworld;
import game.levels.LevelSkyWorld;
import game.particle.ChangeLevelScreen;
import game.particle.YouDiedScreen;
import game.saving.SaveManager;
import game.utils.DoublePoint;
import game.utils.MapLoader;

public class Game
extends JPanel
implements Runnable {
	
	public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);
	
	public Input input;
	
	public Player player;
	private Level level;
	public int levelID = 0;
	private Level[] levels = new Level[4];
	private Map[] maps = new Map[4];
	
	public Inventory inventory;
	
	public static Object LOCK = new Object();
	
	private boolean running = false;
	
	public Screen screen;
	
	private int fps = 0;
	
	public ChangeLevelScreen changeLevelScreen;
	public YouDiedScreen youDiedScreen;
	
	public TextBox textBox;
	
	public boolean saved = false;
			
	public Game()
	{				
		input = new Input(this);
		inventory = new Inventory(this);
		
		textBox = new TextBox();
		
		setBackground(Color.black);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), 
																 new Point(0, 0),
																 "empty"));
	}
		
	public void start()
	{
		new Thread(this).start();
		running = true;
		
		init();
	}
	
	public void stop()
	{
		running = false;
		stop();
	}
	
	private void init()
	{
		screen = new Screen(this);
				
		createMaps();
		
		levels[0] = new LevelOverworld(this);
		levels[1] = new LevelDarkWorld(this);
		levels[2] = new LevelSkyWorld(this);
		levels[3] = new LevelFrostWorld(this);
		
		levelID = 0;
		level = levels[levelID];
		
		player = new Player(this);
		player.setPosition(level.getPlayerSpawn().x, level.getPlayerSpawn().y);
		level.addObject(player);
		
		changeLevelScreen = new ChangeLevelScreen();
		youDiedScreen = new YouDiedScreen(this);
		
		if(exists()) SaveManager.loadGameState(this);
	}
	
	public boolean exists()
	{
		return false;
	}
	
	public void createMaps()
	{
		if(!exists())
		{
			maps[0] = MapLoader.loadMap("/map/overworld.map");
			maps[1] = MapLoader.loadMap("/map/darkworld.map");
			maps[2] = MapLoader.loadMap("/map/skyworld.map");
			maps[3] = MapLoader.loadMap("/map/frostworld.map");
		} else 
		{
			maps[0] = new Map();
			maps[1] = new Map();
			maps[2] = new Map(); 
			maps[3] = new Map();
			
			SaveManager.loadMaps(this);
		}
	}
	 
	public void changeLevel(int level)
	{
		this.level.removeObject(player);
		this.level.collisionSpace.remove(player);
		
		levelID = level;
		this.level = levels[levelID];
		
		this.level.addObject(player);
		player.setPosition(this.level.getPlayerSpawn().x, this.level.getPlayerSpawn().y);
		
		if(level == 1)
		{
			Sounds.wind.setLoop(Clip.LOOP_CONTINUOUSLY);
			Sounds.wind.play(0, 0);
		} else Sounds.wind.stop();
	}
	
	public Level[] getLevels()
	{
		return levels;
	}
	
	public Level getLevel()
	{
		return level;
	}
	
	public int getLevelID()
	{
		return levelID;
	}
	
	public Map[] getMaps()
	{
		return maps;
	}
	
	public void changeLevelScreen()
	{
		changeLevelScreen.run();
	}
	
	public void youDiedScreen()
	{
		youDiedScreen.run();
	}
	
	public void hideYouDiedScreen()
	{
		youDiedScreen.hide();
	}
	
	public void keyPressed(int key)
	{
		if(player != null) player.keyPressed(key);		
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(JFrame.EXIT_ON_CLOSE);
	}
	
	public void keyReleased(int key)
	{
		player.keyReleased(key);
	}
	
	public void mousePressed(int button)
	{
		DoublePoint mousePos = getMouse();
		
		if(!inventory.visible) 
		{
			textBox.mousePressed(mousePos, button);
			if(textBox.over) player.mousePressed(mousePos, button);
		}
		
		inventory.mousePressed(mousePos, button);
	}

	public void mouseReleased(int button)
	{
		DoublePoint mousePos = getMouse();
		
		if(!inventory.visible && textBox.over) player.mouseReleased(mousePos, button);
		inventory.mouseReleased(mousePos, button);
	}
	
	public double getCamX()
	{
		return screen.camX;
	}
	
	public double getCamY()
	{
		return screen.camY;
	}
	
	public DoublePoint getMouse()
	{
		Point m = MouseInfo.getPointerInfo().getLocation();
		return new DoublePoint(m.x / screen.getScale(), m.y / screen.getScale());
//		return MouseInfo.getPointerInfo().getLocation();
	}
	
	public void mouseWheelMoved(int rot)
	{
//		if(rot > 0) inventory.hotbar.selectNext();
//		if(rot < 0) inventory.hotbar.selectLast();
	}
	
	private double healthBarProcess = 20;
	
	private void renderHealthBar()
	{
		int sy = player.invulnerableInvisible ? 9 : 0;
		
		for(int i = 0; i < 10; i ++)
		{
			int y = player.invulnerableInvisible ? 16 - 4 + new Random().nextInt(9) : 16;
			
			if(healthBarProcess >= (i + 1) * 10)
				screen.renderGUI(SpriteSheet.bars.getSprite(0, sy, 9, 9), i * 40 + 16, y, 36, 36, 0, 1);
			else if(healthBarProcess < (i + 1) * 10 && healthBarProcess >= ((i + 1) * 10) - 5)
				screen.renderGUI(SpriteSheet.bars.getSprite(9, sy, 9, 9), i * 40 + 16, y, 36, 36, 0, 1);
			else if(healthBarProcess < ((i + 1) * 10) - 5)
				screen.renderGUI(SpriteSheet.bars.getSprite(18, sy, 9, 9), i * 40 + 16, y, 36, 36, 0, 1);
		}
	}
	
	private double manaBarProcess = 20;
	
	private void renderManaBar()
	{		
		for(int i = 0; i < 10; i ++)
		{			
			if(manaBarProcess >= (i + 1) * 10)
				screen.renderGUI(SpriteSheet.bars.getSprite(27, 0, 9, 9), i * 40 + 16, 64, 36, 36, 0, 1);
			else if(manaBarProcess < (i + 1) * 10 && manaBarProcess >= ((i + 1) * 10) - 5)
				screen.renderGUI(SpriteSheet.bars.getSprite(36, 0, 9, 9), i * 40 + 16, 64, 36, 36, 0, 1);
			else if(manaBarProcess < ((i + 1) * 10) - 5)
				screen.renderGUI(SpriteSheet.bars.getSprite(45, 0, 9, 9), i * 40 + 16, 64, 36, 36, 0, 1);
		}
	}
			
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		synchronized (LOCK) 
		{
			if(screen != null) screen.beforeRendering((Graphics2D) g, WIDTH / 2 - (Screen.WIDTH / 2));
			
			if(level != null) level.render(screen);
		}
		
//		for(int i = 0; i < 100; i++)
//		{
//			screen.renderGUI(SpriteSheet.player.getSprite(0, 0, 64, 64), 
//							 new Random().nextInt(1920) - 128, new Random().nextInt(1080) - 128, 256, 256, 0, 1);
//		}
				
//		g.drawImage(screen.renderedScreen, WIDTH / 2 - (Screen.WIDTH / 2), 0, Screen.WIDTH, Screen.HEIGHT, null);
		
//		screen.renderHitbox(HitboxFactory.create(Level.CENTER - 16, Level.CENTER - 16, 32, 32));
		
		if(player != null)
		{
			renderHealthBar();
			renderManaBar();
			inventory.render(screen);
		}
		
		textBox.render(screen);
		
		screen.renderFont("FPS: "+fps, 16, Screen.HEIGHT - 32, 32, "white", false);
		if(saved) screen.renderFont("Game saved", 256, Screen.HEIGHT - 32, 32, Font.COLOR_WHITE, false);
		
		if(!level.player.isDeath())
		{
			DoublePoint mousePos = getMouse();
			screen.renderGUI(SpriteSheet.cursor.toImage(), mousePos.x, mousePos.y, 48, 48, 0, 1);
		}
		
		changeLevelScreen.render(screen);
		youDiedScreen.render(screen);
		
//		screen.renderFont("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 0, 100, 40, Font.COLOR_YELLOW, false);
//		screen.renderFont("abcdefghijklmnopqrstuvwxyz", 0, 180, 40, Font.COLOR_YELLOW, false);
//		screen.renderFont("0123456789", 0, 190, 40, Font.COLOR_YELLOW, false);
//		screen.renderFont(".,:;!?\"'+-=%*_|/\\()[]{}¦", 0, 235, 40, Font.COLOR_YELLOW, false);
//		screen.renderFont("Totally text and stuff", 0, 100, 40, Font.COLOR_YELLOW, false);
//		screen.renderFont("Boah! Even more text? That's crazy!", 0, 145, 40, Font.COLOR_YELLOW, false);
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH / 2 - (Screen.WIDTH / 2), HEIGHT);
		g.fillRect(WIDTH - (WIDTH / 2 - (Screen.WIDTH / 2)), 0, WIDTH / 2 - (Screen.WIDTH / 2), HEIGHT);
	}

	@Override
	public void run() 
	{
		long last = 0;
		
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		
		while(running)
		{
			long now = System.currentTimeMillis();
			double tpf = (1d / 25) * (now - last);

			last = now;
									
			if(level != null) 
			{
				synchronized (LOCK)
				{
					if(player != null && !player.isDeath()) level.update(tpf);
					else if(player != null && player.isDeath()) player.update(tpf);
					
					if(changeLevelScreen != null) changeLevelScreen.update(tpf);
					if(youDiedScreen != null) youDiedScreen.update(tpf);
					repaint();
				}
			}
			
			if(player != null)
			{
				if(player.getHealth() + 0.5 > healthBarProcess) healthBarProcess += tpf;
				if(player.getHealth() - 0.5 < healthBarProcess) healthBarProcess -= tpf;
				else healthBarProcess = player.getHealth();
				
				if(player.getMana() + 0.5 > manaBarProcess) manaBarProcess += tpf;
				if(player.getMana() - 0.5 < manaBarProcess) manaBarProcess -= tpf;
				else manaBarProcess = player.getMana();
			}
									
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			frames ++;
			
			if(System.currentTimeMillis() - lastTimer > 1000)
			{
				lastTimer += 1000;
				System.out.println(frames + " fps");
				this.fps = frames;
				
//				if(frames < 10)
//				{
//					System.out.println("The game closed because of too little fps");
//					System.exit(JFrame.EXIT_ON_CLOSE);
//				}
				
				frames = 0;
			}
		}
	}
	
	public static void main(String[] args)
	{		
		Game game = new Game();
		
		JFrame frame = new JFrame(); 
		frame.setSize(SIZE);
		frame.setTitle("Dimensions");
		frame.setResizable(false);
//		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
			
		frame.add(game);
		frame.addKeyListener(game.input);
		frame.addMouseListener(game.input);
		frame.addMouseWheelListener(game.input);
		
		game.start();
		
		frame.validate();
		frame.repaint();
	}

}
