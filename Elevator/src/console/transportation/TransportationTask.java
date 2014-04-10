package console.transportation;

import java.util.Set;

import console.constants.TransportationState;
import console.items.Building;
import console.items.Passenger;
import console.items.Storey;

public class TransportationTask implements Runnable {
	private final Passenger passenger;
	private final Building building;
	private boolean isElevator = false;
	
	public TransportationTask(Passenger passenger, Building building) {
		super();
		this.passenger = passenger;
		this.building = building;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		passenger.setTransportationState(TransportationState.IN_PROGRESS);
		
		//testing
		System.out.println(passenger);
		
		Set<Passenger> dispatchStoryContainer = building.getStoreys().get(passenger.getDispatchStory()).getDispatchStoryContainer();
		
		try{
			synchronized (dispatchStoryContainer) {
				if(!isElevator){
					wait();
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
