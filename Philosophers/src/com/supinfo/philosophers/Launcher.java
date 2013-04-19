package com.supinfo.philosophers;


public class Launcher {
	
	public static void main(String[] args) {
		DiningTable diningTable = new DiningTable();
		
		Philosopher[] philosophers = buildPhilosophersArray(5, diningTable);
		
		for (int i = 0; i < philosophers.length; i++) {
			Philosopher philosopher = philosophers[i];
			new Thread(philosopher, "Philosopher " + i).start();
		}
	}
	
	private static Philosopher[] buildPhilosophersArray(int numberOfPhilosophers, DiningTable diningTable) {
		Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
		for (int i = 0; i < philosophers.length; i++) {
			philosophers[i] = new Philosopher(diningTable);
		}
		return philosophers;
	}

}
