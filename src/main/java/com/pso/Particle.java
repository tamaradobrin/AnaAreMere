package com.pso;

import java.util.Arrays;
import java.util.Random;

import com.utils.Function;

public class Particle {

	private double[] present;
	private double[] pbest;
	private double[] v;
	Random r;
	private Function f;

	public Particle(Function f) {
		this.f = f;
		r = new Random();
		present = new double[f.m];
		v = new double[f.m];
		for (int i = 0; i < f.m; i++) {
			present[i] = f.getDouble(r.nextDouble());
			v[i] = r.nextDouble();
		}
		pbest = present;
	}

	public Particle(Particle p) {
		this.present = Arrays.copyOf(p.getPresent(), p.getPresent().length);
		this.pbest = Arrays.copyOf(p.getPBest(), p.getPBest().length);
		this.v = Arrays.copyOf(p.getVelocity(), p.getVelocity().length);
		this.f = p.f;
	}

	public double[] getPresent() {
		return present;
	}

	public void setPresent(double[] particle) {
		this.present = Arrays.copyOf(particle, particle.length);
	}

	public double[] getPBest() {
		return pbest;
	}

	public void setPBest(double[] best) {
		this.pbest = Arrays.copyOf(best, best.length);
	}

	public boolean isPBest(double[] sol) {
		return f.compute(sol) < f.compute(pbest);
	}

	public double[] getVelocity() {
		return v;
	}

	public void setVelocity(double[] v) {
		this.v = Arrays.copyOf(v, v.length);
	}
}
