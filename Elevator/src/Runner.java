
import java.util.Random;

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
		
		for(int i = 0; i < 100; i++){
			System.out.println(getRandomStory(storiesNumber));
		}
	}
	
	private static int getRandomStory(int storiesNumber){
		Random rnd = new Random();
		return rnd.nextInt(storiesNumber);
	}
	private static int getRandomStory(int storiesNumber, int dispatchStory){
		int temp = getRandomStory(storiesNumber - 1);
		if(temp >= dispatchStory){
			temp++;
		}
		return temp;
	}
}
