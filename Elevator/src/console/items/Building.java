package console.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import console.constants.Containers;
import console.constants.TransportationState;
import console.transportation.Controller;

/**
 * Building
 */
public class Building {
	private final Elevator elevator;
	private final List<Storey> storeys;
	private final Controller controller;
	private final Properties properties;
	private final Set<Passenger> passengers;
	
	/**
	 * Creates a building based on the parameters passed
	 * @param properties
	 */
	public Building(Properties properties) {
		super();
		this.properties = properties;
		Random rnd = new Random();
		int storiesNumber = properties.getStoriesNumber();
		elevator = new Elevator(properties.getElevatorCapacity(), rnd.nextInt(storiesNumber));
		storeys = new ArrayList<>();
		for(int i = 0; i < properties.getStoriesNumber(); i++){
			storeys.add(new Storey(i));
		}
		passengers = new HashSet<>();
		for (int i = 0; i < properties.getPassengersNumber(); i++){
			int dispatchStory = rnd.nextInt(storiesNumber);
			int destinationStory = rnd.nextInt(storiesNumber - 1);
			if (destinationStory >= dispatchStory){
				destinationStory++;
			}
			Passenger passenger = new Passenger(i, dispatchStory, destinationStory, Containers.DISPATCH_STORY_CONTAINER);
			storeys.get(dispatchStory).addNewPassenger(passenger);
			passengers.add(passenger);
		}
		controller = new Controller(this);
	}

	public Properties getProperties() {
		return properties;
	}

	public Controller getController() {
		return controller;
	}

	public Set<Passenger> getPassengers() {
		return passengers;
	}
	
	public Elevator getElevator() {
		return elevator;
	}

	public List<Storey> getStoreys() {
		return storeys;
	}


	@Override
	public String toString() {
		return "Building [elevator=" + elevator + ", storeys=" + storeys + "]";
	}

	/**
	 * Validation process is complete transportation
	 * @return
	 * true - if all the conditions of the completion of transportation, 
	 * false - if transportation is not valid
	 */
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
		if(passengersNumber != properties.getPassengersNumber()){
			return false;
		}
		return true;
	}
}
