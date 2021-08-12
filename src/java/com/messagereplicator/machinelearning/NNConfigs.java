package com.messagereplicator.machinelearning;

import com.messagereplicator.util.Function;

/**
 * 
 * @author user Joshua Cottrell
 * 
 * The configurations for the neural network
 *
 */
public class NNConfigs {

	/**
	 * The amount of iterations that will occur in the training function
	 */
	public int iterationAmount;
	/**
	 * The training rate the neural network will be trained to
	 * How much the weights will be updated
	 */
	public double trainingRate;
	
	/**
	 * The activation functions that will be applied to each layer's output
	 */
	public Function[] activationFunctions;
	/**
	 * The corresponding derivative funciton for {@link #activationFunctions}
	 */
	public Function[] activationFunctionsDerivs;
	
}
