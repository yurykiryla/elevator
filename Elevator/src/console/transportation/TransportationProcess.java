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
		
		Controller controller = new Controller(building);
		
		List<Storey> storeys = building.getStoreys();
		for(Storey storey : storeys){
			Set<Passenger> dispatchStoryContainer = storey.getDispatchStoryContainer();
			for(Passenger passenger : dispatchStoryContainer){
				try{
					synchronized (controller) {
						Thread transportationTask = new Thread(new TransportationTask(passenger, building, controller));
						transportationTask.start();
						controller.wait();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		
		controller.run();
	}
}
