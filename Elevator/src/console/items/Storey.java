package console.items;

import java.util.HashSet;
import java.util.Set;

public class Storey {
	private final int storeyNumber;
	private Set<Passenger> dispatchStoryContainer;
	private Set<Passenger> arrivalStoryContainer;

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

	@Override
	public String toString() {
		return "Storey [storeyNumber=" + storeyNumber
				+ ", dispatchStoryContainer=" + dispatchStoryContainer
				+ ", arrivalStoryContainer=" + arrivalStoryContainer + "]";
	}
}
