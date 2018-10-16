package game.utils;

import java.util.ArrayList;
import java.util.function.Consumer;

public class SafeArrayList<E>
extends ArrayList<E>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public void forEach(Consumer<? super E> consumer)
	{
		for (int i = 0; i < size(); i++) {
			consumer.accept(get(i));
		}
	}
}
