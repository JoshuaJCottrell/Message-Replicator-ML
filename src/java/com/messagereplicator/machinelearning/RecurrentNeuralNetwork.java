package com.messagereplicator.machinelearning;

import com.messagereplicator.util.Function;
import com.messagereplicator.util.Matrix;

/**
 * 
 * @author user Joshua Cottrell
 * 
 *         The recurrent neural network structure used in machine learning This
 *         is used when either the input's or output's size is variable
 * 
 *         This implementation will be many inputs to one output
 *
 */
public class RecurrentNeuralNetwork {

	private NeuralNetwork neuralNetwork;
	private Matrix[] recurrentLayers;

	/**
	 * @param layers The amount of nodes in each layer
	 */
	public RecurrentNeuralNetwork(int[] layers, NNConfigs configs) {
		this.neuralNetwork = new NeuralNetwork(layers, configs);
		this.recurrentLayers = setupRecurrentLayers(layers);
	}

	/**
	 * @param layers
	 * @return The recurrent layers of the neural network
	 */
	protected Matrix[] setupRecurrentLayers(int[] layers) {
		Matrix[] recurrentLayers = new Matrix[layers.length - 2];

		for (int i = 1; i < layers.length - 1; i++) {
			int nodes = layers[i];

			recurrentLayers[i - 1] = neuralNetwork.initWeight(nodes, nodes);
		}

		return recurrentLayers;
	}

	/**
	 * Trains the neural network given a training set of corresponding inputs and
	 * outputs
	 * 
	 * @param inputSets  The sets of the training inputs
	 * @param outputSets The sets of the training outputs
	 */
	public void train(Matrix[][] inputSets, Matrix[] outputSets) {
		if (inputSets.length != outputSets.length) {
			throw new IllegalArgumentException("The input set must be the same size as the output set!");
		}

		int maxIterations = neuralNetwork.getConfigs().iterationAmount;
		float trainingRate = neuralNetwork.getConfigs().trainingRate;

		for (int iteration = 0; iteration < maxIterations; iteration++) {
			for (int setIndex = 0; setIndex < inputSets.length; setIndex++) {
				Matrix[] inputSet = inputSets[setIndex];
				Matrix[][] allLayers = getAllLayers(inputSet);

				Matrix[][] allDeltas = new Matrix[inputSet.length][recurrentLayers.length + 1];

				allDeltas[allDeltas.length - 1] = neuralNetwork.calculateDeltas(outputSets[setIndex], allLayers[allLayers.length - 1]);
				neuralNetwork.updateWeightsBiases(allDeltas[allDeltas.length - 1], allLayers[allLayers.length - 1]);

				for (int layerIndex = allLayers.length - 2; layerIndex >= 0; layerIndex--) {
					Matrix[] deltas = allDeltas[layerIndex + 1];

					for (int recLayerIndex = 0; recLayerIndex < recurrentLayers.length; recLayerIndex++) {
						Matrix layer = allLayers[layerIndex][recLayerIndex + 1].map(neuralNetwork.getConfigs().getActivationFunction(recLayerIndex));
						Matrix delta = deltas[recLayerIndex];

						Matrix dRecWeight = delta.dot(layer.T()).multiply(trainingRate);

						this.recurrentLayers[recLayerIndex] = this.recurrentLayers[recLayerIndex].sub(dRecWeight);
					}

					neuralNetwork.updateWeightsBiases(deltas, allLayers[layerIndex]);

					allDeltas[layerIndex] = calculateNewDeltas(deltas, allLayers[layerIndex]);
				}

			}
		}
		
	}

	protected Matrix[] calculateNewDeltas(Matrix[] prevDeltas, Matrix[] layers) {
		Matrix[] nDeltas = new Matrix[prevDeltas.length];

		for (int i = 0; i < recurrentLayers.length; i++) {
			Matrix delta = (recurrentLayers[i].T().dot(prevDeltas[i])).multiply(layers[i + 1].map(neuralNetwork.getConfigs().getActivationFunctionDeriv(i + 1)));
			nDeltas[i] = delta;
		}

		return nDeltas;
	}

