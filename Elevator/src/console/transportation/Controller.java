package console.transportation;

import console.items.Building;
import console.items.Elevator;

public class Controller {
	private Building building;	
	private Elevator elevator;
	private int currentStory;
	
	public Controller(Building building) {
		super();
		this.building = building;
		elevator = building.getElevator();
		currentStory = elevator.getCurrentStory();
	}

	public void run(){
		//testing
		System.out.println("In controller");
		
		
	}
}
