package com.hillclimbing;

import java.util.Arrays;
import java.util.List;

import com.utils.BinaryUtils;
import com.utils.Camel;
import com.utils.FileUtils;
import com.utils.Function;
import com.utils.Griewangk;
import com.utils.Rastrigin;
import com.utils.Rosenbrock;

public class HillClimbing {

	static int iteration;
	static int changeIteration;

	static int[] computeFirstImprovement(double a, double b, int m, int numberOfBits, int function, int[] sol) {
		int n = m * numberOfBits;
		double[] nextSolD = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		double bestVal = Function.computeFunction(nextSolD, m, function);
		int[] neighbour = Arrays.copyOf(sol, n);
		double[] neighbourD = new double[m];
		for (int i = 0; i < n + n / 2; i++) {
			iteration++;
			int pos = (int) (Math.random() * n);
			neighbour[pos] = sol[pos] == 0 ? 1 : 0;
			neighbourD = BinaryUtils.getSolution(a, b, m, neighbour, numberOfBits);
			double newVal = Function.computeFunction(neighbourD, m, function);
			if (newVal < bestVal) {
				changeIteration = iteration;
				bestVal = newVal;
				return neighbour;
			}
		}
		return neighbour;

	}

	static int[] computeFirstImprovementCamel(double a1, double b1, double a2, double b2, int m, int numberOfBits1,
			int numberOfBits2, int function, int[] sol) {
		int n = numberOfBits1 + numberOfBits2;
		double[] nextSolD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, sol, numberOfBits1, numberOfBits2);
		double bestVal = Function.computeFunction(nextSolD, m, function);
		int[] neighbour = Arrays.copyOf(sol, n);
		double[] neighbourD = new double[m];
		for (int i = 0; i < n + n / 2; i++) {
			iteration++;
			int pos = (int) (Math.random() * n);
			neighbour[pos] = sol[pos] == 0 ? 1 : 0;
			neighbourD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, neighbour, numberOfBits1, numberOfBits2);
			double newVal = Function.computeFunction(neighbourD, m, function);
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
		double bestVal = Function.computeFunction(nextSolD, m, function);
		List<int[]> neighboursI = BinaryUtils.getAllNeighbours(sol, n);
		double[] neighbourD = new double[m];
		for (int[] neighbour : neighboursI) {
			iteration++;
			neighbourD = BinaryUtils.getSolution(a, b, m, neighbour, numberOfBits);
			double newVal = Function.computeFunction(neighbourD, m, function);
			if (newVal < bestVal) {
				changeIteration = iteration;
				bestVal = newVal;
				nextSol = Arrays.copyOf(neighbour, n);
			}
		}
		return nextSol;
	}

	static int[] computeBestImprovementCamel(double a1, double b1, double a2, double b2, int m, int numberOfBits1,
			int numberOfBits2, int function, int[] sol) {
		int n = numberOfBits1 + numberOfBits2;
		int[] nextSol = Arrays.copyOf(sol, n);
		double[] nextSolD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, sol, numberOfBits1, numberOfBits2);
		double bestVal = Function.computeFunction(nextSolD, m, function);
		List<int[]> neighboursI = BinaryUtils.getAllNeighbours(sol, n);
		double[] neighbourD = new double[m];
		for (int[] neighbour : neighboursI) {
			iteration++;
			neighbourD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, neighbour, numberOfBits1, numberOfBits2);
			double newVal = Function.computeFunction(neighbourD, m, function);
			if (newVal < bestVal) {
				changeIteration = iteration;
				bestVal = newVal;
				nextSol = Arrays.copyOf(neighbour, n);
			}
		}
		return nextSol;
	}

	public int[] compute(int m, double a, double b, int function, boolean firstImprovement) {
		iteration = 0;
		changeIteration = 0;
		FileUtils fileUtils = new FileUtils();
		String fileName = fileUtils.createFile(function);
		int numberOfBits = BinaryUtils.getNumberOfBits(a, b);
		int n = m * numberOfBits;
		int[] oldSol = BinaryUtils.generateRandomSolution(n);
		int[] nextSol = Arrays.copyOf(oldSol, n);
		do {
			double[] solD = BinaryUtils.getSolution(a, b, m, nextSol, numberOfBits);
			double val = Function.computeFunction(solD, m, function);
			printSolution(m, numberOfBits, nextSol, solD, val);
			fileUtils.updateFile(fileName, changeIteration + " " + val);
			oldSol = Arrays.copyOf(nextSol, n);
			if (firstImprovement)
				nextSol = computeFirstImprovement(a, b, m, numberOfBits, function, oldSol);
			else
				nextSol = computeBestImprovement(a, b, m, numberOfBits, function, oldSol);
		} while (checkCandidate(oldSol, nextSol, a, b, m, numberOfBits, function));
		return nextSol;
	}

	public int[] computeExp(int m, double a, double b, int function, boolean firstImprovement, int[] sol) {
		iteration = 0;
		changeIteration = 0;
		int numberOfBits = BinaryUtils.getNumberOfBits(a, b);
		int n = m * numberOfBits;
		int[] oldSol = Arrays.copyOf(sol, n);
		int[] nextSol = Arrays.copyOf(sol, n);
		do {
			double[] solD = BinaryUtils.getSolution(a, b, m, nextSol, numberOfBits);
			oldSol = Arrays.copyOf(nextSol, n);
			if (firstImprovement)
				nextSol = computeFirstImprovement(a, b, m, numberOfBits, function, oldSol);
			else
				nextSol = computeBestImprovement(a, b, m, numberOfBits, function, oldSol);
		} while (checkCandidate(oldSol, nextSol, a, b, m, numberOfBits, function));
		return nextSol;
	}
	
	public int[] computeExpCamel(int m, double a1, double b1, double a2, double b2, int function, boolean firstImprovement, int[] sol) {
		iteration = 0;
		changeIteration = 0;
		int numberOfBits1 = BinaryUtils.getNumberOfBits(a1, b1);
		int numberOfBits2 = BinaryUtils.getNumberOfBits(a2, b2);
		int n = numberOfBits1 + numberOfBits2;
		int[] oldSol = Arrays.copyOf(sol, n);
		int[] nextSol = Arrays.copyOf(sol, n);
		do {
			double[] solD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, nextSol, numberOfBits1, numberOfBits2);
			oldSol = Arrays.copyOf(nextSol, n);
			if (firstImprovement)
				nextSol = computeFirstImprovementCamel(a1, b1, a2, b2, m, numberOfBits1, numberOfBits2, function,
						oldSol);
			else
				nextSol = computeBestImprovementCamel(a1, b1, a2, b2, m, numberOfBits1, numberOfBits2, function,
						oldSol);
		} while (checkCandidateCamel(oldSol, nextSol, a1, b1, a2, b2, m, numberOfBits1, numberOfBits2, function));
		return nextSol;
	}

	public int[] compute(int m, Function function, boolean firstImprovement, int[] sol) {
		if (function instanceof Rastrigin) {
			Rastrigin f = (Rastrigin) function;
			return computeExp(m, f.getA(), f.getB(), 1, firstImprovement, sol);
		} else if (function instanceof Rosenbrock) {
			Rosenbrock f = (Rosenbrock) function;
			return computeExp(m, f.getA(), f.getB(), 3, firstImprovement, sol);
		} else if (function instanceof Griewangk) {
			Griewangk f = (Griewangk) function;
			return computeExp(m, f.getA(), f.getB(), 2, firstImprovement, sol);
		} else if(function instanceof Camel){
			Camel f = (Camel) function;
			return computeExpCamel(m, f.getA1(), f.getB1(), f.getA2(), f.getB2(), 4, firstImprovement, sol);
		}
		return null;

	}

	void computeCamel(int m, double a1, double b1, double a2, double b2, int function, boolean firstImprovement) {
		iteration = 0;
		changeIteration = 0;
		FileUtils fileUtils = new FileUtils();
		String fileName = fileUtils.createFile(function);
		int numberOfBits1 = BinaryUtils.getNumberOfBits(a1, b1);
		int numberOfBits2 = BinaryUtils.getNumberOfBits(a2, b2);
		int n = numberOfBits1 + numberOfBits2;
		int[] oldSol = BinaryUtils.generateRandomSolution(n);
		int[] nextSol = Arrays.copyOf(oldSol, n);
		do {
			double[] solD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, nextSol, numberOfBits1, numberOfBits2);
			double val = Function.computeFunction(solD, m, function);
			// printSolution(m, numberOfBits, nextSol, solD, val);
			fileUtils.updateFile(fileName, changeIteration + " " + val);
			oldSol = Arrays.copyOf(nextSol, n);
			if (firstImprovement)
				nextSol = computeFirstImprovementCamel(a1, b1, a2, b2, m, numberOfBits1, numberOfBits2, function,
						oldSol);
			else
				nextSol = computeBestImprovementCamel(a1, b1, a2, b2, m, numberOfBits1, numberOfBits2, function,
						oldSol);
		} while (checkCandidateCamel(oldSol, nextSol, a1, b1, a2, b2, m, numberOfBits1, numberOfBits2, function));
	}

	static boolean checkCandidate(int[] sol, int[] candidate, double a, double b, int m, int numberOfBits,
			int function) {
		double[] solD = BinaryUtils.getSolution(a, b, m, sol, numberOfBits);
		double[] candidateD = BinaryUtils.getSolution(a, b, m, candidate, numberOfBits);
		return Function.computeFunction(solD, m, function) > Function.computeFunction(candidateD, m, function);
	}

	static boolean checkCandidateCamel(int[] sol, int[] candidate, double a1, double b1, double a2, double b2, int m,
			int numberOfBits1, int numberOfBits2, int function) {
		double[] solD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, sol, numberOfBits1, numberOfBits2);
		double[] candidateD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, candidate, numberOfBits1, numberOfBits2);
		return Function.computeFunction(solD, m, function) > Function.computeFunction(candidateD, m, function);
	}

	static void printSolution(int m, int numberOfBits, int[] sol, double[] solD, double val) {
		App.printBinarySolution(m, sol, numberOfBits);
		App.printFPSolution(m, solD);
		System.out.println("\nIteration: " + changeIteration + " value: " + val + "\n");
	}
}
