
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import console.beans.Passenger;
import console.containers.DispatchStoryContainer;
import console.factories.PropertiesFactory;
import console.interfaces.IPropertiesReader;


public class Runner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IPropertiesReader propertiesReader = PropertiesFactory.getClassFromFactory();
		int storiesNumber = propertiesReader.getStoriesNumber();
		int elevatorCapacity = propertiesReader.getElevatorCapacity();
		int passengersNumber = propertiesReader.getPassengersNumber();
		double animationBoost = propertiesReader.getAnimationBoost();
		
		System.out.println(storiesNumber + ";" + elevatorCapacity + ";" + passengersNumber + ";" +
				animationBoost);
		
		List<DispatchStoryContainer> dispatchStoryContainers = new ArrayList<>();
		for(int i = 0; i < storiesNumber; i++){
			dispatchStoryContainers.add(new DispatchStoryContainer(i));
		}
		
		Random rnd = new Random();
		for (int i = 0; i < passengersNumber; i++){
			int dispatchStory = rnd.nextInt(storiesNumber);
			int arrivalStory = rnd.nextInt(storiesNumber - 1);
			if (arrivalStory >= dispatchStory){
				arrivalStory++;
			}
			
			System.out.println(dispatchStory + ";" + arrivalStory);
			
			dispatchStoryContainers.get(dispatchStory).addPassenger(new Passenger(i, arrivalStory));
			
		}
		
		
	}
	
}
