package console.items;

import java.util.List;
import java.util.Set;

import console.constants.TransportationState;
import console.exceptions.SynchronizedException;
import console.transportation.Controller;
import console.transportation.TransportationTask;

public class Building {
	private final Elevator elevator;
	private final List<Storey> storeys;
	private final int totalPassengers;
	private final Controller controller;
	
	public Building(Elevator elevator, List<Storey> storeys, int totalPassengers) {
		super();
		this.elevator = elevator;
		this.storeys = storeys;
		this.totalPassengers = totalPassengers;
		controller = new Controller(this);
		for(Storey storey : storeys){
			Set<Passenger> dispatchStoryContainer = storey.getDispatchStoryContainer();
			for(Passenger passenger : dispatchStoryContainer){
				try{
					synchronized (this) {
						Thread transportationTask = new Thread(new TransportationTask(passenger, this, controller));
						transportationTask.start();
						this.wait();
					}
				}catch(InterruptedException e){
					throw new SynchronizedException(e);
				}
			}
		}
	}
	
	public int getTotalPassengers(){
		return totalPassengers;
	}

	public Controller getController() {
		return controller;
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
