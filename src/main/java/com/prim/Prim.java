package com.prim;

import java.util.Arrays;
import java.util.List;

import com.utils.BinaryUtils;

public class Prim {

	public static void main(String[] args) {
		Prim p = new Prim();
		p.exec(true);
		p.exec(false);
		for (int i = 0; i < 31; i++) {
			System.out.println("f(" + i + ") = " + p.f(i));
		}
	}

	public void exec(boolean firstImprovement) {
		System.out.println("First improvement = " + firstImprovement);
		for (int i = 0; i < 31; i++) {
			int[] s = getBinarySolution(i);
			int[] sol = compute(s, firstImprovement);
			int solD = BinaryUtils.getDouble(sol);
			System.out.println(i + " este in bazinul de atractie al lui " + solD);
		}
	}

	public double f(int x) {
		return x * x * x - 60 * x * x + 900 * x + 100;
	}

	public int[] compute(int[] sol, boolean firstImprovement) {
		int n = 5;
		int[] oldSol = Arrays.copyOf(sol, n);
		int[] nextSol = Arrays.copyOf(sol, n);
		double val, nextVal;
		do {
			int solD = BinaryUtils.getDouble(nextSol);
			val = f(solD);
			oldSol = Arrays.copyOf(nextSol, n);
			if (firstImprovement)
				nextSol = computeFirstImprovement(oldSol);
			else
				nextSol = computeBestImprovement(oldSol);
			solD = BinaryUtils.getDouble(nextSol);
			nextVal = f(solD);
		} while (nextVal > val);
		return nextSol;
	}

	int[] computeFirstImprovement(int[] sol) {
		int n = 5;
		int nextSolD = BinaryUtils.getDouble(sol);
		double bestVal = f(nextSolD);
		int[] neighbour = Arrays.copyOf(sol, n);
		int neighbourD = 0;
		for (int i = 0; i < n + n / 2; i++) {
			int pos = (int) (Math.random() * n);
			neighbour[pos] = sol[pos] == 0 ? 1 : 0;
			neighbourD = BinaryUtils.getDouble(neighbour);
			double newVal = f(neighbourD);
			if (newVal > bestVal) {
				bestVal = newVal;
				return neighbour;
			}
		}
		return sol;

	}

	int[] computeBestImprovement(int[] sol) {
		int n = 5;
		int[] nextSol = Arrays.copyOf(sol, n);
		int nextSolD = BinaryUtils.getDouble(sol);
		double bestVal = f(nextSolD);
		List<int[]> neighboursI = BinaryUtils.getAllNeighbours(sol, n);
		int neighbourD = 0;
		for (int[] neighbour : neighboursI) {
			neighbourD = BinaryUtils.getDouble(neighbour);
			double newVal = f(neighbourD);
			if (newVal > bestVal) {
				bestVal = newVal;
				nextSol = Arrays.copyOf(neighbour, n);
			}
		}
		return nextSol;
	}

	private int[] getBinarySolution(int n) {
		int[] s = new int[5];
		int i = 0;
		while (n > 0 && i < 5) {
			s[i] = n % 2;
			n = n / 2;
			i++;
		}
		return s;
	}
}