	/**
	 * @param input
	 * @return The output of the neural network from the given input
	 */
	public Matrix getOutput(Matrix[] inputSets) {
		return null;
	}

	protected Matrix[][] getAllLayers(Matrix[] inputs) {
		Matrix[][] allLayers = new Matrix[inputs.length][];

		Matrix[] prevSeqLayers = null;

		for (int inputIndex = 0; inputIndex < inputs.length; inputIndex++) {
			Matrix[] layers = getSingleLayers(inputs[inputIndex], prevSeqLayers);

			allLayers[inputIndex] = layers;

			prevSeqLayers = new Matrix[recurrentLayers.length];

			for (int prevSeqIndex = 0; prevSeqIndex < recurrentLayers.length; prevSeqIndex++) {
				prevSeqLayers[prevSeqIndex] = layers[prevSeqIndex + 1];
			}
		}

		return allLayers;
	}

	protected Matrix[] getSingleLayers(Matrix input, Matrix[] prevSeqLayers) {
		Matrix[] layers = new Matrix[recurrentLayers.length + 2];
		layers[0] = input;

		for (int layerIndex = 0; layerIndex < recurrentLayers.length + 1; layerIndex++) {
			Matrix prevSeqLayer = null;

			if (layerIndex < recurrentLayers.length && prevSeqLayers != null) {
				prevSeqLayer = prevSeqLayers[layerIndex];
			}

			layers[layerIndex + 1] = getLayer(layers[layerIndex], prevSeqLayer, layerIndex);
		}

		return layers;
	}

	protected Matrix getLayer(Matrix prevLayer, Matrix prevSeqLayer, int layer) {
		Matrix output = neuralNetwork.getLayer(prevLayer, layer);

		if (prevSeqLayer != null) {
			Matrix mappedInput = prevSeqLayer.map(neuralNetwork.getConfigs().getActivationFunction(layer));
			output = output.add(recurrentLayers[layer].dot(mappedInput));
		}

		return output;
	}

//	@SuppressWarnings("javadoc")
//	public static void main(String[] args) {
//		int[] layers = new int[] { 2, 3, 2 };
//
//		Function<Float> sigmoid = new Function<Float>() {
//			@Override
//			public Float perform(Float... x) {
//				return (float) (1f / Math.exp(-x[0]));
//			};
//		};
//
//		Function<Float> sigmodiDeriv = new Function<Float>() {
//			@Override
//			public Float perform(Float... x) {
//				return sigmoid.perform(x) * (1 - sigmoid.perform(x));
//			}
//		};
//
//		Function<Matrix> sqRtDeriv = new Function<Matrix>() {
//			@Override
//			public Matrix perform(Matrix... x) {
//				Matrix exp = x[0];
//				Matrix real = x[1];
//
//				Matrix subbed = real.sub(exp);
//
//				return subbed.multiply(2f);
//			}
//		};
//
//		NNConfigs configs = new NNConfigs(layers.length);
//		configs.iterationAmount = 1;
//		configs.activationFunctions[0] = sigmoid;
//		configs.activationFunctionsDerivs[0] = sigmodiDeriv;
//		configs.costFunctionDeriv = sqRtDeriv;
//		configs.trainingRate = 0.1f;
//
//		RecurrentNeuralNetwork rnn = new RecurrentNeuralNetwork(layers, configs);
//
//		Matrix m0 = new Matrix(new float[][] { new float[] { 0 }, new float[] { 0 } });
//		Matrix m1 = new Matrix(new float[][] { new float[] { 1 }, new float[] { 1 } });
//
//		Matrix[] inputSets = new Matrix[] { m0, m1 };
//		Matrix outputSet = new Matrix(new float[][] { new float[] { 1 }, new float[] { 1 } });
//
//		Matrix[][] allLayers = rnn.getAllLayers(inputSets);
//
//		rnn.train(new Matrix[][] { inputSets, inputSets }, new Matrix[] { outputSet, outputSet });
//
//		System.out.println();
//	}

}
