package com.messagereplicator.machinelearning;

import com.messagereplicator.util.Matrix;

/**
 * 
 * @author user Joshua Cottrell
 * 
 * The neural network structure used in machine learning
 *
 */
public class NeuralNetwork {

	/**
	 * The layers of weights of the neural network
	 */
	private Matrix[] weights;

	/**
	 * The configurations of the neural network
	 */
	private NNConfigs configs;
	
	/**
	 * @param layers The amount of nodes in each layer
	 */
	public NeuralNetwork(int[] layers) {
	}

	/**
	 * Trains the neural network given a training set of corresponding inputs and outputs
	 * 
	 * @param inputSets The sets of the training inputs
	 * @param outputSets The sets of the training outputs
	 */
	public void train(Matrix[] inputSets, Matrix[] outputSets) {
	}

	/**
	 * @param input
	 * @return The output of the neural network from the given input
	 */
	public Matrix getOutput(Matrix input) {
		return null;
	}
	
}
