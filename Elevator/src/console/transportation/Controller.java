package console.transportation;

import java.util.Set;

import console.constants.Actions;
import console.constants.Directions;
import console.items.Building;
import console.items.Elevator;
import console.items.Passenger;

public class Controller {
	private Building building;	
	private Elevator elevator;
	private int currentStory;
	private int transferPassengers = 0;
	private final int totalPassengers;
	private final int totalStories;
	private Directions direction = Directions.UP;
	private Set<Passenger> elevatorContainer;
	private final int elevatorCapacity;
	
	public Controller(Building building) {
		super();
		this.building = building;
		elevator = building.getElevator();
		currentStory = elevator.getCurrentStory();
		totalPassengers = building.getTotalPassengers();
		totalStories = building.getStoreys().size();
		elevatorContainer = elevator.getElevatorContainer();
		elevatorCapacity = elevator.getCapacity();
	}

	public synchronized void run(){
		//testing
		System.out.println("In controller\nTotal passengers " + totalPassengers);
		
		try{
			while (transferPassengers < totalPassengers){
				direction = Directions.UP;
				for(currentStory = 0; currentStory < totalStories; currentStory++){
					synchronized (elevatorContainer) {
						if(elevatorContainer.size() > 0){
							elevatorContainer.notifyAll();
						}
					}
					while(elevatorContainer.size() > 0 || isDeboadingPassenger()){
						wait();
					}
					
					Set<Passenger> dispatchStoryContainer = 
							building.getStoreys().get(currentStory).getDispatchStoryContainer();
					synchronized (dispatchStoryContainer) {
						if(elevatorContainer.size() < elevatorCapacity){
							dispatchStoryContainer.notifyAll();
						}
					}
					while(elevatorContainer.size() < elevatorCapacity || 
							isBoadingPassenger(dispatchStoryContainer)){
						wait();
					}
					System.out.println(Actions.MOVING_ELEVATOR + " from story " + currentStory + " to story" + 
							(currentStory + 1));
				}
				direction = Directions.DOWN;
				for(currentStory = totalStories - 1; currentStory > -1; currentStory--){
					synchronized (elevatorContainer) {
						if(elevatorContainer.size() > 0){
							elevatorContainer.notifyAll();
						}
					}
					while(elevatorContainer.size() > 0 || isDeboadingPassenger()){
						wait();
					}
					
					Set<Passenger> dispatchStoryContainer = 
							building.getStoreys().get(currentStory).getDispatchStoryContainer();
					synchronized (dispatchStoryContainer) {
						if(elevatorContainer.size() < elevatorCapacity){
							dispatchStoryContainer.notifyAll();
						}
					}
					while(elevatorContainer.size() < elevatorCapacity || 
							isBoadingPassenger(dispatchStoryContainer)){
						wait();
					}
					System.out.println(Actions.MOVING_ELEVATOR + " from story " + currentStory + " to story" + 
							(currentStory - 1));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Finish");
	}
	
	public synchronized boolean boadingPassenger(TransportationTask transportationTask){
		if(transportationTask.getDirection() == direction && elevatorContainer.size() < elevatorCapacity){
			Passenger passenger = transportationTask.getPassenger();
			System.out.println(Actions.BOADING_OF_PASSENGER + " (" + "passenger" + passenger.getId() +
					" on story-" + currentStory);
			elevator.boadingPassenger(passenger);
			building.getStoreys().get(passenger.getDispatchStory()).boadingPassenger(passenger);
			return true;
		}else{
			return false;
		}
	}
	
	public synchronized boolean deboadingPassenger(Passenger passenger){
		if(passenger.getDestinationStory() == currentStory){
			System.out.println(Actions.DEBOADING_OF_PASSENGER + " (" + "passenger" + passenger.getId() +
					" on story-" + currentStory);
			building.getStoreys().get(currentStory).deboadingPassenger(passenger);;
			elevator.deboadingPassenger(passenger);
			transferPassengers++;
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isBoadingPassenger(Set<Passenger> dispatchStoryContainer){
		for(Passenger passenger : dispatchStoryContainer){
			if(passenger.getTransportationTask().getDirection() == direction){
				return true;
			}
		}
		return false;
	}
	public boolean isDeboadingPassenger(){
		for(Passenger passenger : elevatorContainer){
			if(passenger.getDestinationStory() == currentStory){
				return true;
			}
		}
		return false;
	}

	public int getElevatorCapacity() {
		return elevatorCapacity;
	}
	
}
