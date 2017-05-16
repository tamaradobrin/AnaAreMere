package com.pso;

import com.utils.Function;
import com.utils.Griewangk;
import com.utils.Rastrigin;
import com.utils.Rosenbrock;

public class PSO {

	public static final int POPULATION_SIZE = 200;
	public static final int ITERATIONS = 1000;

	public static void main(String[] args) {
		int m = 30;
		System.out.println("Rastrigin");
		Function f = new Rastrigin(m);
		Population population = new Population(POPULATION_SIZE, f);
		population.execute(ITERATIONS);
		f = new Griewangk(m);
		population = new Population(POPULATION_SIZE, f);
		population.execute(ITERATIONS);
		f = new Rosenbrock(m);
		population = new Population(POPULATION_SIZE, f);
		population.execute(ITERATIONS);
	}
}
