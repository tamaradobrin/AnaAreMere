package com.hibrid;

import com.utils.Rastrigin;

public class App {

	public static void main(String[] args) {
		int m = 5;
		double mutationRate = 0.1;
		double crossoverRate = 0.3;
		double hillClimbingRate = 0.4;
		int populationSize = 50;
		Hibrid alg = new Hibrid(populationSize, mutationRate, crossoverRate, hillClimbingRate, new Rastrigin(m));
		alg.executeAlgorithm();
	}
}
