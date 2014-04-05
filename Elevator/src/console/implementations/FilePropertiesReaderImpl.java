package console.implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import console.interfaces.IPropertiesReader;

public class FilePropertiesReaderImpl implements IPropertiesReader {
	private Map<String, String> properties;
	private static final String FILENAME = "src/source/config.property";
	private static final String REGEX = ";";
	private static final String KEY_STORIES_NUMBER = "storiesNumber";
	private static final String KEY_ELEVATOR_CAPACITY = "elevatorCapacity";
	private static final String KEY_PASSENGERS_NUMBER = "passengersNumber";
	private static final String KEY_ANIMATION_BOOST = "animationBoost";
	
	public FilePropertiesReaderImpl(){
		Scanner sc = null;
		try{
			sc = new Scanner(new File(FILENAME));
			properties = new HashMap<String, String>();
			while(sc.hasNext()){
				String line[] = sc.nextLine().split(REGEX);
				properties.put(line[0], line[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sc != null){
				sc.close();
			}
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
