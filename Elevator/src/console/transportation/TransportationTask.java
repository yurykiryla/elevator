package console.transportation;

import console.constants.TransportationState;
import console.items.Passenger;

public class TransportationTask implements Runnable {
	private Passenger passenger;
	
	
	public TransportationTask(Passenger passenger) {
		this.passenger = passenger;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		passenger.setTransportationState(TransportationState.IN_PROGRESS);
		
		//testing
		System.out.println(passenger);
	}

}
