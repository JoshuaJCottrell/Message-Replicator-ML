package com.messagereplicator.util;

/**
 * 
 * @author user Joshua Cottrell
 *
 *         The matrix data structure
 *
 */
public class Matrix {

	/**
	 * The values that the matrix will store, values[row][column]
	 */
	private float[][] values;

	/**
	 * @param rows    The amount of rows the matrix has
	 * @param columns The amount of columns the matrix has
	 */
	public Matrix(int rows, int columns) {
		this.values = new float[rows][columns];
	}

	/**
	 * Initialises the matrix using a 2D float array
	 * 
	 * @param values The values of the matrix where values[row][column]
	 */
	public Matrix(float[][] values) {
		this.values = values;
	}

	/**
	 * Adds the corresponding the values within the current and other matrices to
	 * form a new matrix
	 * 
	 * @param otherMatrix
	 * @return The new matrix formed from adding the two matrices
	 */
	public Matrix add(Matrix otherMatrix) {
		int[] thisSize = this.size();
		int[] otherSize = otherMatrix.size();

		if (!(thisSize[0] == otherSize[0] && thisSize[1] == otherSize[1])) {
			throw new IllegalArgumentException("The matrices must be the same size!");
		}

		float[][] newValues = new float[thisSize[0]][thisSize[1]];

		for (int row = 0; row < thisSize[0]; row++) {
			for (int column = 0; column < thisSize[1]; column++) {
				newValues[row][column] = this.get(row, column) + otherMatrix.get(row, column);
			}
		}

		return new Matrix(newValues);
	}

	/**
	 * Subtracts the corresponding the values within the current and other matrices to
	 * form a new matrix
	 * 
	 * @param otherMatrix
	 * @return The new matrix formed from adding the two matrices
	 */
	public Matrix sub(Matrix otherMatrix) {
		int[] thisSize = this.size();
		int[] otherSize = otherMatrix.size();

		if (!(thisSize[0] == otherSize[0] && thisSize[1] == otherSize[1])) {
			throw new IllegalArgumentException("The matrices must be the same size!");
		}

		float[][] newValues = new float[thisSize[0]][thisSize[1]];

		for (int row = 0; row < thisSize[0]; row++) {
			for (int column = 0; column < thisSize[1]; column++) {
				newValues[row][column] = this.get(row, column) - otherMatrix.get(row, column);
			}
		}

		return new Matrix(newValues);
	}
	

	/**
	 * Multiplies the corresponding the values within the current and other matrices
	 * to form a new matrix
	 * 
	 * @param otherMatrix
	 * @return The new matrix formed from multiplying the two matrices
	 */
	public Matrix multiply(Matrix otherMatrix) {
		int[] thisSize = this.size();
		int[] otherSize = otherMatrix.size();

		if (!(thisSize[0] == otherSize[0] && thisSize[1] == otherSize[1])) {
			throw new IllegalArgumentException("The matrices must be the same size!");
		}

		float[][] newValues = new float[thisSize[0]][thisSize[1]];

		for (int row = 0; row < thisSize[0]; row++) {
			for (int column = 0; column < thisSize[1]; column++) {
				newValues[row][column] = this.get(row, column) * otherMatrix.get(row, column);
			}
		}

		return new Matrix(newValues);
	}

	/**
	 * Forms a new matrix by multiplying all the values of {@link #values} by the
	 * scalar given
	 * 
	 * @param scalar
	 * @return The new matrix formed from multiplying the matrix with the scalar
	 */
	public Matrix multiply(float scalar) {
		Function<Float> multiplyFunction = new Function<Float>() {
			@Override
			public Float perform(Float... x) {
				return x[0] * scalar;
			}
		};

		return this.map(multiplyFunction);
	}

	/**
	 * (current) dot (other)
	 * 
	 * @param otherMatrix
	 * @return The new matrix formed from the dot product of the current and other
	 *         matrices
	 */
	public Matrix dot(Matrix otherMatrix) {
		int[] thisSize = this.size();
		int[] otherSize = otherMatrix.size();

		if (thisSize[1] != otherSize[0]) {
			throw new IllegalArgumentException("The amount of columns in this matrix must be equal to the amount of rows in the other matrix");
		}

		float[][] newValues = new float[thisSize[0]][otherSize[1]];

		for (int newColumn = 0; newColumn < otherSize[1]; newColumn++) {
			for (int newRow = 0; newRow < thisSize[0]; newRow++) {
				for (int sharedIndex = 0; sharedIndex < thisSize[1]; sharedIndex++) {
					newValues[newRow][newColumn] += this.get(newRow, sharedIndex) * otherMatrix.get(sharedIndex, newColumn);
				}
			}
		}

		return new Matrix(newValues);
	}

	/**
	 * Forms a new matrix by performing the function on every value within the
	 * matrix
	 * 
	 * @param function
	 * @return The new matrix performed by mapping the function onto the matrix
	 */
	public Matrix map(Function<Float> function) {
		int[] size = this.size();

		float[][] newValues = new float[size[0]][size[1]];

		for (int row = 0; row < size[0]; row++) {
			for (int column = 0; column < size[1]; column++) {
				newValues[row][column] = function.perform(this.get(row, column));
			}
		}

		return new Matrix(newValues);
	}

	/**
	 * Swaps the rows and columns
	 * 
	 * @return The transpose of the matrix
	 */
	public Matrix T() {
		int[] size = this.size();

		float[][] newValues = new float[size[1]][size[0]];

		for (int row = 0; row < size[0]; row++) {
			for (int column = 0; column < size[1]; column++) {
				newValues[column][row] = this.get(row, column);
			}
		}

		return new Matrix(newValues);
	}
	
	/**
	 * @param column
	 * @param row
	 * @return The value at the column and row given
	 */
	public float get(int row, int column) {
		return values[row][column];
	}

	/**
	 * @return { rows, columns }
	 */
	public int[] size() {
		return new int[] { values.length, values[0].length };
	}

	@Override
	public boolean equals(Object obj) {
		Matrix otherMatrix = (Matrix) obj;

		int[] thisSize = this.size();
		int[] otherSize = otherMatrix.size();

		if (!(thisSize[0] == otherSize[0] && thisSize[1] == otherSize[1])) {
			return false;
		}

		for (int row = 0; row < thisSize[0]; row++) {
			for (int column = 0; column < thisSize[1]; column++) {
				float diff = Math.abs(this.get(row, column) - otherMatrix.get(row, column));
				
				if (diff > 0.000001f) {
					return false;
				}
			}
		}
		
		return true;
	}

	@Override
	public Matrix clone() {
		int[] size = this.size();

		float[][] newValues = new float[size[0]][size[1]];

		for (int row = 0; row < size[0]; row++) {
			for (int column = 0; column < size[1]; column++) {
				newValues[row][column] = this.get(row, column);
			}
		}

		return new Matrix(newValues);
	}

}
