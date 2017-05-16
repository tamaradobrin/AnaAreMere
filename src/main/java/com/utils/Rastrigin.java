package com.utils;

public class Rastrigin extends Function {

	private static final double a = -5.12;
	private static final double b = +5.12;

	public Rastrigin(int m) {
		this.m = m;
		this.numberOfBits = BinaryUtils.getNumberOfBits(a, b);
		this.A = a;
		this.B = b;
	}

	public double compute(int[] sol) {
		double sum = 10 * m;
		double[] x = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		for (int i = 0; i < m; i++) {
			sum += x[i] * x[i] - 10 * Math.cos(2 * Math.PI * x[i]);
		}
		return sum;
	}

	@Override
	public double compute(double[] x) {
		double sum = 10 * m;
		for (int i = 0; i < m; i++) {
			sum += x[i] * x[i] - 10 * Math.cos(2 * Math.PI * x[i]);
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
