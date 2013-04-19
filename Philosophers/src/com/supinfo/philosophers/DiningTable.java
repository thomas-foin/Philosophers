package com.supinfo.philosophers;

import java.util.ArrayList;
import java.util.List;

public class DiningTable {
	
	private int numberOfSeat;
	private List<Fork> forks;
	private List<Philosopher> philosophers;
	
	
	public DiningTable() {		
		forks = new ArrayList<Fork>();
		philosophers = new ArrayList<Philosopher>();
	}
	
	public void addPhilosopher(Philosopher philosopher) {
		philosophers.add(philosopher);
		forks.add(new Fork());
		numberOfSeat++;
	}
	
	public void removePhilosopher(Philosopher philosopher) {
		philosophers.remove(philosopher);
		forks.remove(forks.size() - 1);
		numberOfSeat--;
	}
	
	public synchronized Fork[] takeForks(Philosopher philosopher) throws InterruptedException {
		int seatNumber = philosophers.indexOf(philosopher);
		
		if(seatNumber > -1) {
			
			Fork fork1 = forks.get(seatNumber);
			Fork fork2 = forks.get((seatNumber + 1) % numberOfSeat);
			
			while(!fork1.isFree() || !fork2.isFree()) {
				wait();
			}
			
			fork1.take(philosopher);
			fork2.take(philosopher);
			
			return new Fork[] {fork1, fork2};
		
		} else {
			throw new IllegalArgumentException("An unknown philosopher tried to take forks.");
		}
	}

}
