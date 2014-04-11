package console.transportation;

import console.items.Building;
import console.items.Elevator;

public class Controller {
	private Building building;	
	private Elevator elevator;
	private int currentStory;
	private int transferPassengers = 0;
	private final int totalPassengers;
	private final int totalStories;
	private Directions direction;
	
	public Controller(Building building) {
		super();
		this.building = building;
		elevator = building.getElevator();
		currentStory = elevator.getCurrentStory();
		totalPassengers = building.getTotalPassengers();
		totalStories = building.getStoreys().size();
	}

	public void run(){
		//testing
		System.out.println("In controller\nTotal passengers " + totalPassengers);
		
		while (transferPassengers < totalPassengers){
			direction = Directions.UP;
			for(currentStory = 0; currentStory < totalStories; currentStory++){
				
			}
		}
		
	}
	
	private static enum Directions{
		UP, DOWN
	}
}
