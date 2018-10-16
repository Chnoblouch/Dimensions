package game.levels;

import java.util.ArrayList;
import java.util.Random;

import game.Game;
import game.creature.MonsterLobire;
import game.creature.Monster;
import game.creature.Player;
import game.creature.MonsterSlime;
import game.creature.NPCAnyrava;
import game.creature.NPCBruno;
import game.creature.NPCJessica;
import game.environment.GrassGround;
import game.obj.Anvil;
import game.obj.GameObject;
import game.obj.Portal;
import game.obj.Workbench;
import game.utils.Block;
import game.utils.MapLoader;
import game.utils.SafeArrayList;
import game.utils.TimeCounter;

public class LevelOverworld 
extends Level {
	
	private Player player;
	
	private TimeCounter monsterTimer;

	public LevelOverworld(Game game)
	{
		super(game, 0, "dim_overworld");
		
		game.getMaps()[0].onLevel(this);
		
//		Slime slime = new Slime();
//		slime.setPosition(CENTER, CENTER + (Block.SIZE * 6));
//		addObject(slime);
		
		if(!game.exists())
		{
			Portal darkworld = new Portal();
			darkworld.setPosition(CENTER, CENTER + (Block.SIZE * 12));
			darkworld.setLevel(1);
			addObject(darkworld);
			
			Portal skyworld = new Portal();
			skyworld.setPosition(CENTER + (Block.SIZE * 8), CENTER - (Block.SIZE * 16));
			skyworld.setLevel(2);
			addObject(skyworld);
			
			Portal frostworld = new Portal();
			frostworld.setPosition(CENTER + (Block.SIZE * 19), CENTER - (Block.SIZE * 3));
			frostworld.setLevel(3);
			addObject(frostworld);
			
			NPCBruno bruno = new NPCBruno();
			bruno.setPosition(CENTER - (Block.SIZE * 4), CENTER + (Block.SIZE * 8));
			addObject(bruno);
			
			NPCAnyrava anyrava = new NPCAnyrava();
			anyrava.setPosition(CENTER, CENTER + (Block.SIZE * 8));
			addObject(anyrava);
			
			NPCJessica jessica = new NPCJessica();
			jessica.setPosition(CENTER + (Block.SIZE * 4), CENTER + (Block.SIZE * 8));
			addObject(jessica);
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
		
		if(monsterCounter < 15) monsterTimer.count(tpf);
	}
	
}
