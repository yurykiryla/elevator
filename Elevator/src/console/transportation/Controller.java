package console.transportation;

import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



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
	private Logger logger = Logger.getLogger(Controller.class);
	
	public Controller(Building building) {
		super();
		this.building = building;
		elevator = building.getElevator();
		currentStory = elevator.getCurrentStory();
		totalPassengers = building.getTotalPassengers();
		totalStories = building.getStoreys().size();
		elevatorContainer = elevator.getElevatorContainer();
		elevatorCapacity = elevator.getCapacity();
		PropertyConfigurator.configure("src/source/log4j.properties");
	}

	public void run(){
		logger.info(Actions.STARTING_TRANSPORTATION);
		try{
			while (true){
				if (currentStory == 0){
					direction = Directions.UP;
				}
				if (currentStory == totalStories - 1){
					direction = Directions.DOWN;
				}
				synchronized (elevatorContainer) {
					if(elevatorContainer.size() > 0 && isDeboadingPassenger()){
						elevatorContainer.notifyAll();
					}
					while(elevatorContainer.size() > 0 && isDeboadingPassenger()){
						elevatorContainer.wait();
					}
				}
				
				Set<Passenger> dispatchStoryContainer = 
						building.getStoreys().get(currentStory).getDispatchStoryContainer();
				synchronized (dispatchStoryContainer) {
					if(elevatorContainer.size() < elevatorCapacity && isBoadingPassenger(dispatchStoryContainer)){
						dispatchStoryContainer.notifyAll();
					}
					while(elevatorContainer.size() < elevatorCapacity && 
							isBoadingPassenger(dispatchStoryContainer)){
						dispatchStoryContainer.wait();
					}
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
				logger.info(Actions.MOVING_ELEVATOR + getLoggingMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(building.isCompleteTransportation()){
			logger.info(Actions.COMPLETION_TRANSPORTATION);
		}
		
	}
	
	public boolean boadingPassenger(TransportationTask transportationTask){
		if(transportationTask.getDirection() == direction && elevatorContainer.size() < elevatorCapacity){
			Passenger passenger = transportationTask.getPassenger();
			logger.info(Actions.BOADING_OF_PASSENGER + getLoggingMessage(passenger));
			elevator.boadingPassenger(passenger);
			building.getStoreys().get(passenger.getDispatchStory()).boadingPassenger(passenger);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean deboadingPassenger(Passenger passenger){
		if(passenger.getDestinationStory() == currentStory){
			logger.info(Actions.DEBOADING_OF_PASSENGER + getLoggingMessage(passenger));
			building.getStoreys().get(currentStory).deboadingPassenger(passenger);;
			elevator.deboadingPassenger(passenger);
			transferPassengers++;
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isBoadingPassenger(Set<Passenger> dispatchStoryContainer){
		for(Passenger passenger : dispatchStoryContainer){
			if(passenger.getTransportationTask().getDirection() == direction){
				return true;
			}
		}
		return false;
	}
	private boolean isDeboadingPassenger(){
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
