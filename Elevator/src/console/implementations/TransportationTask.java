package console.implementations;

import console.beans.Passenger;

public class TransportationTask implements Runnable {
	private Passenger passenger;
	
	public TransportationTask(Passenger passenger) {
		this.passenger = passenger;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
