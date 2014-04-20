package console.transportation;

import java.util.Random;
import java.util.Set;

import console.constants.Actions;
import console.constants.Directions;
import console.constants.TransportationState;
import console.exceptions.SynchronizedException;
import console.items.Building;
import console.items.Elevator;
import console.items.Passenger;
import console.items.Storey;
import console.logging.ElevatorLogger;

public class Controller implements Runnable{
	private final Building building;	
	private final Elevator elevator;
	private int currentStory;
	private int transferPassengers = 0;
	private final int totalPassengers;
	private final int totalStories;
	private Directions direction;
	private Set<Passenger> elevatorContainer;
	private final int elevatorCapacity;
	private final int delay;
	
	public Controller(Building building) {
		super();
		this.building = building;
		elevator = building.getElevator();
		currentStory = elevator.getCurrentStory();
		totalPassengers = building.getProperties().getPassengersNumber();
		totalStories = building.getStoreys().size();
		elevatorContainer = elevator.getElevatorContainer();
		elevatorCapacity = elevator.getCapacity();
		delay = building.getProperties().getDelay();
		if(new Random().nextInt(2) == 0){
			direction = Directions.UP;
		}else{
			direction = Directions.DOWN;
		}
	}
	
	public Building getBuilding() {
		return building;
	}
	

	public int getTransferPassengers() {
		return transferPassengers;
	}

	public int getTotalPassengers() {
		return totalPassengers;
	}

	public void run(){
		ElevatorLogger.LOGGER.info(Actions.STARTING_TRANSPORTATION);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true){
			if (currentStory == 0){
				direction = Directions.UP;
			}
			if (currentStory == totalStories - 1){
				direction = Directions.DOWN;
			}
			try{
				synchronized (elevatorContainer) {
					if(isDeboadingPassenger()){
						elevatorContainer.notifyAll();
					}
					while(isDeboadingPassenger()){
						elevatorContainer.wait();
					}
				}
				
				Set<Passenger> dispatchStoryContainer = 
						building.getStoreys().get(currentStory).getDispatchStoryContainer();
				synchronized (dispatchStoryContainer) {
					if(isBoadingPassenger(dispatchStoryContainer)){
						dispatchStoryContainer.notifyAll();
					}
					while(isBoadingPassenger(dispatchStoryContainer)){
						dispatchStoryContainer.wait();
					}
				}
			}catch(InterruptedException e){
				throw new SynchronizedException(e);
			}
			
			if(transferPassengers == totalPassengers){
				break;
			}
			
			switch (direction) {
			case UP:
				currentStory++;
				break;
			case DOWN:
				currentStory--;
				break;
			}
			ElevatorLogger.LOGGER.info(Actions.MOVING_ELEVATOR + getLoggingMessage());
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			elevator.setCurrentStory(currentStory);
		}
		
		if(building.isValidTransportation()){
			ElevatorLogger.LOGGER.info(Actions.VALID_TRANSPORTATION);
		}
		ElevatorLogger.LOGGER.info(Actions.COMPLETION_TRANSPORTATION);
	}
	
	public synchronized boolean boadingPassenger(Passenger passenger){
		if(passenger.getTransportationTask().getDirection() == direction && 
				elevatorContainer.size() < elevatorCapacity){
			ElevatorLogger.LOGGER.info(Actions.BOADING_OF_PASSENGER + getLoggingMessage(passenger));
			elevator.boadingPassenger(passenger);
			building.getStoreys().get(passenger.getDispatchStory()).boadingPassenger(passenger);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
		}
	}
	
	public synchronized boolean deboadingPassenger(Passenger passenger){
		if(passenger.getDestinationStory() == currentStory){
			ElevatorLogger.LOGGER.info(Actions.DEBOADING_OF_PASSENGER + getLoggingMessage(passenger));
			building.getStoreys().get(currentStory).deboadingPassenger(passenger);;
			elevator.deboadingPassenger(passenger);
			transferPassengers++;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
		}
	}
	
	public void abortTransportation(){
		ElevatorLogger.LOGGER.info(Actions.ABORTING_TRANSPORTATION);
		abortTransportation(elevatorContainer, "elevatorContainer");
		for(Storey storey : building.getStoreys()){
			abortTransportation(storey.getDispatchStoryContainer(), "dispatchStoryContainer");
			abortTransportation(storey.getArrivalStoryContainer(), "arrivalStoryContainer");
		}
	}
	
	private void abortTransportation(Set<Passenger> passengers, String containerName){
		if(!passengers.isEmpty()){
			for(Passenger passenger : passengers){
				if(passenger.getTransportationState() != TransportationState.COMPLETED){
					passenger.setTransportationState(TransportationState.ABORTED);
				}
				ElevatorLogger.LOGGER.info("passenger" + passenger.getId() + " in " + containerName + ", transportation state - " + passenger.getTransportationState());
			}
		}
	}
	
	private boolean isBoadingPassenger(Set<Passenger> dispatchStoryContainer){
		if(elevatorContainer.size() == elevatorCapacity){
			return false;
		}
		for(Passenger passenger : dispatchStoryContainer){
			if(passenger.getTransportationTask().getDirection() == direction){
				return true;
			}
		}
		return false;
	}
	private boolean isDeboadingPassenger(){
		if(elevatorContainer.isEmpty()){
			return false;
		}
		for(Passenger passenger : elevatorContainer){
			if(passenger.getDestinationStory() == currentStory){
				return true;
			}
		}
		return false;
	}

	private String getLoggingMessage(){
		int previousStory;
		if (direction == Directions.UP){
			previousStory = currentStory - 1;
		}else {
			previousStory = currentStory + 1;
		}
		return " (from story-" + previousStory + " to story-" + currentStory + ")";
	}
	
	private String getLoggingMessage(Passenger passenger){
		return " (passanger" + passenger.getId() + " on story-" + currentStory + ")";
	}
	
	
}
