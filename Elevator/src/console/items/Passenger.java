package console.items;

import console.constants.TransportationState;
import console.transportation.TransportationTask;

public class Passenger {
	private final int id;
	private final int dispatchStory;
	private final int destinationStory;
	private TransportationState transportationState;

	public Passenger(int id, int dispatchStory, int destinationStory) {
		super();
		this.id = id;
		this.dispatchStory = dispatchStory;
		this.destinationStory = destinationStory;
		transportationState = TransportationState.NOT_STARTED;
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

	public int getDispatchStory() {
		return dispatchStory;
	}

	public int getDestinationStory() {
		return destinationStory;
	}

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", dispatchStory=" + dispatchStory
				+ ", destinationStory=" + destinationStory
				+ ", transportationState=" + transportationState + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (id != other.id)
			return false;
		return true;
	}
}
