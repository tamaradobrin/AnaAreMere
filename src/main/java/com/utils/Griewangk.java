package com.utils;

public class Griewangk extends Function{

	private static final double a = -600;
	private static final double b = +600;
	
	public Griewangk(int m) {
		this.m=m;
		this.numberOfBits=BinaryUtils.getNumberOfBits(a, b);
	}
	
	@Override
	public double compute(int[] sol) {
		double sum = 0, prod = 1;
		double[] x = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		for (int i = 0; i < m; i++) {
			sum += (x[i] * x[i]) / 4000;
			prod *= Math.cos(x[i] / Math.sqrt(i + 1));
		}
		return sum - prod + 1;
	}
	
	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

}
