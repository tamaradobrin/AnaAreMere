package com.mtsp;

public class MTSPInstance {
    private int dimension;
    private int[][] coordinates;

    public MTSPInstance(int dimension, int[][] coordinates) {
        this.dimension = dimension;
        this.coordinates = coordinates;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[][] coordinates) {
        this.coordinates = coordinates;
    }
}
