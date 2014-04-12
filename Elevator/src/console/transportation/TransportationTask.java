package console.transportation;

import java.util.Set;

import console.constants.Directions;
import console.constants.TransportationState;
import console.items.Building;
import console.items.Passenger;
import console.items.Storey;

public class TransportationTask implements Runnable {
	private final Passenger passenger;
	private final Building building;
	private Controller controller;
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
			synchronized (dispatchStoryContainer) {
				synchronized (controller) {
					passenger.setTransportationState(TransportationState.IN_PROGRESS);
					//testing
					System.out.println(passenger);
					controller.notify();
				}
				dispatchStoryContainer.wait();
			}
	
			while(!controller.boadingPassenger(this)){
				synchronized (dispatchStoryContainer) {
					synchronized (controller) {
						if(elevatorContainer.size() == controller.getElevatorCapacity()){
							controller.notify();
						}
					}
					dispatchStoryContainer.wait();
				}
			}
			
			synchronized (elevatorContainer) {
				synchronized (controller) {
					if(elevatorContainer.size() == controller.getElevatorCapacity()){
						controller.notify();
					}
				}
				elevatorContainer.wait();
			}
			while(!controller.deboadingPassenger(passenger)){
				synchronized (elevatorContainer) {
					synchronized (controller) {
						if(elevatorContainer.size() == controller.getElevatorCapacity()){
							controller.notify();
						}
					}
					elevatorContainer.wait();
				}
			}
			synchronized (controller) {
				if(elevatorContainer.size() == controller.getElevatorCapacity()){
					controller.notify();
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		passenger.setTransportationState(TransportationState.COMPLETED);
		System.out.println(passenger);
	}



	public Directions getDirection() {
		return direction;
	}



	public Passenger getPassenger() {
		return passenger;
	}
	
	
}
