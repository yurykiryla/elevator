package console.items;

import java.util.HashSet;
import java.util.Set;

public class Elevator {
	private final int capacity;
	private int currentStory;
	private Set<Passenger> elevatorContainer;

	public Elevator(int capacity, int currentStory) {
		super();
		this.capacity = capacity;
		this.currentStory = currentStory;
		elevatorContainer = new HashSet<>();
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public String toString() {
		return "Elevator [capacity=" + capacity + ", currentStory="
				+ currentStory + ", elevatorContainer=" + elevatorContainer
				+ "]";
	}
	
	
}
