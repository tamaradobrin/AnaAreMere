package com.mtsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

  private static final String ROOT_DIRECTORY = "instances";

  private static final String[] INSTANCE_FILES = {"\\eil51.txt", "\\eil76.txt"};

  private static final String FILENAME1 = ROOT_DIRECTORY + INSTANCE_FILES[0];
  private static final String FILENAME2 = ROOT_DIRECTORY + INSTANCE_FILES[1];

  public MTSPInstance readInstance(int pos) {
    int dimension = 0;
    int[][] node_coord;
    List<String> coordLines = new ArrayList<String>();
    BufferedReader br = null;
    FileReader fr = null;
    File f = new File(ROOT_DIRECTORY + INSTANCE_FILES[pos]);
    try {
      fr = new FileReader(f);
      br = new BufferedReader(fr);
      String sCurrentLine;
      br = new BufferedReader(new FileReader(ROOT_DIRECTORY + INSTANCE_FILES[pos]));
      int startLine = -1, index = 0;
      while ((sCurrentLine = br.readLine()) != null) {
        if (sCurrentLine.contains("DIMENSION")) {
          String dim = sCurrentLine.substring(sCurrentLine.indexOf(':') + 1).trim();
          dimension = Integer.parseInt(dim);
        }
        if (sCurrentLine.contains("NODE_COORD_SECTION")) {
          startLine = index + 1;
        }
        if (startLine > 0 && index >= startLine && !sCurrentLine.contains("EOF")) {
          coordLines.add(sCurrentLine);
        }
        index++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)
          br.close();
        if (fr != null)
          fr.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    node_coord = new int[dimension][2];
    for (String line : coordLines) {
      String[] coordS = line.split(" ");
      int i = Integer.parseInt(coordS[0]) - 1;
      int x = Integer.parseInt(coordS[1]);
      int y = Integer.parseInt(coordS[2]);
      node_coord[i][0] = x;
      node_coord[i][1] = y;
    }
    return new MTSPInstance(dimension, node_coord);
  }

  public static void main(String[] args) {
    MTSPInstance instance = new FileUtils().readInstance(1);
    System.out.println("Dimension: " + instance.getN());
    System.out.println("Coordinates: ");
    int[][] coordinates = instance.getCoordinates();
    for (int i = 0; i < instance.getN(); i++) {
      System.out.println(coordinates[i][0] + " " + coordinates[i][1]);
    }
  }
}
