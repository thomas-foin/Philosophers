package com.supinfo.philosophers;

public class Fork {
	
	private Philosopher philosopher;	
	
	public Fork take(Philosopher philosopher) {
		this.philosopher = philosopher;
		return this;
	}
	
	public void leave() {
		this.philosopher = null;
	}
	
	public boolean isFree() {
		return philosopher == null;
	}

}
