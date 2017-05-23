package com.mtsp;

import java.util.*;

public class Solution {
  private int[] chromosome;
  private MTSPInstance instance;
  private double cost;
  private double longestTour;

  public double getLongestTour() {
    return longestTour;
  }

  public void setLongestTour(double longestTour) {
    this.longestTour = longestTour;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public int[] getChromosome() {
    return chromosome;
  }

  public void setChromosome(int[] chromosome) {
    this.chromosome = chromosome;
  }

  public MTSPInstance getInstance() {
    return instance;
  }

  public void setInstance(MTSPInstance instance) {
    this.instance = instance;
  }

  public int[] generateSolution() {
    int m = instance.getM();
    int n = instance.getN();
    chromosome = new int[n + m - 1];
    Map<Integer, List<Integer>> initialSol = new HashMap<Integer, List<Integer>>();
    for (int i = 0; i < m; i++) {
      initialSol.put(i, new ArrayList<Integer>());
    }
    Random r = new Random();
    for (int i = 1; i < n; i++) {
      int assignee = r.nextInt(m);
      initialSol.get(assignee).add(i);
    }
    int k = 0;
    for (int i = 0; i < m; i++) {
      Collections.shuffle(initialSol.get(i));
      for (Integer city : initialSol.get(i)) {
        chromosome[k++] = city;
      }
      chromosome[n + i - 1] = initialSol.get(i).size();
    }
    return chromosome;
  }

  public double computeCost() {
    int[][] cities = instance.getCoordinates();
    double cost = 0;
    int k = 0;
    for (int i = 0; i < instance.getM(); i++) {
      cost += instance.computeDistance(cities[0], cities[chromosome[k]]);
      int j;
      for (j = 1; j < chromosome[instance.getN() - 1 + i]; j++) {
        cost += instance.computeDistance(cities[chromosome[k + j - 1]], cities[chromosome[k + j]]);
      }
      k = k + j;
      cost += instance.computeDistance(cities[chromosome[k]], cities[0]);
      k++;
    }
    return cost;
  }

  public double computeLongestTour() {
    int[][] cities = instance.getCoordinates();
    double longestTour = 0;
    double tour = 0;
    int k = 0;
    for (int i = 0; i < instance.getM(); i++) {
      tour += instance.computeDistance(cities[0], cities[chromosome[k]]);
      int j;
      for (j = 1; j < chromosome[instance.getN() - 1 + i]; j++) {
        tour += instance.computeDistance(cities[chromosome[k + j - 1]], cities[chromosome[k + j]]);
      }
      k = k + j;
      tour += instance.computeDistance(cities[chromosome[k]], cities[0]);
      if (tour > longestTour) {
        longestTour = tour;
      }
      k++;
      tour = 0;
    }
    return longestTour;
  }

  public int[] getTourStartPositions() {
    int k = 0;
    int[] startPositions = new int[instance.getM()];
    for (int i = 0; i < instance.getM(); i++) {
      startPositions[i] = k;
      k += chromosome[instance.getN() - 1 + i];
    }
    return startPositions;
  }

  public static void main(String[] args) {
    MTSPInstance instance = new MTSPInstance(0, 3);
    Solution s = new Solution();
    s.setInstance(instance);
    int[] sol = s.generateSolution();
    for (int i = 0; i < instance.getN() + instance.getM() - 1; i++) {
      System.out.print(sol[i] + " ");
    }
    double totalCost = s.computeCost();
    System.out.println(totalCost);
    double longestTour = s.computeLongestTour();
    System.out.println(longestTour);
    int[] startPos = s.getTourStartPositions();
    for (int i = 0; i < instance.getM(); i++) {
      System.out.print(startPos[i] + " ");
    }
    System.out.println("\nMutation1");
    Population p = new Population();
    p.setInstance(instance);
    s = p.mutation1(s);
    int[] sol1 = s.getChromosome();
    for (int i = 0; i < instance.getN() + instance.getM() - 1; i++) {
      System.out.print(sol1[i] + " ");
    }
    System.out.println("\nMutation2");
    s = p.mutation2(s);
    sol1 = s.getChromosome();
    for (int i = 0; i < instance.getN() + instance.getM() - 1; i++) {
      System.out.print(sol1[i] + " ");
    }
    System.out.println("\nCrossover");
    s = p.crossover(s);
    sol1 = s.getChromosome();
    for (int i = 0; i < instance.getN() + instance.getM() - 1; i++) {
      System.out.print(sol1[i] + " ");
    }
  }
}
