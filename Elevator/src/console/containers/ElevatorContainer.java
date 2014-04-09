package console.containers;

import java.util.HashSet;
import java.util.Set;

import console.items.Passenger;

public class ElevatorContainer {
	private Set<Passenger> passengers;
	private final int elevatorCapacity;
	
	public ElevatorContainer(int elevatorCapacity){
		this.elevatorCapacity = elevatorCapacity;
		passengers = new HashSet<>();
	}
	
	public boolean isEmpty(){
		return passengers.isEmpty();
	}
	
	public boolean isFull(){
		return passengers.size() == elevatorCapacity;
	}
	
	public void putPassenger(Passenger passenger){
		if(isFull()){
			throw new IllegalArgumentException();
		}
		passengers.add(passenger);
	}
}
