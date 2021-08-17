package com.messagereplicator.util;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class MatrixTest {

	@Test
	void testAdd() {
		float[][] values1 = new float[][] {
			new float[] {1, 2, 3, 4},
			new float[] {5, 6, 7, 8},
		};
		
		float[][] values2 = new float[][] {
			new float[] {2, 4, 6, 8},
			new float[] {10, 12, 14, 16},
		};
		
		Matrix m1 = new Matrix(values1);
		Matrix m2 = new Matrix(values2);
		
		assertTrue(m1.add(m1).equals(m2));
	}

	@Test
	void testMultiplyMatrix() {
		float[][] values1 = new float[][] {
			new float[] {1, 2, 3, 4},
			new float[] {5, 6, 7, 8},
		};
		
		float[][] values2 = new float[][] {
			new float[] {1, 4, 9, 16},
			new float[] {25, 36, 49, 64},
		};
		
		Matrix m1 = new Matrix(values1);
		Matrix m2 = new Matrix(values2);
		
		assertTrue(m1.multiply(m1).equals(m2));
	}

	@Test
	void testMultiplyFloat() {
		float[][] values1 = new float[][] {
			new float[] {1, 2, 3, 4},
			new float[] {5, 6, 7, 8},
		};
		
		float[][] values2 = new float[][] {
			new float[] {2, 4, 6, 8},
			new float[] {10, 12, 14, 16},
		};
		
		float[][] values3 = new float[][] {
			new float[] {3, 6, 9, 12},
			new float[] {15, 18, 21, 24},
		};

		Matrix m1 = new Matrix(values1);
		Matrix m2 = new Matrix(values2);
		Matrix m3 = new Matrix(values3);

		assertTrue(m1.multiply(2).equals(m2));
		assertTrue(m1.multiply(3).equals(m3));
		
	}

	@Test
	void testDot() {
		float[][] values1 = new float[][] {
			new float[] {1, 2, 3, 4},
			new float[] {5, 6, 7, 8},
		};
		
		float[][] values2 = new float[][] {
			new float[] {1, 5},
			new float[] {2, 6},
			new float[] {3, 7},
			new float[] {4, 8},
		};

		float[][] values3 = new float[][] {
			new float[] {30, 70},
			new float[] {70, 174}
		};

		Matrix m1 = new Matrix(values1);
		Matrix m2 = new Matrix(values2);
	
		Matrix m3 = m1.dot(m2);
		Matrix m4 = new Matrix(values3);
		
		assertTrue(m3.equals(m4));
		
	}

	@Test
	void testMap() {
		Function function1 = new Function() {
			@Override
			public float perform(float x) {
				return 2 * x + 1;
			}
		};

		float[][] values1 = new float[][] {
			new float[] {1, 2, 3, 4},
			new float[] {5, 6, 7, 8},
		};

		float[][] values2 = new float[][] {
			new float[] {3, 5, 7, 9},
			new float[] {11, 13, 15, 17},
		};
		
		Matrix m1 = new Matrix(values1).map(function1);
		Matrix m2 = new Matrix(values2);
		
		assertTrue(m1.equals(m2));
		
	}

	@Test
	void testT() {
		float[][] values1 = new float[][] {
			new float[] {1, 2, 3, 4},
			new float[] {5, 6, 7, 8},
		};
		
		float[][] values2 = new float[][] {
			new float[] {1, 5},
			new float[] {2, 6},
			new float[] {3, 7},
			new float[] {4, 8},
		};

		Matrix m1 = new Matrix(values1).T();
		Matrix m2 = new Matrix(values2);

		assertTrue(m1.equals(m2));
		
	}
	
	@Test
	void testEqualsObject() {
		float[][] values1 = new float[][] {
			new float[] {1, 2, 3, 4},
			new float[] {5, 6, 7, 8},
		};

		Matrix m1 = new Matrix(values1);
		Matrix m2 = m1.clone();

		assertTrue(m1.equals(m2));
		
		float[][] values2 = new float[][] {
			new float[] {2, 3, 4},
			new float[] {5, 6, 7}
		};
		
		Matrix m3 = new Matrix(values2);
		
		assertTrue(!m1.equals(m3));
		
	}

}
