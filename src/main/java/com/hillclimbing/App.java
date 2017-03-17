package com.hillclimbing;

import java.util.concurrent.TimeUnit;

public class App {

	public static void main(String[] args) {
		int m = 5, numberOfIterations = 2;
		HillClimbing hillClimbing = new HillClimbing();
		try {
			for (int i = 0; i < numberOfIterations; i++) {
				hillClimbing.computeCamel(2, -3, 3, -2, 2, 4, false);
				TimeUnit.SECONDS.sleep(1);
			}
			for (int i = 0; i < numberOfIterations; i++) {
				hillClimbing.compute(m, -5.12, 5.12, 1, false);
				TimeUnit.SECONDS.sleep(1);
			}
			for (int i = 0; i < numberOfIterations; i++) {
				hillClimbing.compute(m, -600, 600, 2, false);
				TimeUnit.SECONDS.sleep(1);
			}
			for (int i = 0; i < numberOfIterations; i++) {
				hillClimbing.compute(m, -2.048, 2.048, 3, false);
				TimeUnit.SECONDS.sleep(1);
			}
			for (int i = 0; i < numberOfIterations; i++) {
				hillClimbing.computeCamel(2, -3, 3, -2, 2, 4, false);
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
