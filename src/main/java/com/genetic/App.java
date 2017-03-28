package com.genetic;

import java.util.concurrent.TimeUnit;

import com.utils.Camel;
import com.utils.Griewangk;
import com.utils.Rastrigin;
import com.utils.Rosenbrock;

public class App {
	public static void main(String[] args) {
		int m = 30, numberOfIterations = 30;
		double mutationRate = 0.1;
		double crossoverRate = 0.3;
		int populationSize = 50;
		Genetic alg;
		try {
			System.out.println("Rastrigin");
			for (int i = 0; i < numberOfIterations; i++) {
				alg = new Genetic(populationSize, mutationRate, crossoverRate, new Rastrigin(m));
				alg.executeAlgorithm();
				TimeUnit.SECONDS.sleep(1);

			}
			System.out.println("Griewangk");
			for (int i = 0; i < numberOfIterations; i++) {
				alg = new Genetic(populationSize, mutationRate, crossoverRate, new Griewangk(m));
				alg.executeAlgorithm();
				TimeUnit.SECONDS.sleep(1);
			}
			System.out.println("Rosenbrock");
			for (int i = 0; i < numberOfIterations; i++) {
				alg = new Genetic(populationSize, mutationRate, crossoverRate, new Rosenbrock(m));
				alg.executeAlgorithm();
				TimeUnit.SECONDS.sleep(1);
			}
			System.out.println("Camel");
			for (int i = 0; i < numberOfIterations; i++) {
				alg = new Genetic(populationSize, mutationRate, crossoverRate, new Camel(2));
				alg.executeAlgorithm();
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
