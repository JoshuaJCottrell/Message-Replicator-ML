package com.messagereplicator.machinelearning;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.messagereplicator.util.Function;
import com.messagereplicator.util.Matrix;

class NeuralNetworkTest {

	NeuralNetwork testNet;

	@BeforeEach
	void setUp() throws Exception {
		int[] layers = new int[] { 2, 3, 2 };

		Function<Float> relu = new Function<Float>() {
			@Override
			public Float perform(Float... x) {
				return x[0] > 0f ? x[0] : 0f;
			};
		};

		Function<Float> reluDeriv = new Function<Float>() {
			@Override
			public Float perform(Float... x) {
				return x[0] > 0f ? 1f : 0f;
			}
		};

		Function<Matrix> sqRt = new Function<Matrix>() {
			@Override
			public Matrix perform(Matrix... x) {
				Matrix exp = x[0];
				Matrix real = x[1];

				Matrix subbed = real.sub(exp);

				return subbed.multiply(subbed);
			}
		};

		NNConfigs configs = new NNConfigs(layers.length);
		configs.iterationAmount = 1;
		configs.activationFunctions[0] = relu;
		configs.activationFunctionsDerivs[0] = reluDeriv;
		configs.costFunctionDeriv = sqRt;
		configs.trainingRate = 0.1f;

		testNet = new NeuralNetwork(layers, configs);

		float[][] w0 = new float[][] { new float[] { 0.1f, 0.2f }, new float[] { 0.3f, 0.4f }, new float[] { 0.5f, 0.6f } };

		Matrix m0 = new Matrix(w0);
		Matrix m1 = m0.T();

		testNet.weights[0] = m0;
		testNet.weights[1] = m1;
	}

	@Test
	void testSetupBiases() {
		int outputs = 10;

		Matrix biasMatrix = testNet.setupBiases(new int[] { 1, outputs })[0];

		int[] size = biasMatrix.size();

		boolean testSize = size[0] == outputs && size[1] == 1;

		String errSize = String.format("The matrix size has been initialised wrong!\nShould be [ %d, %d ], actually [ %d, %d ]", outputs, 1, size[0], size[1]);

		assertTrue(errSize, testSize);
	}

	@Test
	void testInitWeight() {
		int curLayer = 5;
		int nextLayer = 6;
		Matrix weightMatrix = testNet.initWeight(curLayer, nextLayer);
		int[] size = weightMatrix.size();

		boolean testSize = size[0] == curLayer && size[1] == nextLayer;

		String errSize = String.format("The matrix size has been initialised wrong!\nShould be [ %d, %d ], actually [ %d, %d ]", curLayer, nextLayer, size[0], size[1]);
		assertTrue(errSize, testSize);

		float expMean = 0;
		float expVar = 1f / curLayer;

		float realMean = 0;
		float realVar = 0;

		for (int y = 0; y < size[1]; y++) {
			for (int x = 0; x < size[0]; x++) {
				realMean += weightMatrix.get(x, y);
			}
		}

		realMean /= curLayer * nextLayer;

		for (int y = 0; y < size[1]; y++) {
			for (int x = 0; x < size[0]; x++) {
				float tmp = (weightMatrix.get(x, y) - realMean);
				realVar += tmp * tmp;
			}
		}

		realVar /= curLayer * nextLayer;

		float meanErr = (realMean - expMean) * (realMean - expMean);
		float varErr = (realVar - expVar) * (realVar - expVar);

		boolean testMean = meanErr < 0.01f;
		boolean testVar = varErr < 0.01f;

		String errMean = String.format("The values of the matrix have been initialised wrong!\nMean error should be under 0.01, actually %f", meanErr);

		String errVar = String.format("The values of the matrix have been initialised wrong!\nVariance error should be under 0.01, actually %f", varErr);

		assertTrue(errMean, testMean);
		assertTrue(errVar, testVar);

	}

	@Test
	void testGetLayer() {
		Matrix input = new Matrix(new float[][] { new float[] { 1f }, new float[] { 1f } });

		Matrix output = testNet.getLayer(input, 0);

		Matrix expOutput = new Matrix(new float[][] { new float[] { 0.3f }, new float[] { 0.7f }, new float[] { 1.1f } });
		
		boolean testOutput = output.equals(expOutput);
		
		String errOutput = String.format("The output matrix is incorrect!\nExpected: {{0.3},{0.7},{1.1}}, Actually: {{%f},{%f},{%f}}",
				output.get(0, 0), output.get(1, 0), output.get(2, 0));
		
		assertTrue(errOutput, testOutput);
	}

	@Test
	void testGetOutput() {
		Matrix input = new Matrix(new float[][] { new float[] { 1f }, new float[] { 1f } });

		Matrix output = testNet.getOutput(input);

		Matrix expOutput = new Matrix(new float[][] { new float[] { 0.79f }, new float[] { 1.0f } });
		
		boolean testOutput = output.equals(expOutput);
		
		String errOutput = String.format("The output matrix is incorrect!\nExpected: {{0.79},{1.00}}, Actually: {{%f},{%f}}",
				output.get(0, 0), output.get(1, 0));
		
		assertTrue(errOutput, testOutput);
	}

}
