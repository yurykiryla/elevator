package console.constants;

/**
 * Passenger container names
 */
public enum Containers {
	DISPATCH_STORY_CONTAINER("dispatchStoryContainer"),
	ELEVATOR_CONTAINER("elevatorContainer"),
	ARRIVAL_STORY_CONTAINER("arrivalStoryContainer");
	
	private String containerName;
	
	private Containers(String containerName){
		this.containerName = containerName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return containerName;
	}
	
	
}
