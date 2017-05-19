package com.mtsp;

public class MTSPInstance {
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
}
