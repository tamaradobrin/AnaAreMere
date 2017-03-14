package com.hillclimbing;

import java.util.Arrays;
import java.util.List;

public class HillClimbing {

	static int iteration;
	static int changeIteration;

	static int[] computeFirstImprovement(double a, double b, int m, int numberOfBits, int function, int[] sol) {
		int n = m * numberOfBits;
		double[] nextSolD = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		double bestVal = Functions.computeFunction(nextSolD, m, function);
		int[] neighbour = Arrays.copyOf(sol, n);
		double[] neighbourD = new double[m];
		for (int i = 0; i < n + n / 2; i++) {
			iteration++;
			int pos = (int) (Math.random() * n);
			neighbour[pos] = sol[pos] == 0 ? 1 : 0;
			neighbourD = BinaryUtils.getSolution(a, b, m, neighbour, numberOfBits);
			double newVal = Functions.computeFunction(neighbourD, m, function);
			if (newVal < bestVal) {
				changeIteration = iteration;
				bestVal = newVal;
				return neighbour;
			}
		}
		return neighbour;

	}

	static int[] computeBestImprovement(double a, double b, int m, int numberOfBits, int function, int[] sol) {
		int n = m * numberOfBits;
		int[] nextSol = Arrays.copyOf(sol, n);
		double[] nextSolD = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		double bestVal = Functions.computeFunction(nextSolD, m, function);
		List<int[]> neighboursI = BinaryUtils.getAllNeighbours(sol, n);
		double[] neighbourD = new double[m];
		for (int[] neighbour : neighboursI) {
			iteration++;
			neighbourD = BinaryUtils.getSolution(a, b, m, neighbour, numberOfBits);
			double newVal = Functions.computeFunction(neighbourD, m, function);
			if (newVal < bestVal) {
				changeIteration = iteration;
				bestVal = newVal;
				nextSol = Arrays.copyOf(neighbour, n);
			}
		}
		return nextSol;
	}

	static void compute(int m, double a, double b, int function, boolean firstImprovement) {
		int numberOfBits = BinaryUtils.getNumberOfBits(a, b);
		int n = m * numberOfBits;
		int[] oldSol = BinaryUtils.generateRandomSolution(m, numberOfBits);
		int[] nextSol = Arrays.copyOf(oldSol, n);
		do {
			
			double[] solD = BinaryUtils.getSolution(a, b, m, nextSol, numberOfBits);
			double val = Functions.computeFunction(solD, m, function);
			printSolution(m, numberOfBits, nextSol, solD, val);
			oldSol = Arrays.copyOf(nextSol, n);
			if (firstImprovement)
				nextSol = computeFirstImprovement(a, b, m, numberOfBits, function, oldSol);
			else
				nextSol = computeBestImprovement(a, b, m, numberOfBits, function, oldSol);
		} while (checkCandidate(oldSol, nextSol, a, b, m, numberOfBits, function));
	}

	static boolean checkCandidate(int[] sol, int[] candidate, double a, double b, int m, int numberOfBits,
			int function) {
		double[] solD = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		double[] candidateD = BinaryUtils.getSolution(a, b, m, candidate, numberOfBits);
		return Functions.computeFunction(solD, m, function) > Functions.computeFunction(candidateD, m, function);
	}
	
	static void printSolution(int m, int numberOfBits, int[] sol, double[] solD, double val){
		App.printBinarySolution(m, sol, numberOfBits);
		App.printFPSolution(m, solD);
		System.out.println("\nIteration: " + changeIteration + ", value: "
				+ val
				+ "\n");
	}
}
