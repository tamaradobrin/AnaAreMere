package com.mtsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Population {
  public static final double EPSILON = 0.0001;

  private List<Solution> population;
  private int populationSize;
  private MTSPInstance instance;
  private double bestTour;
  private Solution bestSolution;

  public double getBestTour() {
    return bestTour;
  }

  public void setBestTour(double bestTour) {
    this.bestTour = bestTour;
  }

  public Solution getBestSolution() {
    return bestSolution;
  }

  public void setBestSolution(Solution bestSolution) {
    this.bestSolution = bestSolution;
  }

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
    List<Solution> newPop = new ArrayList<Solution>();
    for (Solution s : population) {
      Solution newS = new Solution();
      newS.setInstance(s.getInstance());
      int[] chromosome = Arrays.copyOf(s.getChromosome(), s.getChromosome().length);
      newS.setChromosome(chromosome);
    }
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

  public void operate(double m1Rate, double m2Rate, double cRate) {
    List<Solution> p = new ArrayList<Solution>();
    Random random = new Random();;
    for (int i = 0; i < populationSize; i++) {
      Solution s = population.get(i);
      double r = random.nextDouble();
      if (r < m1Rate) {
        s = mutation1(s);
      }
      r = random.nextDouble();
      if (r < m2Rate) {
        s = mutation2(s);
      }
      r = random.nextDouble();
      if (r < cRate) {
        s = crossover(s);
      }
      p.add(s);
    }
    setPopulation(p);
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
    int[] tourStarts = parent.getTourStartPositions();
    Random r = new Random();
    int t1 = r.nextInt(instance.getM());
    int t2 = r.nextInt(instance.getM());
    while (t1 == t2) {
      t2 = r.nextInt(instance.getM());
    }
    int aux;
    if (t2 < t1) {
      aux = t1;
      t1 = t2;
      t2 = aux;
    }
    int p1 = r.nextInt(parentChromosome[instance.getN() - 1 + t1]);
    int q1 = r.nextInt(parentChromosome[instance.getN() - 1 + t1]);
    int p2 = r.nextInt(parentChromosome[instance.getN() - 1 + t2]);
    int q2 = r.nextInt(parentChromosome[instance.getN() - 1 + t2]);
    if (q1 < p1) {
      aux = q1;
      q1 = p1;
      p1 = aux;
    }
    if (q2 < p2) {
      aux = q2;
      q2 = p2;
      p2 = aux;
    }
    int[] childChromosome = Arrays.copyOf(parentChromosome, parentChromosome.length);
    if ((q1 - p1) != (q2 - p2)) {
      childChromosome[instance.getN() - 1 + t1] =
          childChromosome[instance.getN() - 1 + t1] + (q2 - p2) - (q1 - p1);
      childChromosome[instance.getN() - 1 + t2] =
          childChromosome[instance.getN() - 1 + t2] + (q1 - p1) - (q2 - p2);
    }
    int i = 0, k = 0;
    while (i < instance.getN() - 1 && k < instance.getN() - 1) {
      if (i < tourStarts[t1] + p1) {
        i++;
        k++;
      } else if (k >= tourStarts[t1] + p1 && k <= tourStarts[t1] + q1) {
        for (int j = p2; j <= q2; j++) {
          childChromosome[i] = parentChromosome[tourStarts[t2] + j];
          i++;
        }
        k = tourStarts[t1] + q1 + 1;
      } else if (k > tourStarts[t1] + q1 && k < tourStarts[t2] + p2) {
        childChromosome[i] = parentChromosome[k];
        i++;
        k++;
      } else if (k >= tourStarts[t2] + p2 && k <= tourStarts[t2] + q2) {
        for (int j = p1; j <= q1; j++) {
          childChromosome[i] = parentChromosome[tourStarts[t1] + j];
          i++;
        }
        k = tourStarts[t2] + q2 + 1;
      } else {
        childChromosome[i] = parentChromosome[k];
        i++;
        k++;
      }
    }
    child.setChromosome(childChromosome);
    return child;
  }

  public void rouletteWheel() {
    List<Solution> p = new ArrayList<Solution>();
    double[] fitnessValues = computeFitnessValues(population);
    double[] prob = new double[populationSize];
    double[] cummulateProb = new double[populationSize + 1];
    double totalFitness = 0;
    for (int i = 0; i < populationSize; i++) {
      totalFitness += fitnessValues[i];
    }
    for (int i = 0; i < populationSize; i++) {
      prob[i] = fitnessValues[i] / totalFitness;
    }
    cummulateProb[0] = 0;
    for (int i = 1; i < populationSize; i++) {
      cummulateProb[i] = cummulateProb[i - 1] + prob[i - 1];
    }
    cummulateProb[populationSize] = 1;
    double r;
    Random random = new Random();
    for (int i = 0; i < populationSize; i++) {
      p.add(new Solution());
      r = random.nextDouble();
      for (int j = 0; j < populationSize - 1; j++) {
        if (cummulateProb[j] < r && r < cummulateProb[j + 1]) {
          p.set(i, population.get(j));
        }
      }
      if (r > cummulateProb[populationSize - 1]) {
        p.set(i, population.get(populationSize - 1));
      }
    }
    setPopulation(p);
  }

  public double[] computeFitnessValues(List<Solution> population) {
    double[] fitnessValues = new double[populationSize];
    int i = 0;
    for (Solution s : population) {
      fitnessValues[i++] = 1 / (s.computeLongestTour() + EPSILON);
    }
    return fitnessValues;
  }
}
