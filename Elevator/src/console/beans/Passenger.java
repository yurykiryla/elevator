package console.beans;

import console.constants.TransportationState;
import console.implementations.TransportationTask;

public class Passenger {
	private final int id;
	private final int destinationStory;
	private final TransportationTask transportationTask;
	private TransportationState transportationState;
	
	public Passenger(int id, int destinationStory) {
		super();
		this.id = id;
		this.destinationStory = destinationStory;
		this.transportationState = TransportationState.NOT_STARTED;
		transportationTask = new TransportationTask(this);
	}
	
	public TransportationState getTransportationState() {
		return transportationState;
	}
	
	public void setTransportationState(TransportationState transportationState) {
		this.transportationState = transportationState;
	}
	
	public int getId() {
		return id;
	}
	
	public int getDestinationStory() {
		return destinationStory;
	}
	
	@Override
	public String toString() {
		return id + ";" + destinationStory + ";" + transportationState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destinationStory;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passenger other = (Passenger) obj;
		if (destinationStory != other.destinationStory)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void transportation(){
		Thread transportationThread = new Thread(transportationTask);
		transportationThread.start();
	}
	
}
