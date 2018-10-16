package game.levels;

import java.util.Comparator;

import game.CollisionSpace;
import game.Game;
import game.creature.Player;
import game.environment.GrassGround;
import game.gfx.Screen;
import game.obj.GameObject;
import game.utils.Block;
import game.utils.DoublePoint;
import game.utils.SafeArrayList;

public class Level
{
	public Game game;
		
	private int id = 0;
	private String name;
	
	public Player player;
	public SafeArrayList<GameObject> objects = new SafeArrayList<>();
	public SafeArrayList<GameObject> updateables = new SafeArrayList<>();
	public CollisionSpace collisionSpace = new CollisionSpace();
	
	public static double SIZE = Block.SIZE * 128;
	public static double CENTER = Block.SIZE * 64;
	
	private DoublePoint playerSpawn = new DoublePoint(Level.CENTER, Level.CENTER + (Block.SIZE * 4));
	
	public int monsterCounter = 0;
	
	public Level(Game game, int id, String name)
	{
		this.game = game;
		this.id = id;
		this.name = name;
	}
	
	public void setPlayerSpawn(double x, double y)
	{
		playerSpawn.x = x;
		playerSpawn.y = y;
	}
	
	public DoublePoint getPlayerSpawn()
	{
		return playerSpawn;
	}
	
	public void addObject(GameObject obj)
	{
		if(obj instanceof Player) player = (Player) obj;
		
		objects.add(obj);
		if(obj.doUpdate()) updateables.add(obj);
		if(obj.inCollisionSpace()) collisionSpace.add(obj);
		obj.init(this);
	}
	
	public void removeObject(GameObject obj)
	{
		objects.remove(obj);
		if(obj.doUpdate()) updateables.remove(obj);
		if(obj.inCollisionSpace()) collisionSpace.remove(obj);
	}
	
	public void update(double tpf)
	{
		updateables.forEach(o -> 
		{ 
			if(game.screen.isInside(o.getUpdateHitbox()) || o.updateOutside())
				o.update(tpf);
		});
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void render(Screen screen)
	{
		synchronized (Game.LOCK) 
		{
			objects.sort(new Comparator<GameObject>()
			{
				@Override
				public int compare(GameObject r1, GameObject r2) 
				{
					return r1.getZIndex() - r2.getZIndex();
				}
			});
			
			objects.forEach(r -> r.render(screen));
		}
	}

}
