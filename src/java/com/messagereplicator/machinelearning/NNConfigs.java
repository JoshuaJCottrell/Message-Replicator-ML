package com.messagereplicator.machinelearning;

import com.messagereplicator.util.Function;
import com.messagereplicator.util.Matrix;

/**
 * 
 * @author user Joshua Cottrell
 * 
 *         The configurations for the neural network
 *
 */
public class NNConfigs {

	/**
	 * The amount of iterations that will occur in the training function
	 */
	public int iterationAmount;
	/**
	 * The training rate the neural network will be trained to How much the weights
	 * will be updated
	 */
	public float trainingRate;

	/**
	 * The activation functions that will be applied to each layer's output
	 */
	public Function<Float>[] activationFunctions;
	/**
	 * The corresponding derivative funciton for {@link #activationFunctions}
	 */
	public Function<Float>[] activationFunctionsDerivs;

	// public Function costFunction;
	
	/**
	 * The derivative of the cost function used
	 */
	public Function<Matrix> costFunctionDeriv;

	/**
	 * Sets up the neural network's configurations
	 * 
	 * @param amountOfLayers
	 */
	@SuppressWarnings("unchecked")
	public NNConfigs(int amountOfLayers) {
		this.activationFunctions = new Function[amountOfLayers];
		this.activationFunctionsDerivs = new Function[amountOfLayers];
	}

	/**
	 * @param layer
	 * @return The activation function of a particular layer, the first layer's as a default
	 */
	public Function<Float> getActivationFunction(int layer) {
		Function<Float> activation = activationFunctions[layer];
		
		if (activation == null) {
			activation = activationFunctions[0];
		}

		return activation;
	}

	/**
	 * @param layer
	 * @return The derivative of the activation function of a particular layer, the first layer's as a default
	 */
	public Function<Float> getActivationFunctionDeriv(int layer) {
		Function<Float> activation = activationFunctionsDerivs[layer];

		if (activation == null) {
			activation = activationFunctionsDerivs[0];
		}

		return activation;
	}

}
