package console.transportation;

import java.util.List;
import java.util.Set;

import console.items.Building;
import console.items.Passenger;
import console.items.Storey;

public class TransportationProcess {
	private Building building;

	public TransportationProcess(Building building) {
		super();
		this.building = building;
	}
	
	public void start(){
		//testing
		System.out.println("Starting transportation\n" + building);
		
		List<Storey> storeys = building.getStoreys();
		for(Storey storey : storeys){
			Set<Passenger> dispatchStoryContainer = storey.getDispatchStoryContainer();
			for(Passenger passenger : dispatchStoryContainer){
				Thread transportationTask = new Thread(new TransportationTask(passenger, building));
				transportationTask.start();
			}
		}
		
		Controller controller = new Controller(building);
		controller.run();
	}
}
