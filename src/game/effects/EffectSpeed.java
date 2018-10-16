package game.effects;

import game.TextLoader;

public class EffectSpeed
extends Effect {

	public EffectSpeed(double durationLeft) 
	{
		super(durationLeft);
		setEffectID(ID_SPEED);
		setName(TextLoader.getText("effect_speed"));
	}
	
}
