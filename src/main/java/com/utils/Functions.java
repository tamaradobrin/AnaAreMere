package com.utils;

public class Functions {
	public static double rastrigin(double[] x, int n) {
		double sum = 10 * n;
		for (int i = 0; i < n; i++) {
			sum += x[i] * x[i] - 10 * Math.cos(2 * Math.PI * x[i]);
		}
		return sum;
	}

	public static double griewangk(double[] x, int n) {
		double sum = 0, prod = 1;
		for (int i = 0; i < n; i++) {
			sum += (x[i] * x[i]) / 4000;
			prod *= Math.cos(x[i] / Math.sqrt(i + 1));
		}
		return sum - prod + 1;
	}

	public static double rosenbrock(double[] x, int n) {
		double sum = 0;
		for (int i = 0; i < n - 1; i++) {
			sum += 100 * Math.pow(x[i + 1] - x[i] * x[i], 2) + Math.pow(1 - x[i], 2);
		}
		return sum;
	}

	public static double camel(double x1, double x2) {
		return (4 - 2.1 * Math.pow(x1, 2) + Math.pow(x1, 4) / 3) * Math.pow(x1, 2) + x1 * x2
				+ (-4 + 4 * Math.pow(x2, 2)) * Math.pow(x2, 2);
	}

	public static double computeFunction(double[] x, int n, int function) {
		switch (function) {
		case 1:
			return rastrigin(x, n);
		case 2:
			return griewangk(x, n);
		case 3:
			return rosenbrock(x, n);
		case 4:
			return camel(x[0], x[1]);
		default:
			return 0;
		}
	}
}
