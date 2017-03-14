package com.hillclimbing;

public class App {

	public static double a, b;

	public static void main(String[] args) {
		a = -2.048;
		b = 2.048;
		int m = 5;
		int function = 3;
		HillClimbing hillClimbing = new HillClimbing();
		hillClimbing.compute(m, a, b, function, false);
	}

	static void printBinarySolution(int m, int[] sol, int numberOfBits) {
		for (int i = 0; i < m; i++) {
			String line = "";
			for (int j = 0; j < numberOfBits; j++)
				line += sol[numberOfBits * i + j];
			System.out.println(line);
		}
	}

	static void printFPSolution(int m, double[] sol) {
		for (int i = 0; i < m; i++)
			System.out.println(sol[i]);
	}
}
