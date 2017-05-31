package com.pso;

import com.utils.Function;
import com.utils.Griewangk;
import com.utils.Rastrigin;
import com.utils.Rosenbrock;

import java.util.concurrent.TimeUnit;

public class PSO {

    public static final int POPULATION_SIZE = 50;
    public static final int ITERATIONS = 200;

    public static void main(String[] args) {
        int m = 30, numberOfIterations = 30;
        Population population;
        try {
            System.out.println("Rastrigin");
            Function f = new Rastrigin(m);
            for (int i = 0; i < numberOfIterations; i++) {
                population = new Population(POPULATION_SIZE, f);
                population.execute(ITERATIONS);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Griewangk");
            f = new Griewangk(m);
            for (int i = 0; i < numberOfIterations; i++) {
                population = new Population(POPULATION_SIZE, f);
                population.execute(ITERATIONS);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Rosenbrock");
            f = new Rosenbrock(m);
            for (int i = 0; i < numberOfIterations; i++) {
                population = new Population(POPULATION_SIZE, f);
                population.execute(ITERATIONS);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
