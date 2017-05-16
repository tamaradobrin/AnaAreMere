package com.pso;

import java.util.Random;

import com.utils.FileUtils;
import com.utils.Function;

public class Population {

	int populationSize;
	Particle[] particles;
	Particle gbest;
	Function f;
	Random r;
	double c1 = 1, c2 = 2, c3 = 2;

	Population(int populationSize, Function f) {
		this.populationSize = populationSize;
		this.f = f;
	}

	public Particle[] generateInitialPopulation(int size) {
		particles = new Particle[size];
		for (int i = 0; i < size; i++) {
			particles[i] = new Particle(f);
		}
		gbest = computeGBest();
		return particles;
	}

	public Particle computeNext(Particle particle) {
		Particle nextP = new Particle(particle);
		r = new Random();
		double[] v = particle.getVelocity();
		double[] x = particle.getPresent();
		double[] g = getGBest().getPresent();
		double[] p = particle.getPBest();
		for (int i = 0; i < v.length; i++) {
			v[i] = r.nextDouble() * c1 * v[i] + r.nextDouble() * c2 * (g[i] - x[i])
					+ r.nextDouble() * c3 * (p[i] - x[i]);
			x[i] = f.getDoubleInRange(x[i] + v[i]);
		}
		nextP.setVelocity(v);
		nextP.setPresent(x);
		if (nextP.isPBest(x)) {
			nextP.setPBest(x);
		}
		if (isGBest(nextP)) {
			this.gbest = new Particle(nextP);
		}
		return nextP;
	}

	public void execute(int iterations) {
		generateInitialPopulation(populationSize);
		FileUtils fileUtils = new FileUtils();
		String fileName = fileUtils.createFile(f);
		int count = 0;
		while (count < iterations) {
			for (int i = 0; i < populationSize; i++) {
				particles[i] = new Particle(computeNext(particles[i]));
			}
			count++;
			printBest(count, fileName);
		}
	}

	public boolean isGBest(Particle sol) {
		return f.compute(sol.getPresent()) < f.compute(gbest.getPresent());
	}

	public void setGBest(Particle sol) {
		this.gbest = sol;
	}

	public Particle getGBest() {
		return gbest;
	}

	public Particle computeGBest() {
		gbest = new Particle(particles[0]);
		for (Particle particle : particles) {
			if (f.compute(particle.getPresent()) < f.compute(gbest.getPresent())) {
				gbest = new Particle(particle);
			}
		}
		return gbest;
	}

	public void printBest(int count, String fileName) {
		FileUtils fileUtils = new FileUtils();
		String text = "Generation: " + count + ", ";
		text += "Best value: " + f.compute(gbest.getPresent());
		System.out.println(text);
		fileUtils.updateFile(fileName, text);
	}
}
