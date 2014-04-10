package console.items;

import java.util.List;

public class Building {
	private Elevator elevator;
	private List<Storey> storeys;
	
	public Building(Elevator elevator, List<Storey> storeys) {
		super();
		this.elevator = elevator;
		this.storeys = storeys;
	}
	
	
}
