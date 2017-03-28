package com.hibrid;

import com.utils.Camel;
import com.utils.Griewangk;
import com.utils.Rastrigin;
import com.utils.Rosenbrock;

public class App {

	public static void main(String[] args) {
		int m = 30;
		double mutationRate = 0.1;
		double crossoverRate = 0.4;
		double hillClimbingRate = 0.3;
		int populationSize = 50;
		Hibrid alg = new Hibrid(populationSize, mutationRate, crossoverRate, hillClimbingRate, new Rastrigin(m));
		alg.executeAlgorithm();
		alg = new Hibrid(populationSize, mutationRate, crossoverRate, hillClimbingRate, new Griewangk(m));
		alg.executeAlgorithm();
		alg = new Hibrid(populationSize, mutationRate, crossoverRate, hillClimbingRate, new Rosenbrock(m));
		alg.executeAlgorithm();
		alg = new Hibrid(populationSize, mutationRate, crossoverRate, hillClimbingRate, new Camel(2));
		alg.executeAlgorithm();
	}
}
