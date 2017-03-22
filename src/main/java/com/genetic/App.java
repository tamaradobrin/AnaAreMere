package com.genetic;

import com.utils.Camel;
import com.utils.Griewangk;
import com.utils.Rastrigin;
import com.utils.Rosenbrock;

public class App {
	public static void main(String[] args) {
		int m = 5;
		double mutationRate = 0.1;
		double crossoverRate = 0.3;
		int populationSize = 50;
		Genetic alg = new Genetic(populationSize, mutationRate, crossoverRate, new Rastrigin(m));
		alg.executeAlgorithm();
		alg = new Genetic(populationSize, mutationRate, crossoverRate, new Griewangk(m));
		alg.executeAlgorithm();
		alg = new Genetic(populationSize, mutationRate, crossoverRate, new Rosenbrock(m));
		alg.executeAlgorithm();
		alg = new Genetic(populationSize, mutationRate, crossoverRate, new Camel(m));
		alg.executeAlgorithm();
	}
}
