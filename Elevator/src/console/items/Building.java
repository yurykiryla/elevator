package console.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import console.constants.TransportationState;
import console.exceptions.SynchronizedException;
import console.transportation.Controller;
import console.transportation.TransportationTask;

public class Building {
	private final Elevator elevator;
	private final List<Storey> storeys;
	private final Controller controller;
	private final ThreadGroup threadGroup = new ThreadGroup("PassengerTasks");
	private final Properties properties;
	
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
		
		for (int i = 0; i < properties.getPassengersNumber(); i++){
			int dispatchStory = rnd.nextInt(storiesNumber);
			int destinationStory = rnd.nextInt(storiesNumber - 1);
			if (destinationStory >= dispatchStory){
				destinationStory++;
			}
			Passenger passenger = new Passenger(i, dispatchStory, destinationStory);
			storeys.get(dispatchStory).addNewPassenger(passenger);
		}
		
		controller = new Controller(this);
		for(Storey storey : storeys){
			Set<Passenger> dispatchStoryContainer = storey.getDispatchStoryContainer();
			for(Passenger passenger : dispatchStoryContainer){
				try{
					synchronized (this) {
						Thread transportationTask = 
								new Thread(threadGroup, new TransportationTask(passenger, this, controller));
						transportationTask.start();
						this.wait();
					}
				}catch(InterruptedException e){
					throw new SynchronizedException(e);
				}
			}
		}
	}

	public ThreadGroup getThreadGroup() {
		return threadGroup;
	}

	public Properties getProperties() {
		return properties;
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
		if(passengersNumber != properties.getPassengersNumber()){
			return false;
		}
		return true;
	}
}
