
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import console.factories.PropertiesFactory;
import console.interfaces.IPropertiesReader;
import console.items.Passenger;
import console.items.Storey;


public class Runner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IPropertiesReader propertiesReader = PropertiesFactory.getClassFromFactory();
		int storiesNumber = propertiesReader.getStoriesNumber();
		int elevatorCapacity = propertiesReader.getElevatorCapacity();
		int passengersNumber = propertiesReader.getPassengersNumber();
		double animationBoost = propertiesReader.getAnimationBoost();
		
		//testing
		System.out.println(storiesNumber + ";" + elevatorCapacity + ";" + passengersNumber + ";" +
				animationBoost);
		
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
			
			//testing
			System.out.println(dispatchStory + ";" + destinationStory);
			
			Passenger passenger = new Passenger(i, dispatchStory, destinationStory);
			storeys.get(dispatchStory).addNewPassenger(passenger);
		}
	}
}
