package com.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryUtils {
	public static final int q = 2;
	public static int numberOfBits;

	public static int log(double x, int base) {
		return (int) (Math.log(x) / Math.log(base) + 1e-10);
	}

	public static int getNumberOfBits(double a, double b) {
		double x = Math.pow(10, q) * (b - a);
		return log(x, 2);
	}

	public static int[] generateRandomSolution(int length) {
		int[] solution = new int[length];
		for (int i = 0; i < length; i++) {
			solution[i] = Math.random() > 0.5 ? 0 : 1;
		}
		return solution;
	}

	public static double[] getSolution(double a, double b, int m, int[] binary, int numberOfBits) {
		double[] sol = new double[m];
		for (int i = 0; i < m; i++) {
			int[] v = Arrays.copyOfRange(binary, i * numberOfBits, (i + 1) * numberOfBits);
			int valInt = getDouble(v);
			sol[i] = ((double) valInt / (Math.pow(2, numberOfBits) - 1)) * (b - a) + a;
		}
		return sol;
	}

	public static double[] getSolutionCamel(double a1, double b1, double a2, double b2, int m, int[] binary, int n1,
			int n2) {
		double[] sol = new double[m];
		int[] v1 = Arrays.copyOfRange(binary, 0, n1);
		int valInt1 = getDouble(v1);
		sol[0] = ((double) valInt1 / (Math.pow(2, n1) - 1)) * (b1 - a1) + a1;
		int[] v2 = Arrays.copyOfRange(binary, n1, n1 + n2);
		int valInt2 = getDouble(v2);
		sol[1] = ((double) valInt2 / (Math.pow(2, n2) - 1)) * (b2 - a2) + a2;
		return sol;
	}

	public static int getDouble(int[] binary) {
		int result = 0;
		int pow = 1;
		for (int i = 0; i < binary.length; i++) {
			result += pow * binary[i];
			pow *= 2;
		}
		return result;
	}

	public static List<int[]> getAllNeighbours(int[] solution, int n) {
		List<int[]> neighbours = new ArrayList<int[]>();
		for (int i = 0; i < n; i++) {
			int[] neighbour = Arrays.copyOf(solution, solution.length);
			neighbour[i] = solution[i] == 0 ? 1 : 0;
			neighbours.add(neighbour);
		}
		return neighbours;
	}

	public static int[][] generatePopulation(int populationSize, int length, int m) {
		int[][] population = new int[populationSize][m * length];
		for (int i = 0; i < populationSize; i++) {
			population[i] = generateRandomSolution(m * length);
		}
		return population;
	}
}
