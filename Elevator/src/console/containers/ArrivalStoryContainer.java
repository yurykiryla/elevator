package console.containers;

import java.util.Set;

import console.constants.TransportationState;
import console.items.Passenger;

public class ArrivalStoryContainer {
	private final int story;
	private Set<Passenger> passengers;
	
	public ArrivalStoryContainer(int story) {
		super();
		this.story = story;
	}
	
	public void addPassenger(Passenger passenger){
		passengers.add(passenger);
	}
	
	public int getPassengersNumber(){
		return passengers.size();
	}
	
	public boolean isVerify(){
		for(Passenger passenger : passengers){
			if(passenger.getDestinationStory() != story || 
					passenger.getTransportationState() != TransportationState.COMPLETED) {
				return false;
			}
		}
		return true;
	}
}
