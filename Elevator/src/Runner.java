
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import console.exceptions.SynchronizedException;
import console.factories.PropertiesFactory;
import console.interfaces.IPropertiesReader;
import console.items.Building;
import console.items.Elevator;
import console.items.Passenger;
import console.items.Storey;
import console.transportation.Controller;
import console.transportation.TransportationTask;


public class Runner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IPropertiesReader propertiesReader = PropertiesFactory.getClassFromFactory();
		int storiesNumber = propertiesReader.getStoriesNumber();
		int elevatorCapacity = propertiesReader.getElevatorCapacity();
		int passengersNumber = propertiesReader.getPassengersNumber();
		double animationBoost = propertiesReader.getAnimationBoost();
		List<Storey> storeys = new ArrayList<>();
		for(int i = 0; i < storiesNumber; i++){
			storeys.add(new Storey(i));
		}
		
		Random rnd = new Random();
		for (int i = 0; i < passengersNumber; i++){
			int dispatchStory = rnd.nextInt(storiesNumber);
			int destinationStory = rnd.nextInt(storiesNumber - 1);
			if (destinationStory >= dispatchStory){
				destinationStory++;
			}
			Passenger passenger = new Passenger(i, dispatchStory, destinationStory);
			storeys.get(dispatchStory).addNewPassenger(passenger);
		}
		
		Building building = new Building(new Elevator(elevatorCapacity, 0), storeys, passengersNumber);
		
		Controller controller = new Controller(building);
		
		for(Storey storey : storeys){
			Set<Passenger> dispatchStoryContainer = storey.getDispatchStoryContainer();
			for(Passenger passenger : dispatchStoryContainer){
				try{
					synchronized (building) {
						Thread transportationTask = new Thread(new TransportationTask(passenger, building, controller));
						transportationTask.start();
						building.wait();
					}
				}catch(InterruptedException e){
					throw new SynchronizedException(e);
				}
			}
		}
		
		
		if(animationBoost == 0.0){
			controller.run();
		}
	}
}
