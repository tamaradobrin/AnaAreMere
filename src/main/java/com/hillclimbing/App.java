package com.hillclimbing;

import java.util.Arrays;

public class App {
	public static final int q = 2;
	public static int numberOfBits;
	public static double a, b;

	static int log(double x, int base) {
		return (int) (Math.log(x) / Math.log(base) + 1e-10);
	}

	static int getNumberOfBits(double a, double b) {
		double x = Math.pow(10, q) * (b - a);
		return log(x, 2);
	}

	static int[] generateRandomSolution(double a, double b, int m) {
		int length = numberOfBits * m;
		int[] solution = new int[length];
		for (int i = 0; i < length; i++) {
			solution[i] = Math.random() > 0.5 ? 0 : 1;
		}
		return solution;
	}

	static double[] getSolution(double a, double b, int m, int[] binary) {
		double[] sol = new double[m];
		for (int i = 0; i < m; i++) {
			int[] v = Arrays.copyOfRange(binary, i * numberOfBits, (i + 1) * numberOfBits);
			int valInt = getDouble(v);
			sol[i] = ((double)valInt / (Math.pow(2, numberOfBits) - 1)) * (b - a) + a;
		}
		return sol;
	}

	static int getDouble(int[] binary) {
		int result = 0;
		int pow = 1;
		for (int i = 0; i < binary.length; i++) {
			result += pow * binary[i];
			pow *= 2;
		}
		return result;
	}

	public static void main(String[] args) {
		a = -2.048;
		b = 2.048;
		int m = 5;
		numberOfBits = getNumberOfBits(a, b);
		int[] sol = generateRandomSolution(a, b, m);
		printBinarySolution(m, sol);
		double[] solD = getSolution(a, b, m, sol);
		printFPSolution(m, solD);
	}

	static void printBinarySolution(int m, int[] sol) {
		for (int i = 0; i < m; i++) {
			String line = "";
			for (int j = 0; j < numberOfBits; j++)
				line += sol[numberOfBits * i + j];
			System.out.println(line);
		}
	}
	
	static void printFPSolution(int m, double[] sol){
		for(int i=0;i<m;i++)
			System.out.println(sol[i]);
	}
}
