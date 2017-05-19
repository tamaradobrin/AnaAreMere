package com.mtsp;

import java.util.Random;

public class Solution {
  private int[] chromosome;
  private MTSPInstance instance;

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

  public void generateSolution() {
    int m = instance.getM();
    int n = instance.getN();
    int[][] coordinates = instance.getCoordinates();
    Random r = new Random();
    for (int i = 0; i < n; i++) {
        int assignee = r.nextInt(m);
    }
  }
}
