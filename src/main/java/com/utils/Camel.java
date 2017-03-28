package com.utils;

public class Camel extends Function {

	private static final double a1 = -3;
	private static final double b1 = +3;
	private static final double a2 = -2;
	private static final double b2 = +2;
	public int numberOfBits1;
	public int numberOfBits2;

	public Camel(int m) {
		this.m = m;
		this.numberOfBits1 = BinaryUtils.getNumberOfBits(a1, b1);
		this.numberOfBits2 = BinaryUtils.getNumberOfBits(a2, b2);
		this.numberOfBits = numberOfBits1 + numberOfBits2;
	}

	@Override
	public double compute(int[] sol) {
		double[] x = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, sol, numberOfBits1, numberOfBits2);
		return (4 - 2.1 * Math.pow(x[0], 2) + Math.pow(x[0], 4) / 3) * Math.pow(x[0], 2) + x[0] * x[1]
				+ (-4 + 4 * Math.pow(x[1], 2)) * Math.pow(x[1], 2);
	}

	public double getA1() {
		return a1;
	}

	public double getB1() {
		return b1;
	}

	public double getA2() {
		return a2;
	}

	public double getB2() {
		return b2;
	}

}
