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
	}

}
