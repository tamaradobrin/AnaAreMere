package com.mtsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
      for (Integer city : initialSol.get(i)) {
        chromosome[k++] = city;
      }
      chromosome[n + i - 1] = initialSol.get(i).size();
    }
    return chromosome;
  }

  public static void main(String[] args) {
    MTSPInstance instance = new MTSPInstance(0, 2);
    Solution s = new Solution();
    s.setInstance(instance);
    int[] sol = s.generateSolution();
    for (int i = 0; i < instance.getN() + instance.getM(); i++) {
      System.out.print(sol[i] + " ");
    }
  }
}
