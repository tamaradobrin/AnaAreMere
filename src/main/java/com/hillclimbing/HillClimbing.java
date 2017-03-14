package com.hillclimbing;

import java.util.Arrays;
import java.util.List;

public class HillClimbing {

	static int[] computeBestImprovement(double a, double b, int m, int numberOfBits, int function, int[] sol) {
		int n = m * numberOfBits;
		int[] nextSol = Arrays.copyOf(sol, n);
		double[] nextSolD = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		double bestVal = Functions.computeFunction(nextSolD, m, function);
		List<int[]> neighboursI = BinaryUtils.getAllNeighbours(sol, n);
		double[] neighbourD = new double[m];
		for (int[] neighbour : neighboursI) {
			neighbourD = BinaryUtils.getSolution(a, b, m, neighbour, numberOfBits);
			double newVal = Functions.computeFunction(neighbourD, m, function);
			if (newVal < bestVal) {
				bestVal = newVal;
				nextSol = Arrays.copyOf(neighbour, n);
			}
		}
		return nextSol;
	}

	static void computeBestImprovement(int m, double a, double b, int function) {
		int numberOfBits = BinaryUtils.getNumberOfBits(a, b);
		int n = m * numberOfBits;
		int[] oldSol = BinaryUtils.generateRandomSolution(m, numberOfBits);
		int[] nextSol = Arrays.copyOf(oldSol, n);
		do {
			App.printBinarySolution(m, nextSol, numberOfBits);
			App.printFPSolution(m, BinaryUtils.getSolution(a, b, m, nextSol, numberOfBits));
			System.out.println(Functions.computeFunction(BinaryUtils.getSolution(a, b, m, nextSol, numberOfBits), m, function));
			oldSol = Arrays.copyOf(nextSol, n);
			nextSol = computeBestImprovement(a, b, m, numberOfBits, function, oldSol);
		} while (checkCandidate(oldSol, nextSol, a, b, m, numberOfBits, function));
	}

	static boolean checkCandidate(int[] sol, int[] candidate, double a, double b, int m, int numberOfBits,
			int function) {
		double[] solD = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		double[] candidateD = BinaryUtils.getSolution(a, b, m, candidate, numberOfBits);
		return Functions.computeFunction(solD, m, function) > Functions.computeFunction(candidateD, m, function);
	}
}
