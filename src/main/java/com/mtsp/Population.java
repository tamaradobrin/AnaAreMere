package com.mtsp;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

public class Population {

    private static final int NUMBER_OF_GENERATIONS = 100;

    private List<Solution> population;
    private int populationSize;
    private MTSPInstance instance;

    public MTSPInstance getInstance() {
        return instance;
    }

    public void setInstance(MTSPInstance instance) {
        this.instance = instance;
    }

    public List<Solution> getPopulation() {
        return population;
    }

    public void setPopulation(List<Solution> population) {
        this.population = population;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void generateInitialPopulation() {
        this.population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Solution s = new Solution();
            s.setInstance(instance);
            s.generateSolution();
            population.add(s);
        }
    }

}
