package com.messagereplicator.util;

/**
 * 
 * @author user Joshua Cottrell
 * 
 * A class that allows the passing of maths functions into arguments
 *
 */
public abstract class Function<K> {

	/**
	 * Performs a mathematical function on each member of x
	 * 
	 * @param x
	 * @return f(x)
	 */
	@SuppressWarnings("unchecked")
	public abstract K perform(K... x);
	
}
