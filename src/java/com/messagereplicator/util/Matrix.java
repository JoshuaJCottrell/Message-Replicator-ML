package com.messagereplicator.util;

/**
 * 
 * @author user Joshua Cottrell
 *
 *	The matrix data structure
 *
 */
public class Matrix {

	/**
	 * The values that the matrix will store
	 * values[column][row]
	 */
	private float[][] values;
	
	/**
	 * @param rows The amount of rows the matrix has
	 * @param columns The amount of columns the matrix has
	 */
	public Matrix(int rows, int columns) {
	}

	/**
	 * Adds the corresponding the values within the current and other matrices to
	 * form a new matrix
	 * 
	 * @param otherMatrix
	 * @return The new matrix formed from adding the two matrices
	 */
	public Matrix add(Matrix otherMatrix) {
		return null;
	}

	/**
	 * Multiplies the corresponding the values within the current and other matrices to
	 * form a new matrix
	 * 
	 * @param otherMatrix
	 * @return The new matrix formed from multiplying the two matrices
	 */
	public Matrix multiply(Matrix otherMatrix) {
		return null;
	}
	
	/**
	 * Forms a new matrix by multiplying all the values of {@link #values} by the scalar given
	 * 
	 * @param scalar
	 * @return The new matrix formed from multiplying the matrix with the scalar
	 */
	public Matrix multiply(double scalar) {
		return null;
	}
	
	/**
	 * (current) dot (other)
	 * 
	 * @param otherMatrix
	 * @return The new matrix formed from the dot product of the current and other matrices
	 */
	public Matrix dot(Matrix otherMatrix) {
		return null;
	}
	
	/**
	 * Forms a new matrix by performing the function on every value within the matrix
	 * 
	 * @param function
	 * @return The new matrix performed by mapping the function onto the matrix
	 */
	public Matrix map(Function function) {
		return null;
	}

	@Override
	public Matrix clone() {
		return null;
	}
	
}
