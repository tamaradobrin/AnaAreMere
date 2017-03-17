package com.genetic;

import com.utils.Rastrigin;

public class App {
	public static void main(String[] args) {
		int m = 5;
		double mutationRate = 0.5;
		double crossoverRate = 0.5;
		int populationSize = 50;
		Genetic alg = new Genetic(populationSize, mutationRate, crossoverRate, new Rastrigin(m));
		alg.executeAlgorithm();
	}
}
