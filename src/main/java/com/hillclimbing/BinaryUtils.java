package com.hillclimbing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryUtils {
	public static final int q = 2;
	public static int numberOfBits;

	static int log(double x, int base) {
		return (int) (Math.log(x) / Math.log(base) + 1e-10);
	}

	static int getNumberOfBits(double a, double b) {
		double x = Math.pow(10, q) * (b - a);
		return log(x, 2);
	}

	static int[] generateRandomSolution(int m, int numberOfBits) {
		int length = numberOfBits * m;
		int[] solution = new int[length];
		for (int i = 0; i < length; i++) {
			solution[i] = Math.random() > 0.5 ? 0 : 1;
		}
		return solution;
	}

	static double[] getSolution(double a, double b, int m, int[] binary, int numberOfBits) {
		double[] sol = new double[m];
		for (int i = 0; i < m; i++) {
			int[] v = Arrays.copyOfRange(binary, i * numberOfBits, (i + 1) * numberOfBits);
			int valInt = getDouble(v);
			sol[i] = ((double) valInt / (Math.pow(2, numberOfBits) - 1)) * (b - a) + a;
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

	static List<int[]> getAllNeighbours(int[] solution, int n) {
		List<int[]> neighbours = new ArrayList<int[]>();
		for (int i = 0; i < n; i++) {
			int[] neighbour = Arrays.copyOf(solution, solution.length);
			neighbour[i] = solution[i] == 0 ? 1 : 0;
			neighbours.add(neighbour);
		}
		return neighbours;
	}
}
