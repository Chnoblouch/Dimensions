package game.particle;

import game.obj.GameObject;

public class Particle
extends GameObject {
	
	@Override
	public boolean updateOutside()
	{
		return true;
	}

}
