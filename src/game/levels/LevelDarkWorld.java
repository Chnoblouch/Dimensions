package game.levels;

import java.util.ArrayList;
import java.util.Random;

import game.Game;
import game.creature.MonsterLobire;
import game.creature.Monster;
import game.creature.Player;
import game.creature.MonsterSlime;
import game.creature.MonsterWalkingEye;
import game.environment.DarkGround;
import game.environment.GrassGround;
import game.obj.Portal;
import game.utils.Block;
import game.utils.SafeArrayList;
import game.utils.TimeCounter;

public class LevelDarkWorld 
extends Level {
	
	private Player player;
	
	private TimeCounter monsterTimer;

	public LevelDarkWorld(Game game)
	{
		super(game, 1, "dim_darkworld");
		
		game.getMaps()[1].onLevel(this);
		
		if(!game.exists())
		{
			Portal portal = new Portal();
			portal.setPosition(CENTER, CENTER + (Block.SIZE * 12));
			portal.setLevel(0);
			addObject(portal);
		}
		
		monsterTimer = new TimeCounter(5000, () ->
		{
			ArrayList<DarkGround> grounds = new SafeArrayList<>();
			
			for(int i = 0; i < objects.size(); i++)
			{
				if(objects.get(i) instanceof DarkGround) grounds.add((DarkGround) objects.get(i));
			}
			
			DarkGround place = grounds.get(new Random().nextInt(grounds.size()));
			
			Monster m = new MonsterWalkingEye();
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
		
		if(monsterCounter < 15) monsterTimer.count(tpf);
	}
	
}
