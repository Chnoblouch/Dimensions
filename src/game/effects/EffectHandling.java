package game.effects;

import game.utils.SafeArrayList;

import java.util.ArrayList;

public class EffectHandling {
	
	private ArrayList<Effect> effects = new SafeArrayList<>();
	
	public void addEffect(Effect effect)
	{
		if(hasEffect(effect.getEffectID())) removeEffect(effect.getEffectID());
		effects.add(effect);
	}
	
	public ArrayList<Effect> getEffects()
	{
		return effects;
	}
	
	public void removeEffect(int id)
	{
		if(hasEffect(id)) removeEffect(getEffect(id));
	}
	
	public void removeEffect(Effect effect)
	{
		effects.remove(effect);
	}
	
	public boolean hasEffect(int id)
	{
		for(int i = 0; i < effects.size(); i++)
		{
			Effect e = effects.get(i);
			if(e.getEffectID() == id) return true;
		}
		
		return false;
	}
	
	public Effect getEffect(int id)
	{
		for(int i = 0; i < effects.size(); i++)
		{
			Effect e = effects.get(i);
			if(e.getEffectID() == id) return e;
		}
		
		return null;
	}
	
	public void updateEffects(double tpf)
	{
		for(int i = 0; i < effects.size(); i++)
		{
			Effect e = effects.get(i);
			
			e.update(tpf);
			if(e.isOver()) effects.remove(e);
		}
	}

}
