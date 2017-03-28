package com.hibrid;

import java.util.Arrays;
import java.util.Random;

import com.genetic.Genetic;
import com.hillclimbing.HillClimbing;
import com.utils.Camel;
import com.utils.FileUtils;
import com.utils.Function;

public class Hibrid {
	private static final int NUMBER_OF_GENERATIONS = 200;
	private Function function;
	private int populationSize;
	private int length;
	private int[][] population;
	private double[] fitnessValues;
	private double mutationRate;
	private double crossoverRate;
	private double hillclimbingRate;
	private int[] bestIndividual;
	private double bestFitness;

	public Hibrid(int populationSize, double mutationRate, double crossoverRate, double hillclimbingRate,
			Function function) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.hillclimbingRate = hillclimbingRate;
		this.function = function;
		if (function.m == 2) {
			this.length = function.numberOfBits;
		} else {
			this.length = function.m * function.numberOfBits;
		}
	}

	public void executeAlgorithm() {
		Genetic genetic = new Genetic(populationSize, mutationRate, crossoverRate, function);
		population = genetic.generateInitialPopulation();
		int count = 0;
		FileUtils fileUtils = new FileUtils();
		String fileName = fileUtils.createFile(function);
		genetic.computeAndPrintBest(population, fileName, count);
		do {
			population = genetic.rouletteWheel(population);
			population = genetic.mutation(population, mutationRate);
			population = genetic.crossover(population, crossoverRate);
			population = hillClimbing(population, hillclimbingRate);
			count++;
			genetic.computeAndPrintBest(population, fileName, count);
		} while (count < NUMBER_OF_GENERATIONS);
		bestIndividual = genetic.getBestIndividual(population, genetic.computeFitnessValues(population));
		double bestFunctionValue = function.compute(bestIndividual);
		System.out.println(bestFunctionValue);
	}

	public int[][] hillClimbing(int[][] population, double hillclimbingRate) {
		int[][] res = new int[populationSize][length];
		double r;
		Random random = new Random();
		HillClimbing hillclimbing = new HillClimbing();
		for (int i = 0; i < populationSize; i++) {
			r = random.nextDouble();
			if (r < hillclimbingRate) {
				int[] bestNeighbour = hillclimbing.compute(function.m, function, true, population[i]);
				res[i] = Arrays.copyOf(bestNeighbour, bestNeighbour.length);
			} else {
				res[i] = Arrays.copyOf(population[i], population[i].length);
			}
		}
		return res;
	}
}
