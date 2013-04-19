package com.supinfo.philosophers;

import java.util.Random;

public class Philosopher implements Runnable {
	
	public static final int MIN_THINKING = 0;
	public static final int MAX_THINKING = 3000;
	
	public static final int MIN_EATING = 0;
	public static final int MAX_EATING = 3000;
	
	public static Random random = new Random();
	
	private DiningTable diningTable;
	private PhilosopherState philosopherState;
	private boolean run;
	private Fork[] forks;
	
	
	public Philosopher(DiningTable diningTable) {
		this.philosopherState = PhilosopherState.THINKING;
		this.run = true;
		
		this.diningTable = diningTable;
		diningTable.addPhilosopher(this);
	}
	
	public boolean isThinking() {
		return philosopherState == PhilosopherState.THINKING;
	}
	
	public boolean isEating() {
		return philosopherState == PhilosopherState.EATING;
	}
	
	public boolean isHungry() {
		return philosopherState == PhilosopherState.HUNGRY;
	}
	
	public void stop() {
		run = false;
		leaveForks();
	}

	@Override
	public void run() {
		while (run) {
			switch (philosopherState) {
			case THINKING: 
				think(); 
				break;
			case HUNGRY:
				hungry();
				break;
			case EATING:
				eat();
				break;
			}
		}
		
	}
	
	private void eat() {
		System.out.println(Thread.currentThread().getName() + " eat !");
		
		int eatingDuration = random.nextInt(MAX_EATING - MIN_EATING) + MIN_EATING;
		try {
			Thread.sleep(eatingDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		leaveForks();
		philosopherState = PhilosopherState.THINKING;
	}

	private void leaveForks() {
		for (Fork fork : forks) {
			fork.leave();
		}
		synchronized (diningTable) {
			diningTable.notifyAll();
		}
		forks = null;
	}

	private void hungry() {
		System.out.println(Thread.currentThread().getName() + " is hungry !");
		long begin = System.currentTimeMillis();
		try {
			this.forks = diningTable.takeForks(this);
			philosopherState = PhilosopherState.EATING;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + " waited " + (end - begin));
	}

	private void think() {
		System.out.println(Thread.currentThread().getName() + " think !");
		
		int thinkingDuration = random.nextInt(MAX_THINKING - MIN_THINKING) + MIN_THINKING;
		try {
			Thread.sleep(thinkingDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		philosopherState = PhilosopherState.HUNGRY;
	}

}
