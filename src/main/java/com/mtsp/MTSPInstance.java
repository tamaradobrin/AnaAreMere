package com.mtsp;

import java.util.List;

public class MTSPInstance {

  public final static double M1_RATE = 0.2;
  public final static double M2_RATE = 0.1;
  public final static double C_RATE = 0.3;
  public final static int ITERATIONS = 1000;
  public final static int POPULATION_SIZE = 40;

  private int n;
  private int[][] coordinates;
  private int m;

  public MTSPInstance(int dimension, int[][] coordinates) {
    this.n = dimension;
    this.coordinates = coordinates;
  }

  public MTSPInstance(int instance, int m) {
    MTSPInstance mtspInstance = new FileUtils().readInstance(instance);
    this.n = mtspInstance.getN();
    this.coordinates = mtspInstance.getCoordinates();
    this.m = m;
  }

  public int getN() {
    return n;
  }

  public void setN(int n) {
    this.n = n;
  }

  public int[][] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(int[][] coordinates) {
    this.coordinates = coordinates;
  }

  public int getM() {
    return m;
  }

  public void setM(int m) {
    this.m = m;
  }

  public double computeDistance(int[] a, int[] b) {
    return Math.sqrt(Math.pow(b[0] - a[0], 2) + Math.pow(b[1] - a[1], 2));
  }

  public void executeAlgorithm(MTSPInstance instance) {
    Population population = new Population();
    population.setInstance(instance);
    population.setPopulationSize(POPULATION_SIZE);
    population.generateInitialPopulation();
    int count = 0;
    printBest(population, count);
    do {
      population.rouletteWheel();
      population.operate(M1_RATE, M2_RATE, C_RATE);
      count++;
      printBest(population, count);
    } while (count < ITERATIONS);
  }

  private void printBest(Population population, int count) {
    List<Solution> p = population.getPopulation();
    Solution bestS = p.get(0);
    double best = bestS.computeLongestTour();
    for (Solution s : p) {
      double t = s.computeLongestTour();
      if (t < best) {
        best = t;
        bestS = s;
      }
    }
    System.out
        .print("\nGeneration: " + count + ", Shortest longest tour: " + best + " for solution: ");
    int[] sol = bestS.getChromosome();
    for (int i = 0; i < sol.length; i++) {
      System.out.print(sol[i] + " ");
    }
    population.setBestTour(best);
    population.setBestSolution(bestS);
  }

  public static void main(String[] args){
      MTSPInstance instance = new MTSPInstance(0, 3);
      instance.executeAlgorithm(instance);
  }
}
