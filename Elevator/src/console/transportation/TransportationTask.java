package console.transportation;

import java.util.Set;

import console.constants.TransportationState;
import console.items.Building;
import console.items.Passenger;
import console.items.Storey;

public class TransportationTask implements Runnable {
	private final Passenger passenger;
	private final Building building;
	private Controller controller;

	public TransportationTask(Passenger passenger, Building building,
			Controller controller) {
		super();
		this.passenger = passenger;
		this.building = building;
		this.controller = controller;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (controller) {
			passenger.setTransportationState(TransportationState.IN_PROGRESS);
			
			//testing
			System.out.println(passenger);

			controller.notifyAll();
		}
		
		
	}

}
