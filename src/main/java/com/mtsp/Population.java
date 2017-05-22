package com.mtsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    this.population = new ArrayList<Solution>();
    for (int i = 0; i < populationSize; i++) {
      Solution s = new Solution();
      s.setInstance(instance);
      s.generateSolution();
      population.add(s);
    }
  }

  public Solution mutation1(Solution parent) {
    Solution child = new Solution();
    child.setInstance(instance);
    int[] childChromosome = Arrays.copyOf(parent.getChromosome(), parent.getChromosome().length);
    Random r = new Random();
    int pos1 = r.nextInt(instance.getN() - 1);
    int pos2 = r.nextInt(instance.getN() - 1);
    while (pos1 == pos2) {
      pos2 = r.nextInt(instance.getN() - 1);
    }
    int aux = childChromosome[pos1];
    childChromosome[pos1] = childChromosome[pos2];
    childChromosome[pos2] = aux;
    child.setChromosome(childChromosome);
    return child;
  }

  public Solution mutation2(Solution parent) {
    Solution child = new Solution();
    child.setInstance(instance);
    int[] parentChromosome = parent.getChromosome();
    int[] childChromosome = Arrays.copyOf(parentChromosome, parentChromosome.length);
    Random r = new Random();
    int pos1 = r.nextInt(instance.getN() - 1);
    int pos2 = r.nextInt(instance.getN() - 1);
    while (pos1 == pos2) {
      pos2 = r.nextInt(instance.getN() - 1);
    }
    int aux;
    if (pos1 > pos2) {
      aux = pos1;
      pos1 = pos2;
      pos2 = aux;
    }
    for (int i = 0; i <= pos2 - pos1; i++) {
      childChromosome[pos1 + i] = parentChromosome[pos2 - i];
    }
    child.setChromosome(childChromosome);
    return child;
  }

  public Solution crossover(Solution parent) {
    Solution child = new Solution();
    child.setInstance(instance);
    int[] parentChromosome = parent.getChromosome();
    Random r = new Random();
    int t1 = r.nextInt(instance.getM());
    int t2 = r.nextInt(instance.getM());
    while (t1 == t2) {
      t2 = r.nextInt(instance.getM());
    }
    int p1 = r.nextInt(parentChromosome[instance.getN() + instance.getM() - 1 + t1]);
    int q1 = r.nextInt(parentChromosome[instance.getN() + instance.getM() - 1 + t1]);
    int p2 = r.nextInt(parentChromosome[instance.getN() + instance.getM() - 1 + t2]);
    int q2 = r.nextInt(parentChromosome[instance.getN() + instance.getM() - 1 + t2]);
    int aux;
    if (q1 > p1) {
      aux = q1;
      q1 = p1;
      p1 = aux;
    }
    if (q2 > p2) {
      aux = q2;
      q2 = p2;
      p2 = aux;
    }
    return child;
  }

}
