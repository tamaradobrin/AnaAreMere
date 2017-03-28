package com.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.utils.BinaryUtils;
import com.utils.FileUtils;
import com.utils.Function;

public class Genetic {
	private static final int NUMBER_OF_GENERATIONS = 100;
	private Function function;
	private int populationSize;
	private int length;
	private int[][] population;
	private double[] fitnessValues;
	private double mutationRate;
	private double crossoverRate;
	private int[] bestIndividual;
	private double bestFitness;
	
	public Genetic(){
		super();
	}

	public Genetic(int populationSize, double mutationRate, double crossoverRate, Function function) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.function = function;
		if (function.m == 2) {
			this.length = function.numberOfBits;
		} else {
			this.length = function.m * function.numberOfBits;
		}
	}

	public int[][] generateInitialPopulation() {
		population = BinaryUtils.generatePopulation(populationSize, function.numberOfBits, function.m);
		return population;
	}

	public void executeAlgorithm() {
		System.out.println("Started execution");
		generateInitialPopulation();
		int count = 0;
		FileUtils fileUtils = new FileUtils();
		String fileName = fileUtils.createFile(function);
		computeAndPrintBest(population, fileName, count);
		do {
			population = rouletteWheel(population);
			population = mutation(population, mutationRate);
			population = crossover(population, crossoverRate);
			count++;
			computeAndPrintBest(population, fileName, count);
		} while (count < NUMBER_OF_GENERATIONS);
		System.out.println("End of execution");
	}

	public double[] computeFitnessValues(int[][] population) {
		double[] res = new double[populationSize];
		for (int i = 0; i < populationSize; i++) {
			res[i] = function.computeFitness(population[i]);
		}
		return res;
	}

	public int[][] rouletteWheel(int[][] population) {
		int[][] res = new int[populationSize][length];
		fitnessValues = computeFitnessValues(population);
		double[] prob = new double[populationSize];
		double[] cummulateProb = new double[populationSize + 1];
		double totalFitness = 0;
		for (int i = 0; i < populationSize; i++) {
			totalFitness += fitnessValues[i];
		}
		for (int i = 0; i < populationSize; i++) {
			prob[i] = fitnessValues[i] / totalFitness;
		}
		cummulateProb[0] = 0;
		for (int i = 1; i < populationSize; i++) {
			cummulateProb[i] = cummulateProb[i - 1] + prob[i - 1];
		}
		cummulateProb[populationSize] = 1;
		double r;
		Random random = new Random();
		for (int i = 0; i < populationSize; i++) {
			r = random.nextDouble();
			for (int j = 0; j < populationSize - 1; j++) {
				if (cummulateProb[j] < r && r < cummulateProb[j + 1]) {
					res[i] = Arrays.copyOf(population[j], population[j].length);
				}
			}
			if (r > cummulateProb[populationSize - 1]) {
				res[i] = Arrays.copyOf(population[populationSize - 1], population[populationSize - 1].length);
			}
		}
		return res;
	}

	public int[][] mutation(int[][] population, double mutationRate) {
		int[][] res = new int[populationSize][length];
		Random random = new Random();
		for (int i = 0; i < populationSize; i++) {
			int[] child = Arrays.copyOf(population[i], length);
			double r = random.nextDouble();
			if (r < mutationRate) {
				int pos = random.nextInt(length);
				child[pos] = child[pos] == 0 ? 1 : 0;
			}
			res[i] = Arrays.copyOf(child, length);
		}
		return res;
	}

	public int[][] crossover(int[][] population, double crossoverRate) {
		int[][] res = new int[populationSize][length];
		Random random = new Random();
		List<Integer> selectedPositions = new ArrayList<Integer>();
		for (int i = 0; i < populationSize; i++) {
			res[i] = Arrays.copyOf(population[i], length);
			double r = random.nextDouble();
			if (r < crossoverRate) {
				selectedPositions.add(i);
			}
		}
		while (selectedPositions.size() > 1) {
			int r = random.nextInt(selectedPositions.size());
			int pos1 = selectedPositions.get(r);
			selectedPositions.remove(Integer.valueOf(pos1));
			r = random.nextInt(selectedPositions.size());
			int pos2 = selectedPositions.get(r);
			selectedPositions.remove(Integer.valueOf(pos2));
			int[] child = crossover(population[pos1], population[pos2]);
			res[pos1] = Arrays.copyOf(child, length);
			res[pos2] = Arrays.copyOf(child, length);
		}
		return res;
	}

	public int[] crossover(int[] parent1, int[] parent2) {
		int[] child = new int[length];
		Random random = new Random();
		int pos = random.nextInt(length);
		for (int i = 0; i < length; i++) {
			if (i < pos) {
				child[i] = parent1[i];
			} else {
				child[i] = parent2[i];
			}
		}
		return child;
	}

	public int[] getBestIndividual(int[][] population, double[] fitnessValues) {
		int[] res = new int[length];
		double max = getBestFitness(fitnessValues);
		for (int i = 0; i < populationSize; i++) {
			if (fitnessValues[i] == max) {
				res = population[i];
				break;
			}
		}
		return res;
	}

	public double getBestFitness(double[] fitnessValues) {
		double max = fitnessValues[0];
		for (int i = 1; i < fitnessValues.length; i++) {
			if (fitnessValues[i] > max) {
				max = fitnessValues[i];
			}
		}
		return max;
	}

	public void computeAndPrintBest(int[][] population, String fileName, int count) {
		FileUtils fileUtils = new FileUtils();
		fitnessValues = computeFitnessValues(population);
		bestFitness = getBestFitness(fitnessValues);
		bestIndividual = getBestIndividual(population, fitnessValues);
		double bestFunctionValue = function.compute(bestIndividual);
		String text = "Generation: " + count;
		text += ", Best fitness: " + bestFitness;
		text += ", Best function value: " + bestFunctionValue;
		fileUtils.updateFile(fileName, text);
	}
}
