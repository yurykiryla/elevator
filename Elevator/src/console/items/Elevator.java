package console.items;

import java.util.HashSet;
import java.util.Set;

/**
 * Elevator
 */
public class Elevator {
	private final int capacity;
	private int currentStory;
	private Set<Passenger> elevatorContainer;

	/**
	 * Create elevator
	 * @param capacity - elevator capacity
	 * @param currentStory - floor on which the elevator
	 */
	public Elevator(int capacity, int currentStory) {
		super();
		this.capacity = capacity;
		this.currentStory = currentStory;
		elevatorContainer = new HashSet<>();
	}

	public int getCapacity() {
		return capacity;
	}	

	public int getCurrentStory() {
		return currentStory;
	}

	public void setCurrentStory(int currentStory) {
		this.currentStory = currentStory;
	}

	public Set<Passenger> getElevatorContainer() {
		return elevatorContainer;
	}
	
	/**
	 * Passenger enters the elevator
	 * @param passenger
	 */
	public void boadingPassenger(Passenger passenger){
		elevatorContainer.add(passenger);
	}
	
	/**
	 * Passenger gets off the elevator
	 * @param passenger
	 */
	public void deboadingPassenger(Passenger passenger){
		elevatorContainer.remove(passenger);
	}

	@Override
	public String toString() {
		return "ElevatorContainer [capacity=" + capacity + ", currentStory="
				+ currentStory + ", elevatorContainer=" + elevatorContainer
				+ "]";
	}
	
	
}
