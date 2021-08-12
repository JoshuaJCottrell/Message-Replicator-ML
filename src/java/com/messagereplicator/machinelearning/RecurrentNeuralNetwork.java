package com.messagereplicator.machinelearning;

import com.messagereplicator.util.Matrix;

/**
 * 
 * @author user Joshua Cottrell
 * 
 * The recurrent neural network structure used in machine learning
 * This is used when either the input's or output's size is variable
 *
 */
public class RecurrentNeuralNetwork {

	private NeuralNetwork neuralNetwork;
	private Matrix[] recurrentLayers;
	
	/**
	 * @param layers The amount of nodes in each layer
	 */
	public RecurrentNeuralNetwork(int[] layers) {
	}

	/**
	 * Trains the neural network given a training set of corresponding inputs and outputs
	 * 
	 * @param inputSets The sets of the training inputs
	 * @param outputSets The sets of the training outputs
	 */
	public void train(Matrix[][] inputSets, Matrix[][] outputSets) {
	}

	/**
	 * @param input
	 * @return The output of the neural network from the given input
	 */
	public Matrix[] getOutputs(Matrix[] inputSets) {
		return null;
	}
	
}
