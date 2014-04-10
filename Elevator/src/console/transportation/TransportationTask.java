package console.transportation;

import console.constants.TransportationState;
import console.items.Building;
import console.items.Passenger;

public class TransportationTask implements Runnable {
	private final Passenger passenger;
	private final Building building;
	
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
	}

}
