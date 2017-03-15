package com.hillclimbing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

	static int[] computeFirstImprovementCamel(double a1, double b1, double a2, double b2, int m, int numberOfBits1,
			int numberOfBits2, int function, int[] sol) {
		int n = numberOfBits1 + numberOfBits2;
		double[] nextSolD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, sol, numberOfBits1, numberOfBits2);
		double bestVal = Functions.computeFunction(nextSolD, m, function);
		int[] neighbour = Arrays.copyOf(sol, n);
		double[] neighbourD = new double[m];
		for (int i = 0; i < n + n / 2; i++) {
			iteration++;
			int pos = (int) (Math.random() * n);
			neighbour[pos] = sol[pos] == 0 ? 1 : 0;
			neighbourD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, neighbour, numberOfBits1, numberOfBits2);
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

	static int[] computeBestImprovementCamel(double a1, double b1, double a2, double b2, int m, int numberOfBits1,
			int numberOfBits2, int function, int[] sol) {
		int n = numberOfBits1 + numberOfBits2;
		int[] nextSol = Arrays.copyOf(sol, n);
		double[] nextSolD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, sol, numberOfBits1, numberOfBits2);
		double bestVal = Functions.computeFunction(nextSolD, m, function);
		List<int[]> neighboursI = BinaryUtils.getAllNeighbours(sol, n);
		double[] neighbourD = new double[m];
		for (int[] neighbour : neighboursI) {
			iteration++;
			neighbourD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, neighbour, numberOfBits1, numberOfBits2);
			double newVal = Functions.computeFunction(neighbourD, m, function);
			if (newVal < bestVal) {
				changeIteration = iteration;
				bestVal = newVal;
				nextSol = Arrays.copyOf(neighbour, n);
			}
		}
		return nextSol;
	}

	void compute(int m, double a, double b, int function, boolean firstImprovement) {
		iteration = 0;changeIteration=0;
		String fileName = createFile(function);
		int numberOfBits = BinaryUtils.getNumberOfBits(a, b);
		int n = m * numberOfBits;
		int[] oldSol = BinaryUtils.generateRandomSolution(n);
		int[] nextSol = Arrays.copyOf(oldSol, n);
		do {
			double[] solD = BinaryUtils.getSolution(a, b, m, nextSol, numberOfBits);
			double val = Functions.computeFunction(solD, m, function);
			printSolution(m, numberOfBits, nextSol, solD, val);
			updateFile(fileName, changeIteration + " " + val);
			oldSol = Arrays.copyOf(nextSol, n);
			if (firstImprovement)
				nextSol = computeFirstImprovement(a, b, m, numberOfBits, function, oldSol);
			else
				nextSol = computeBestImprovement(a, b, m, numberOfBits, function, oldSol);
		} while (checkCandidate(oldSol, nextSol, a, b, m, numberOfBits, function));
	}

	void computeCamel(int m, double a1, double b1, double a2, double b2, int function, boolean firstImprovement) {
		iteration = 0;changeIteration=0;
		String fileName = createFile(function);
		int numberOfBits1 = BinaryUtils.getNumberOfBits(a1, b1);
		int numberOfBits2 = BinaryUtils.getNumberOfBits(a2, b2);
		int n = numberOfBits1 + numberOfBits2;
		int[] oldSol = BinaryUtils.generateRandomSolution(n);
		int[] nextSol = Arrays.copyOf(oldSol, n);
		do {
			double[] solD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, nextSol, numberOfBits1, numberOfBits2);
			double val = Functions.computeFunction(solD, m, function);
			// printSolution(m, numberOfBits, nextSol, solD, val);
			updateFile(fileName, changeIteration + " " + val);
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
		return Functions.computeFunction(solD, m, function) > Functions.computeFunction(candidateD, m, function);
	}

	static boolean checkCandidateCamel(int[] sol, int[] candidate, double a1, double b1, double a2, double b2, int m,
			int numberOfBits1, int numberOfBits2, int function) {
		double[] solD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, sol, numberOfBits1, numberOfBits2);
		double[] candidateD = BinaryUtils.getSolutionCamel(a1, b1, a2, b2, m, candidate, numberOfBits1, numberOfBits2);
		return Functions.computeFunction(solD, m, function) > Functions.computeFunction(candidateD, m, function);
	}

	static void printSolution(int m, int numberOfBits, int[] sol, double[] solD, double val) {
		App.printBinarySolution(m, sol, numberOfBits);
		App.printFPSolution(m, solD);
		System.out.println("\nIteration: " + changeIteration + " value: " + val + "\n");
	}

	String createFile(int function) {
		String fileName = "";
		switch (function) {
		case 1:
			fileName = "Rastrigin";
			break;
		case 2:
			fileName = "Griewangk";
			break;
		case 3:
			fileName = "Rosenbrock";
			break;
		case 4:
			fileName = "Camel";
			break;
		}
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
		fileName = fileName + timestamp + ".txt";
		try {
			URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
			File file = new File(url.getPath() + fileName);
			file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	void updateFile(String fileName, String text) {
		try {
			URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
			File file = new File(url.getPath() + fileName);
			FileWriter writer = new FileWriter(file.getPath(), true);
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(text);
			bw.newLine();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
