package com.messagereplicator.machinelearning;

import java.util.Random;

import com.messagereplicator.util.Matrix;

/**
 * 
 * @author user Joshua Cottrell
 * 
 *         The neural network structure used in machine learning
 *
 */
public class NeuralNetwork {

	/**
	 * The layers of weights of the neural network
	 */
	protected Matrix[] weights;
	protected Matrix[] biases;

	/**
	 * The configurations of the neural network
	 */
	private NNConfigs configs;

	/**
	 * @param layers The amount of nodes in each layer
	 */
	public NeuralNetwork(int[] layers, NNConfigs configs) {
		this.weights = setupMatrices(layers);
		this.biases = setupBiases(layers);
		this.configs = configs;
	}

	protected Matrix[] setupBiases(int[] layers) {
		Matrix[] biases = new Matrix[layers.length];

		for (int layerIndex = 1; layerIndex < layers.length; layerIndex++) {
			biases[layerIndex - 1] = new Matrix(layers[layerIndex], 1);
		}

		return biases;
	}

	/**
	 * Sets up the weights of the neural network according to the layers given
	 * 
	 * @param layers
	 * @return The matrices for the neural network's weights
	 */
	protected Matrix[] setupMatrices(int[] layers) {
		Matrix[] matrices = new Matrix[layers.length - 1];

		for (int layerIndex = 0; layerIndex < layers.length - 1; layerIndex++) {
			int curLayer = layers[layerIndex];
			int nextLayer = layers[layerIndex + 1];

			matrices[layerIndex] = initWeight(curLayer, nextLayer);
		}

		return matrices;
	}

	/**
	 * Initialises a weight matrix randomly with a mean of 0 and a variance of (1 / curLayer)
	 * 
	 * @param curLayer
	 * @param nextLayer
	 * @return The initial weights of a particular layer
	 */
	protected Matrix initWeight(int curLayer, int nextLayer) {
		Random random = new Random();
		float stdev = (float) Math.sqrt(1f / curLayer);

		float[][] weightValues = new float[nextLayer][curLayer];

		for (int y = 0; y < nextLayer; y++) {
			for (int x = 0; x < curLayer; x++) {
				weightValues[y][x] = (float) (random.nextGaussian() * stdev);
			}
		}

		return new Matrix(weightValues);
	}

	/**
	 * Trains the neural network given a training set of corresponding inputs and
	 * outputs
	 * 
	 * @param inputSets  The sets of the training inputs
	 * @param outputSets The sets of the training outputs
	 */
	public void train(Matrix[] inputSet, Matrix[] outputSet) {
		if (inputSet.length != outputSet.length) {
			throw new IllegalArgumentException("The input set must be the same size as the output set!");
		}

		int maxIterations = configs.iterationAmount;

		for (int iteration = 0; iteration < maxIterations; iteration++) {
			for (int setIndex = 0; setIndex < inputSet.length; setIndex++) {
				Matrix input = inputSet[setIndex];
				Matrix expectedOutput = inputSet[setIndex];

				Matrix[] layers = getLayers(input);

				// May be added later in logging
				// Matrix costs = configs.costFunction.perform(expectedOutput, realOutput);

				Matrix[] deltas = calculateDeltas(expectedOutput, layers);

				updateWeightsBiases(deltas, layers);
			}
		}

	}

	protected void updateWeightsBiases(Matrix[] deltas, Matrix[] layers) {
		float trainingRate = configs.trainingRate;

		for (int weightIndex = 0; weightIndex < weights.length; weightIndex++) {
			Matrix curWeights = weights[weightIndex];
			Matrix curBiases = biases[weightIndex];

			Matrix layer = layers[weightIndex].map(configs.getActivationFunction(weightIndex));
			Matrix delta = deltas[weightIndex];

			Matrix dWeight = delta.dot(layer.T()).multiply(trainingRate);
			
			Matrix newWeights = curWeights.sub(dWeight);
			Matrix newBiases = curBiases.sub(delta.multiply(trainingRate));

			this.weights[weightIndex] = newWeights;
			this.biases[weightIndex] = newBiases;
		}
	}

	protected Matrix[] calculateDeltas(Matrix expectedOutput, Matrix[] layers) {
		Matrix[] deltas = new Matrix[layers.length - 1];
		
		Matrix realOutput = layers[layers.length - 1].map(configs.getActivationFunction(layers.length - 1));

		Matrix costDeriv = configs.costFunctionDeriv.perform(expectedOutput, realOutput);
		Matrix outLayer = layers[layers.length - 1];
		deltas[deltas.length - 1] = costDeriv.multiply(outLayer.map(configs.getActivationFunctionDeriv(layers.length - 1)));

		for (int deltaIndex = deltas.length - 2; deltaIndex >= 0; deltaIndex--) {
			Matrix weights = this.weights[deltaIndex + 1];
			Matrix layerDeriv = layers[deltaIndex + 1].map(configs.getActivationFunctionDeriv(deltaIndex + 1));
			Matrix delta = deltas[deltaIndex + 1];

			Matrix newDelta = weights.T().dot(delta).multiply(layerDeriv);
			deltas[deltaIndex] = newDelta;
		}

		return deltas;
	}

	/**
	 * @param input
	 * @return The output of the neural network from the given input
	 */
	public Matrix getOutput(Matrix input) {
		Matrix[] layers = getLayers(input);

		return layers[layers.length - 1].map(configs.getActivationFunction(layers.length - 1));
	}

	protected Matrix[] getLayers(Matrix input) {
		Matrix[] layers = new Matrix[weights.length + 1];
		layers[0] = input;

		for (int layerIndex = 0; layerIndex < weights.length; layerIndex++) {
			layers[layerIndex + 1] = getLayer(layers[layerIndex], layerIndex);
		}

		return layers;
	}

	/**
	 * Works out the node values for a given layer, depending on the results of the
	 * previous layer
	 * 
	 * @param prevLayer
	 * @param layer
	 * @return A column matrix containing the values for the nodes in the layer
	 */
	protected Matrix getLayer(Matrix prevLayer, int layer) {
		Matrix mappedLayer = prevLayer.map(configs.getActivationFunction(layer));

		return weights[layer].dot(mappedLayer).add(biases[layer]);
	}

	/**
	 * @return The configs of the neural network
	 */
	protected NNConfigs getConfigs() {
		return configs;
	}
	
}
