package com.utils;

public class Rosenbrock extends Function {

	private static final double a = -2.048;
	private static final double b = +2.048;

	public Rosenbrock(int m) {
		this.m = m;
		this.numberOfBits = BinaryUtils.getNumberOfBits(a, b);
		this.A = a;
		this.B = b;
	}

	@Override
	public double compute(int[] sol) {
		double sum = 0;
		double[] x = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		for (int i = 0; i < m - 1; i++) {
			sum += 100 * Math.pow(x[i + 1] - x[i] * x[i], 2) + Math.pow(1 - x[i], 2);
		}
		return sum;
	}

	@Override
	public double compute(double[] x) {
		double sum = 0;
		for (int i = 0; i < m - 1; i++) {
			sum += 100 * Math.pow(x[i + 1] - x[i] * x[i], 2) + Math.pow(1 - x[i], 2);
		}
		return sum;
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

}
