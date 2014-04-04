package console.implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import console.interfaces.IPropertiesReader;

public class FilePropertiesReaderImpl implements IPropertiesReader {
	Map<String, String> properties;
	private final String FILENAME = "src/source/config.property";
	private final String REGEX = ";";
	private final String KEY_STORIES_NUMBER = "storiesNumber";
	private final String KEY_ELEVATOR_CAPACITY = "elevatorCapacity";
	private final String KEY_PASSENGERS_NUMBER = "passengersNumber";
	private final String KEY_ANIMATION_BOOST = "animationBoost";
	
	public FilePropertiesReaderImpl(){
		try(Scanner sc = new Scanner(new File(FILENAME))){
			properties = new HashMap<String, String>();
			while(sc.hasNext()){
				String line[] = sc.nextLine().split(REGEX);
				properties.put(line[0], line[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getStoriesNumber() {
		// TODO Auto-generated method stub
		return Integer.parseInt(properties.get(KEY_STORIES_NUMBER));
	}

	public int getElevatorCapacity() {
		// TODO Auto-generated method stub
		return Integer.parseInt(properties.get(KEY_ELEVATOR_CAPACITY));
	}

	public int getPassengersNumber() {
		// TODO Auto-generated method stub
		return Integer.parseInt(properties.get(KEY_PASSENGERS_NUMBER));
	}

	public double getAnimationBoost() {
		// TODO Auto-generated method stub
		return Double.parseDouble(properties.get(KEY_ANIMATION_BOOST));
	}

}
