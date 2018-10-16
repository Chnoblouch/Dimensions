package game.utils;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import game.environment.BlueStone;
import game.environment.DarkGround;
import game.environment.DarkSpike;
import game.environment.FrostPearl;
import game.environment.GrassBlock;
import game.environment.GrassGround;
import game.environment.Mushroom;
import game.environment.NightHerb;
import game.environment.RedStone;
import game.environment.SnowGround;
import game.environment.Stone;
import game.environment.Tree;
import game.gfx.SpriteFilter;
import game.gfx.SpriteSheet;

public class Editor 
extends JPanel
implements ActionListener, KeyListener, MouseMotionListener, MouseListener {
	
	public JMenuBar menuBar;
	
	private JMenu file;
	private JMenuItem saveMap;
	private JMenuItem openMap;
	private JMenuItem exportMap;
	
	private JMenu addObject;
	
	private JMenu setMode;
	private JMenuItem modePaint;
	private JMenuItem modeSelect;
	
	private JMenu extras;
	private JMenuItem instantGround;
	private JMenuItem selectAllOfCurrentObj;
	private JCheckBoxMenuItem bigBrush;
	private JCheckBoxMenuItem unlimited;
	
	private int mode = 0;
	private final static int MODE_PAINT = 0;
	private final static int MODE_SELECT = 1;
	
	private EditorObject objToPaint;
	private HashMap<String, EditorObject> availableObjects = new HashMap<>();
	private ArrayList<ObjectPlace> objPlaces = new SafeArrayList<>();
	
	public ArrayList<EditorObject> selectedObjects = new SafeArrayList<>();
	private ArrayList<EditorObject> objs = new SafeArrayList<>();
	private double camX, camY;
	
	private boolean shiftDown = false;
	
	public Editor()
	{		
		addMouseMotionListener(this);
		addMouseListener(this);
		
		menuBar = new JMenuBar();
		
		file = new JMenu("File");
		
		saveMap = new JMenuItem("Save");
		saveMap.addActionListener(this);
		file.add(saveMap);
		
		openMap = new JMenuItem("Open");
		openMap.addActionListener(this);
		file.add(openMap);
		
		exportMap = new JMenuItem("Export");
		exportMap.addActionListener(this);
		file.add(exportMap);
		
		menuBar.add(file);
		
		addObject = new JMenu("Select Object");
		menuBar.add(addObject);
		
		setMode = new JMenu("Select Mode");
		
		modePaint = new JMenuItem("Paint");
		modePaint.addActionListener(this);
		setMode.add(modePaint);
		
		modeSelect = new JMenuItem("Select");
		modeSelect.addActionListener(this);
		setMode.add(modeSelect);
		
		menuBar.add(setMode);
		
		extras = new JMenu("Extras");
		
		instantGround = new JMenuItem("Instant Grass Ground");
		instantGround.addActionListener(this);
		extras.add(instantGround);
		
		selectAllOfCurrentObj = new JMenuItem("Select all of current obj");
		selectAllOfCurrentObj.addActionListener(this);
		extras.add(selectAllOfCurrentObj);
		
		bigBrush = new JCheckBoxMenuItem("Big Brush");
		extras.add(bigBrush);
		
		unlimited = new JCheckBoxMenuItem("Unlimited Painting");
		extras.add(unlimited);
		
		menuBar.add(extras);
		
		addObjectToMenubar("Grass Ground", GrassGround.class.getName(), SpriteSheet.grassBlock, Block.SIZE, Block.SIZE, 24, 24, 
						   new int[]{0, 24, 48}, new int[]{0, 0, 0}, 0, false);
		addObjectToMenubar("Grass Block", GrassBlock.class.getName(), SpriteSheet.grassBlock, Block.SIZE, Block.SIZE, 24, 24, 
						   new int[]{0, 24, 48, 72, 96, 120, 144, 168, 192,
								   	 0, 24, 48, 72, 96, 120,
								   	 0, 24, 48, 72, 96, 120,
								   	 0, 24, 48, 72, 96, 120, 144,
								   	 0, 24}, 
						   new int[]{24, 24, 24, 24, 24, 24, 24, 24, 24,
								     48, 48, 48, 48, 48, 48,
								     72, 72, 72, 72, 72, 72,
								     96, 96, 96, 96, 96, 96, 96,
								     120, 120}, 
						   			 1, false);
		addObjectToMenubar("Tree", Tree.class.getName(), SpriteSheet.overworld, Block.SIZE * 3, Block.SIZE * 3, 72, 72, 
						   new int[]{0}, new int[]{0}, 1, true);
		addObjectToMenubar("Stone", Stone.class.getName(), SpriteSheet.overworld, Block.SIZE * 1.5, Block.SIZE, 32, 24, 
				   		  new int[]{0}, new int[]{72}, 1, true);
		addObjectToMenubar("Red Stone", RedStone.class.getName(), SpriteSheet.darkworld, Block.SIZE * 1.5, Block.SIZE, 32, 24, 
		   		  		   new int[]{0}, new int[]{64}, 1, true);
		addObjectToMenubar("Blue Stone", BlueStone.class.getName(), SpriteSheet.iceworld, Block.SIZE * 1.5, Block.SIZE, 32, 24, 
						   new int[]{0}, new int[]{24}, 1, true);
		addObjectToMenubar("Mushroom", Mushroom.class.getName(), SpriteSheet.overworld, Block.SIZE, Block.SIZE, 24, 24, 
		   		  new int[]{32}, new int[]{72}, 1, true);
		
		
		addObject.addSeparator();
		
		addObjectToMenubar("Dark Ground", DarkGround.class.getName(), SpriteSheet.darkBlock, Block.SIZE, Block.SIZE, 24, 24, 
						   new int[]{0}, new int[]{0}, 0, false);
		addObjectToMenubar("Dark Spike", DarkSpike.class.getName(), SpriteSheet.darkworld, 64, 256, 16, 64, 
						   new int[]{0}, new int[]{0}, 1, true);
		addObjectToMenubar("Night Herb", NightHerb.class.getName(), SpriteSheet.darkworld, Block.SIZE, Block.SIZE, 24, 24, 
						   new int[]{16}, new int[]{0}, 1, true);
		
		addObject.addSeparator();
		
		addObjectToMenubar("Snow Ground", SnowGround.class.getName(), SpriteSheet.snowBlock, Block.SIZE, Block.SIZE, 24, 24, 
				   		   new int[]{0, 24, 48}, new int[]{0, 0, 0}, 0, false);
		addObjectToMenubar("Frost Pearl", FrostPearl.class.getName(), SpriteSheet.iceworld, Block.SIZE, Block.SIZE, 24, 24, 
						   new int[]{0}, new int[]{0}, 1, false);
		
		for(int x = 0; x < Block.SIZE * 128; x+= Block.SIZE)
		{
			for(int y = 0; y < Block.SIZE * 128; y+= Block.SIZE)
			{
				objPlaces.add(new ObjectPlace(x, y));
			}
		}
		
		camX = 64 * Block.SIZE - 500;
		camY = 64 * Block.SIZE - 375;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		objs.sort(new Comparator<EditorObject>() 
		{
			@Override
			public int compare(EditorObject o1, EditorObject o2)
			{
				int o1Index = (int) (o1.zIndex > 0 ? o1.y + o1.h : 0);
				int o2Index = (int) (o2.zIndex > 0 ? o2.y + o2.h : 0);
				
				return o1Index - o2Index;
			}
		});
		
		objs.forEach(t -> t.render(this, (Graphics2D) g, camX, camY));
		selectedObjects.forEach(t -> t.renderSelected(this, (Graphics2D) g, camX, camY));
	}
	
	private void addObjectToMenubar(String name, String objName, SpriteSheet sheet, double w, double h, int sw, int sh, 
								    int[] asx, int[] asy, int zIndex, boolean shadow)
	{
		availableObjects.put(name, new EditorObject(name, objName, sheet, w, h, sw, sh, asx, asy, zIndex, shadow));
		
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
//				EditorObject obj = new EditorObject(sheet, w, h, sw, sh, alpha, asx, asy);
//				obj.x = 0;
//				obj.y = 0;
//				obj.alpha = 255;
//				objs.add(obj);
//				
//				selectedObjects.add(obj);
				
				objToPaint = new EditorObject(name, objName, sheet, w, h, sw, sh, asx, asy, zIndex, shadow);
				
				repaint();
			}
		});
		addObject.add(item);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == saveMap) saveMap();
		else if(e.getSource() == openMap) openMap();
		else if(e.getSource() == exportMap) exportMap();
		else if(e.getSource() == modePaint) mode = MODE_PAINT;
		else if(e.getSource() == modeSelect) mode = MODE_SELECT;
		else if(e.getSource() == instantGround)
		{
			for(int y = Block.SIZE * 16; y < Block.SIZE * 112; y += Block.SIZE)
			{
				for(int x = Block.SIZE * 16; x < Block.SIZE * 112; x += Block.SIZE)
				{
					if((x < Block.SIZE * 40 || x > Block.SIZE * 88) || (y < Block.SIZE * 40 || y > Block.SIZE * 88))
					{						
						for(int i = 0; i < objPlaces.size(); i++)
						{
							ObjectPlace p = objPlaces.get(i);
							if(p.x == x && p.y == y && p.isEmpty())
							{
								EditorObject obj = objToPaint.clone();
								obj.x = p.x;
								obj.y = p.y;
								obj.place = p;
								objs.add(obj);
								
								p.objs.add(obj);
//								selectedObjects.add(obj);
							}
						}
					}
				}
			}
			
//			for(int i = 0; i < objPlaces.size(); i++)
//			{
//				ObjectPlace p  = objPlaces.get(i);
//				if(p.isEmpty()) 
//				{
//					EditorObject obj = objToPaint.clone();
//					obj.x = p.x;
//					obj.y = p.y;
//					obj.place = p;
//					objs.add(obj);
//					
//					p.objs.add(obj);
//					selectedObjects.add(obj);
//				}
//			}
		}
		else if(e.getSource() == selectAllOfCurrentObj)
		{
			for(int i = 0; i < objs.size(); i++)
			{
				EditorObject p = objs.get(i);
				if(p.name.equals(objToPaint.name)) selectedObjects.add(p);
			}
		}
	}
	
	private void saveMap()
	{
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Map Editor File", "mef"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.showSaveDialog(null);
		
		if(fc.getSelectedFile() != null)
		{
			File file = new File(fc.getSelectedFile().getAbsolutePath());
			
			try 
			{
				if(!file.getName().contains(".")) file = new File(file.getAbsolutePath()+".mef");
				
				PrintWriter pw = new PrintWriter(file.getAbsolutePath());
				
				for(int i = 0; i < objs.size(); i++)
				{
					EditorObject t = objs.get(i);
					pw.println(t.name+","+t.x+","+t.y+","+t.data+","+t.brightness+","+t.noPlace);
				}
				
				pw.close();
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void openMap()
	{
		objs.forEach(t -> 
		{
			if(!t.noPlace) t.place.objs.clear();
			objs.remove(t);
		});
		
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Map Editor File", "mef"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.showOpenDialog(null);
		
		if(fc.getSelectedFile() != null)
		{
			File file = new File(fc.getSelectedFile().getAbsolutePath());
			
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = "";
				
				while((line = reader.readLine()) != null)
				{
					String name = line.split(",")[0];
					double x = Double.parseDouble(line.split(",")[1]);
					double y = Double.parseDouble(line.split(",")[2]);
					int data = Integer.parseInt(line.split(",")[3]);
					double brightness = Double.parseDouble(line.split(",")[4]);
					boolean noPlace = Boolean.parseBoolean(line.split(",")[5]);
					
					if(availableObjects.get(name) == null)
					{
						System.out.println(name);
						continue;
					}
					
					EditorObject obj = availableObjects.get(name).clone();
					obj.x = x;
					obj.y = y;
					obj.data = data;
					obj.brightness = brightness;
					obj.noPlace = noPlace;
					objs.add(obj);
					
					if(!noPlace)
					{
						for(int i = 0; i < objPlaces.size(); i++)
						{
							ObjectPlace p = objPlaces.get(i);
							if(p.x == x && p.y == y)
							{
								obj.place = p;
								p.objs.add(obj);
							}
						}
					}
				}
				
				repaint();
				
				reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void exportMap()
	{
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Map", "map"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File("C:/Users/Magmi/workspace/Dimensions/res/map"));
		fc.showSaveDialog(null);
		
		if(fc.getSelectedFile() != null)
		{
			File file = fc.getSelectedFile();
						
			try 
			{
				if(!file.getName().contains(".")) file = new File(file.getAbsolutePath()+".map");
				
				PrintWriter pw = new PrintWriter(file.getAbsolutePath());
				
				for(int i = 0; i < objs.size(); i++)
				{
					EditorObject t = objs.get(i);
					pw.println(t.objName+","+t.x+","+t.y+","+t.data+","+t.brightness);
				}
				
				pw.close();
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
//			try {
//				ImageIO.write(getMapAsImage(), "PNG", file);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
	
//	private BufferedImage getMapAsImage()
//	{
//		BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g = img.createGraphics();
//		
//		for(int i = 0; i < objs.size(); i++)
//		{
//			EditorObject t = objs.get(i);
//						
//			g.setColor(new Color(t.col.getRed(), t.col.getGreen(), t.col.getBlue(), t.alpha));
//			g.fillRect((int) (t.x / Block.SIZE), (int) (t.y / Block.SIZE), 1, 1);
//		}
//		
//		return img;
//	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_P) mode = MODE_PAINT;
		if(key == KeyEvent.VK_S) mode = MODE_SELECT;
		
		selectedObjects.forEach(t -> 
		{
//			if(key == KeyEvent.VK_RIGHT) t.x += Block.SIZE;
//			if(key == KeyEvent.VK_LEFT) t.x -= Block.SIZE;
//			if(key == KeyEvent.VK_DOWN) t.y += Block.SIZE;
//			if(key == KeyEvent.VK_UP) t.y -= Block.SIZE;
			
			if(key == KeyEvent.VK_UP) 
			{
				t.data ++;
				if(t.data > 64) t.data = 1;
			}
			
			if(key == KeyEvent.VK_DOWN) 
			{
				t.data --;
				if(t.data < 1) t.data = 64;
			}
			
			if(key == KeyEvent.VK_RIGHT) 
			{
				t.brightness += 0.1;
				if(t.brightness > 1) t.brightness = 0;
			}
			
			if(key == KeyEvent.VK_LEFT) 
			{
				t.brightness -= 0.1;
				if(t.brightness < 0) t.brightness = 1;
			}
		});
		
		if(key == KeyEvent.VK_A) 
		{
			objs.forEach(t -> {
				if(!selectedObjects.contains(t)) selectedObjects.add(t);
			});
		}
			
		if(key == KeyEvent.VK_ENTER) selectedObjects.clear();
		if(key == KeyEvent.VK_DELETE || key == KeyEvent.VK_BACK_SPACE)
		{
			selectedObjects.forEach(t -> 
			{
				if(!t.noPlace) t.place.objs.remove(t);
				objs.remove(t);
			});
			selectedObjects.clear();
		}
		
		if(key == KeyEvent.VK_SHIFT) shiftDown = true;
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) shiftDown = false;
	}
	
	private Point lastMousePos = new Point();
	private int button = 0;
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{		
		if(button == 3)
		{
			if(e.getX() > lastMousePos.getX()) camX -= e.getX() - lastMousePos.x;
			if(e.getX() < lastMousePos.getX()) camX += lastMousePos.x - e.getX();
			if(e.getY() > lastMousePos.getY()) camY -= e.getY() - lastMousePos.y;
			if(e.getY() < lastMousePos.getY()) camY += lastMousePos.y - e.getY();
					
			lastMousePos = e.getPoint();
		} else if(button == 1 && objToPaint != null)
		{
			if(mode == MODE_PAINT && !unlimited.isSelected())
			{
				int finalX = bigBrush.isSelected() ? e.getX() + (int) camX - (Block.SIZE * 4) : e.getX() + (int) camX;
				int finalY = bigBrush.isSelected() ? e.getY() + (int) camY - (Block.SIZE * 4) : e.getY() + (int) camY;
				int finalW = bigBrush.isSelected() ? Block.SIZE * 12 : 0;
				int finalH = bigBrush.isSelected() ? Block.SIZE * 12 : 0;
				
				objPlaces.forEach(p ->
				{			
					if(!p.isObject(objToPaint.name))
					{
						if(finalX + finalW >= p.x && finalX <= p.x + Block.SIZE && finalY + finalH >= p.y && finalY <= p.y + Block.SIZE)
						{
							EditorObject obj = objToPaint.clone();
							obj.x = p.x;
							obj.y = p.y;
							obj.place = p;
							objs.add(obj);
							
							p.objs.add(obj);
							selectedObjects.add(obj);
						}
					}
				});
			}
		}
		
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		lastMousePos = e.getPoint();
		
		button = e.getButton();
		
		if(button == 1)
		{
			int finalX = e.getX() + (int) camX;
			int finalY = e.getY() + (int) camY;
			
			if(mode == MODE_PAINT && unlimited.isSelected())
			{				
				EditorObject obj = objToPaint.clone();
				obj.x = finalX - (obj.w / 2);
				obj.y = finalY - (obj.h / 2);
				obj.noPlace = true;
				objs.add(obj);
				
				selectedObjects.add(obj);
			}
			
			for(int i = 0; i < objs.size(); i++)
			{
				EditorObject t = objs.get(i);
				
				if(finalX >= t.x && finalX <= t.x + t.w && finalY >= t.y && finalY <= t.y + t.h)
				{					
					if(!shiftDown)
					{
						if(mode == MODE_SELECT)
						{
							if(!selectedObjects.contains(t)) 
							{
								selectedObjects.add(t);
								break;
							}
//							else selectedObjects.remove(t);
						}
					} else {
						if(mode == MODE_PAINT) objToPaint = t.clone();
					}
				}
			}
		}
		
		repaint();
	}	
	
	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	class EditorObject
	{
		public String name;
		public String objName;
		private SpriteSheet sheet;
		public double x, y, w, h;
		private int sw, sh;
		public int data = 0;
		public double brightness = 1;
		private int[] asx;
		private int[] asy;
		public HashMap<Integer, Point> dataSprite = new HashMap<>();
		public int zIndex;
		public ObjectPlace place;
		public boolean noPlace = false;
		public boolean shadow = false;
		
		public EditorObject(String name, String objName, SpriteSheet sheet, double w, double h, int sw, int sh, int[] asx, int[] asy, int zIndex,
						    boolean shadow)
		{
			this.name = name;
			this.objName = objName;
			this.sheet = sheet;
			this.w = w;
			this.h = h;
			this.sw = sw;
			this.sh = sh;
			this.asx = asx;
			this.asy = asy;
			this.zIndex = zIndex;
			this.shadow = shadow;
			
			for(int i = 0; i < asx.length; i++)
			{
				dataSprite.put(i, new Point(asx[i], asy[i]));
			}
		}
		
		public EditorObject clone()
		{
			EditorObject clone = new EditorObject(name, objName, sheet, w, h, sw, sh, asx, asy, zIndex, shadow);
			clone.data = data;
			
			return clone;
		}
				
		public void render(Editor editor, Graphics2D g, double camX, double camY)
		{
			int finalX = (int) (x - camX);
			int finalY = (int) (y - camY);
			int finalW = (int) w;
			int finalH = (int) h;
			
			Point sprite = dataSprite.get(data); 
			if(sprite == null) sprite = new Point(0, 0);
			
			if(shadow)
			{
				AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
				g.setComposite(ac);
				try 
				{
					g.drawImage(SpriteFilter.getShadowStanding(sheet.getSprite(sprite.x, sprite.y, sw, sh)), finalX, finalY, finalW * 2, finalH, null);
				} catch (Exception e) 
				{
				}
			}
			
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			g.setComposite(ac);
			
			if(brightness == 1) g.drawImage(sheet.getSprite(sprite.x, sprite.y, sw, sh), finalX, finalY, finalW, finalH, null);
			else 
				g.drawImage(SpriteFilter.getDarker(sheet.getSprite(sprite.x, sprite.y, sw, sh), brightness), finalX, finalY, finalW, finalH, null);
		}
		
		public void renderSelected(Editor editor, Graphics2D g, double camX, double camY)
		{
			int finalX = (int) (x - camX);
			int finalY = (int) (y - camY);
			int finalW = (int) w;
			int finalH = (int) h;
			
			g.setColor(new Color(255, 0, 0));
			g.setStroke(new BasicStroke(6));
			g.drawRect(finalX - 3, finalY - 3, finalW + 6, finalH + 6);
			
			String dataText = "Data: " + data;
			
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString(dataText, (int) (finalX - (g.getFontMetrics().stringWidth(dataText) / 2) + (w / 2)), finalY + 148);
		}
	}
	
	class ObjectPlace
	{
		public ArrayList<EditorObject> objs = new SafeArrayList<>();
		public double x, y;
		
		public ObjectPlace(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
		
		public boolean isObject(String name)
		{
			return objs.stream().filter(t -> t.name.equals(name)).findAny().orElse(null) != null;
		}
		
		public boolean isEmpty()
		{
			return objs.isEmpty();
		}
	}
	
	public static void main(String[] args)
	{
		Editor editor = new Editor();
		
		JFrame frame = new JFrame(); 
		frame.pack();
		frame.setSize(1000, 750);
		frame.setTitle("Editor");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(editor.menuBar);
		frame.addKeyListener(editor);
		frame.setVisible(true);
			
		frame.add(editor);
		
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
