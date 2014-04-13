package console.items;

import java.util.List;

import console.constants.TransportationState;

public class Building {
	private final Elevator elevator;
	private final List<Storey> storeys;
	private final int totalPassengers;
	
	public Building(Elevator elevator, List<Storey> storeys, int totalPassengers) {
		super();
		this.elevator = elevator;
		this.storeys = storeys;
		this.totalPassengers = totalPassengers;
	}
	
	public int getTotalPassengers(){
		return totalPassengers;
	}

	@Override
	public String toString() {
		return "Building [elevator=" + elevator + ", storeys=" + storeys + "]";
	}

	public Elevator getElevator() {
		return elevator;
	}

	public List<Storey> getStoreys() {
		return storeys;
	}
	
	public boolean isValidTransportation(){
		if(!elevator.getElevatorContainer().isEmpty()){
			return false;
		}
		int passengersNumber = 0;
		for(Storey storey : storeys){
			if(!storey.getDispatchStoryContainer().isEmpty()){
				return false;
			}
			for(Passenger passenger : storey.getArrivalStoryContainer()){
				if(passenger.getTransportationState() != TransportationState.COMPLETED){
					return false;
				}
				if(passenger.getDestinationStory() != storey.getStoreyNumber()){
					return false;
				}
				passengersNumber++;
			}
		}
		if(passengersNumber != totalPassengers){
			return false;
		}
		return true;
	}
}
