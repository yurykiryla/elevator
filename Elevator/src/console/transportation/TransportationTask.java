package console.transportation;

import java.util.Set;

import console.constants.Directions;
import console.constants.TransportationState;
import console.items.Building;
import console.items.Passenger;

/**
 * Passenger Transportation task
 */
public class TransportationTask extends Thread {
	private final Passenger passenger;
	private final Building building;
	private final Controller controller;
	private Set<Passenger> elevatorContainer;
	private Set<Passenger> dispatchStoryContainer;
	private Directions direction;
	

	public TransportationTask(Passenger passenger, Building building,
			Controller controller) {
		super();
		this.passenger = passenger;
		passenger.setTransportationTask(this);
		this.building = building;
		this.controller = controller;
		elevatorContainer = building.getElevator().getElevatorContainer();
		dispatchStoryContainer = building.getStoreys().get(passenger.getDispatchStory()).getDispatchStoryContainer();
		if(passenger.getDestinationStory() >  passenger.getDispatchStory()){
			direction = Directions.UP;
		}else{
			direction = Directions.DOWN;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		try{
			//start transportation
			synchronized (building) {
				passenger.setTransportationState(TransportationState.IN_PROGRESS);
				building.notify();
			}
			
			// boading passengers to elevator
			synchronized (dispatchStoryContainer) {
				dispatchStoryContainer.wait();
				while(!controller.boadingPassenger(passenger)){
					dispatchStoryContainer.wait();
				}
				dispatchStoryContainer.notifyAll();
			}
			
			// deboading passengers from elevator
			synchronized (elevatorContainer) {
				elevatorContainer.wait();
				while(!controller.deboadingPassenger(passenger)){
					elevatorContainer.wait();
				}
				elevatorContainer.notifyAll();
			}
	

		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		passenger.setTransportationState(TransportationState.COMPLETED);
	}

	public Directions getDirection() {
		return direction;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		super.interrupt();
		passenger.setTransportationState(TransportationState.ABORTED);
	}	
}
