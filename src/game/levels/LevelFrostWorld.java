package game.levels;

import java.util.ArrayList;
import java.util.Random;

import game.Game;
import game.creature.Monster;
import game.creature.MonsterRogo;
import game.creature.MonsterWalkingEye;
import game.creature.Player;
import game.environment.DarkGround;
import game.environment.SnowGround;
import game.obj.Portal;
import game.utils.Block;
import game.utils.SafeArrayList;
import game.utils.TimeCounter;

public class LevelFrostWorld 
extends Level {
	
	private Player player;
	
	private TimeCounter monsterTimer;

	public LevelFrostWorld(Game game)
	{
		super(game, 3, "dim_iceworld");
		
		game.getMaps()[3].onLevel(this);
		
		if(!game.exists())
		{
			Portal portal = new Portal();
			portal.setPosition(CENTER, CENTER + (Block.SIZE * 8));
			portal.setLevel(0);
			addObject(portal);
		}
		
		monsterTimer = new TimeCounter(5000, () ->
		{
			ArrayList<SnowGround> grounds = new SafeArrayList<>();
			
			for(int i = 0; i < objects.size(); i++)
			{
				if(objects.get(i) instanceof SnowGround) grounds.add((SnowGround) objects.get(i));
			}
			
			SnowGround place = grounds.get(new Random().nextInt(grounds.size()));
			
			Monster m = new MonsterRogo();
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
