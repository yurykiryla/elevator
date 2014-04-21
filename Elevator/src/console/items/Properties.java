package console.items;

/**
 * Properties
 */
public class Properties {
	private static final int BASE_DALAY = 1001;
	private static final int DELAY_MULTIPLIER = 10;
	private int storiesNumber;
	private int elevatorCapacity;
	private int passengersNumber;
	private int animationBoost;
	
	public Properties() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * sets initial parameters
	 * @param storiesNumber - number of floors
	 * @param elevatorCapacity - elevator capacity
	 * @param passengersNumber - number of passengers
	 * @param animationBoost - animation boost
	 */
	public Properties(int storiesNumber, int elevatorCapacity,
			int passengersNumber, int animationBoost) {
		super();
		this.storiesNumber = storiesNumber;
		this.elevatorCapacity = elevatorCapacity;
		this.passengersNumber = passengersNumber;
		this.animationBoost = animationBoost;
	}
	
	public int getStoriesNumber() {
		return storiesNumber;
	}
	
	public void setStoriesNumber(int storiesNumber) {
		this.storiesNumber = storiesNumber;
	}
	
	public int getElevatorCapacity() {
		return elevatorCapacity;
	}
	
	public void setElevatorCapacity(int elevatorCapacity) {
		this.elevatorCapacity = elevatorCapacity;
	}
	
	public int getPassengersNumber() {
		return passengersNumber;
	}
	
	public void setPassengersNumber(int passengersNumber) {
		this.passengersNumber = passengersNumber;
	}
	
	public int getAnimationBoost() {
		return animationBoost;
	}
	
	public void setAnimationBoost(int animationBoost) {
		this.animationBoost = animationBoost;
	}	
	
	/**
	 * returns the time delay
	 */
	public int getDelay(){
		if(animationBoost > 100 || animationBoost == 0){
			return 0;
		}
		return BASE_DALAY - animationBoost * DELAY_MULTIPLIER;
	}
}
