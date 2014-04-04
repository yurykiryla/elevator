package console.beans;

import console.constants.TransportationState;

public class Passenger {
	int id;
	int destinationStory;
	TransportationState transportationState;
	public Passenger() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Passenger(int id, int destinationStory,
			TransportationState transportationState) {
		super();
		this.id = id;
		this.destinationStory = destinationStory;
		this.transportationState = transportationState;
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
}
