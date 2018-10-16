package game.levels;

import java.util.ArrayList;
import java.util.Random;

import game.Game;
import game.creature.Dragon;
import game.creature.MonsterLobire;
import game.creature.Monster;
import game.creature.Player;
import game.creature.MonsterSlime;
import game.creature.NPCAnyrava;
import game.environment.GrassGround;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.obj.Portal;
import game.utils.Block;
import game.utils.MapLoader;
import game.utils.SafeArrayList;
import game.utils.TimeCounter;

public class LevelSkyWorld 
extends Level {
	
	private Player player;
	
	public Dragon dragon;
	public Dragon eldenir;

	private TimeCounter monsterTimer;
	
	public LevelSkyWorld(Game game)
	{
		super(game, 2, "dim_skyworld");
		
		setPlayerSpawn(Level.CENTER - (Block.SIZE * 1.5), Level.CENTER - (Block.SIZE * 4.5));
		
		game.getMaps()[2].onLevel(this);
		
		if(!game.exists())
		{
			Portal portal = new Portal();
			portal.setPosition(CENTER + (Block.SIZE * 11), CENTER);
			portal.setLevel(0);
			addObject(portal);
			
			dragon = new Dragon();
			dragon.setPosition(CENTER - (Block.SIZE * 4), CENTER - 384);
			addObject(dragon);
			
			eldenir = new Dragon();
			eldenir.setPosition(CENTER - (Block.SIZE * 4), CENTER - 384);
			eldenir.setLook(SpriteSheet.dragonEldenir);
			eldenir.setName("Eldenir");
			addObject(eldenir);
			
			NPCAnyrava anyrava = new NPCAnyrava();
			anyrava.setPosition(CENTER, CENTER);
			anyrava.tameDragon(eldenir);
			anyrava.rideOnDragon(eldenir);
			addObject(anyrava);
		}
		
		monsterTimer = new TimeCounter(5000, () ->
		{
			ArrayList<GrassGround> grounds = new SafeArrayList<>();
			
			for(int i = 0; i < objects.size(); i++)
			{
				if(objects.get(i) instanceof GrassGround) grounds.add((GrassGround) objects.get(i));
			}
			
			GrassGround place = grounds.get(new Random().nextInt(grounds.size()));
			
			Monster m = new Random().nextBoolean() ? new MonsterSlime() : new MonsterLobire();
			m.setPosition(place.getX(), place.getY() - m.getHitbox().h);
			addObject(m);
			
			monsterCounter ++;
			
			monsterTimer.reset();
		});
	}
	
	@Override
	public void update(double tpf)
	{
		super.update(tpf);
		
		if(monsterCounter < 5) monsterTimer.count(tpf);
	}
	
	@Override
	public void render(Screen screen)
	{
		screen.renderGUI(SpriteSheet.skyworldbg.getSprite(0, 0, 128, 128), 0, 0, Screen.DEFAULT_WIDTH, Screen.DEFAULT_HEIGHT, 0, 1);
		super.render(screen);
	}
	
}
