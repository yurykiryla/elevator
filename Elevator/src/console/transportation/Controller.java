package console.transportation;

import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


















import console.constants.Actions;
import console.constants.Directions;
import console.exceptions.SynchronizedException;
import console.items.Building;
import console.items.Elevator;
import console.items.Passenger;

public class Controller {
	private final Building building;	
	private final Elevator elevator;
	private int currentStory;
	private int transferPassengers = 0;
	private final int totalPassengers;
	private final int totalStories;
	private Directions direction;
	private Set<Passenger> elevatorContainer;
	private final int elevatorCapacity;
	private Logger logger = Logger.getLogger(Controller.class);
	private static final String FILENAME_LOG_PROPERTIES = "src/source/log4j.properties";
	
	public Controller(Building building) {
		super();
		this.building = building;
		elevator = building.getElevator();
		currentStory = elevator.getCurrentStory();
		totalPassengers = building.getProperties().getPassengersNumber();
		totalStories = building.getStoreys().size();
		elevatorContainer = elevator.getElevatorContainer();
		elevatorCapacity = elevator.getCapacity();
		PropertyConfigurator.configure(FILENAME_LOG_PROPERTIES);
		if(new Random().nextInt(2) == 0){
			direction = Directions.UP;
		}else{
			direction = Directions.DOWN;
		}
	}

	public void run(){
		logger.info(Actions.STARTING_TRANSPORTATION);
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
			logger.info(Actions.MOVING_ELEVATOR + getLoggingMessage());
		}
		
		if(building.isValidTransportation()){
			logger.info(Actions.VALID_TRANSPORTATION);
		}
		logger.info(Actions.COMPLETION_TRANSPORTATION);
		
	}
	
	public boolean boadingPassenger(Passenger passenger){
		if(passenger.getTransportationTask().getDirection() == direction && 
				elevatorContainer.size() < elevatorCapacity){
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
