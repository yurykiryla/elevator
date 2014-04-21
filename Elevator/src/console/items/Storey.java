package console.items;

import java.util.HashSet;
import java.util.Set;

/**
 * Storey
 * @author Yury
 *
 */
public class Storey {
	private final int storeyNumber;
	private Set<Passenger> dispatchStoryContainer;
	private Set<Passenger> arrivalStoryContainer;

	/**
	 * creates a floor with a specified number
	 * @param storeyNumber
	 */
	public Storey(int storeyNumber) {
		super();
		this.storeyNumber = storeyNumber;
		dispatchStoryContainer = new HashSet<>();
		arrivalStoryContainer = new HashSet<>();
	}
	
	public void addNewPassenger(Passenger passenger){
		dispatchStoryContainer.add(passenger);
	}

	public Set<Passenger> getDispatchStoryContainer() {
		return dispatchStoryContainer;
	}	
	
	public Set<Passenger> getArrivalStoryContainer() {
		return arrivalStoryContainer;
	}
	
	public int getStoreyNumber() {
		return storeyNumber;
	}

	/**
	 * Passenger enters the elevator
	 * @param passenger
	 */
	public void boadingPassenger(Passenger passenger){
		dispatchStoryContainer.remove(passenger);
	}

	/**
	 * Passenger gets off the elevator
	 * @param passenger
	 */
	public void deboadingPassenger(Passenger passenger){
		arrivalStoryContainer.add(passenger);
	}

	@Override
	public String toString() {
		return "Storey [storeyNumber=" + storeyNumber
				+ ", dispatchStoryContainer=" + dispatchStoryContainer
				+ ", arrivalStoryContainer=" + arrivalStoryContainer + "]";
	}
}
