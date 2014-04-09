package console.containers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import console.items.Passenger;

public class DispatchStoryContainer {
	private final int story;
	private Set<Passenger> passengers;
	
	public DispatchStoryContainer(int story) {
		super();
		this.story = story;
		passengers = new HashSet<>();
	}
	
	public void addPassenger(Passenger passenger){
		passengers.add(passenger);
	}

	public int getStory() {
		return story;
	}
	
	public boolean isEmpty(){
		return passengers.isEmpty();
	}
	
	public Iterator<Passenger> getIterator(){
		return passengers.iterator();
	}
}
