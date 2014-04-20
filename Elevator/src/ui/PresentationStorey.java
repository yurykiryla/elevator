package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import console.items.Passenger;

public class PresentationStorey extends JPanel {
	private static final long serialVersionUID = -8503820548929834090L;
	private static final int PASSENGERS_VIEW_MAX = 10;
	private final static int SIZE_Y = 40;
	private final static int MARGIN_Y = 5;
	private final static int MARGIN_X = 5;
	
	private static int passengersViewNumber = 5;

	public static void setPassengersViewNumber(int capacity) {
		if(capacity < PASSENGERS_VIEW_MAX){
			passengersViewNumber = capacity;
		}else{
			passengersViewNumber = PASSENGERS_VIEW_MAX;
		}
	}
	
	private class PassengerContainer extends JComponent{
		private static final long serialVersionUID = -9081531667264153000L;
		protected Set<Passenger> passengers;

		public PassengerContainer(Set<Passenger> passengers) {
			super();
			this.passengers = passengers;
			setMaximumSize(new Dimension(Integer.MAX_VALUE, SIZE_Y - 1));
			setMinimumSize(new Dimension(SIZE_Y - MARGIN_X, SIZE_Y - MARGIN_Y));
			setPreferredSize(getMinimumSize());
			if(passengers != null){
				this.setToolTipText("Size = " + passengers.size());
			}
		}
		
		public void update(Set<Passenger> passengers){
			this.passengers = passengers;
			if(passengers != null){
				this.setToolTipText("Size = " + passengers.size());
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			Graphics2D graphics2d = (Graphics2D) g;
			graphics2d.setColor(Color.blue);
			drawContainer(graphics2d);
			
			int x = 2;
			synchronized (this) {
				if(passengers != null){
					for(Passenger passenger : passengers){
						x += drawPassenger(graphics2d, passenger, x);
						if(getSize().getWidth() < x){
							break;
						}
						x += MARGIN_X;
					}
				}
			}
		}
		
		protected void drawContainer (Graphics2D graphics2d){
			graphics2d.setColor(Color.gray);
			graphics2d.draw(new Rectangle2D.Double(0, 0, getSize().getWidth() - 1, getSize().getHeight() - 1));
		}
		
		private int drawPassenger(Graphics2D graphics2d, Passenger passenger, int x){
			String id = String.valueOf(passenger.getId());
			
			Rectangle2D stringBounds = graphics2d.getFontMetrics().getStringBounds(id, graphics2d);
			int width = (int) Math.round(stringBounds.getWidth());
			int height = (int) Math.round(stringBounds.getHeight());
			
			graphics2d.setColor(Color.red);
			graphics2d.drawString(id, x + MARGIN_X, SIZE_Y - 3 * MARGIN_Y);
			graphics2d.draw(new Rectangle2D.Double(x, SIZE_Y - 3 * MARGIN_Y - height,
					width + 2 * MARGIN_X, height + 1 * MARGIN_Y));
			return width + 2 * MARGIN_X;
		}
	}
	
	private class Elevator extends PassengerContainer{
		private static final long serialVersionUID = 8388064106781936255L;
		private final int SIZE_X = 35;
		
		public Elevator(Set<Passenger> passengers) {
			super(passengers);
			// TODO Auto-generated constructor stub
			setMaximumSize(new Dimension(SIZE_X * passengersViewNumber, SIZE_Y));
			setMinimumSize(new Dimension(SIZE_X, SIZE_Y));
			setPreferredSize(getMaximumSize());
			setSize(getMinimumSize());
		}

		@Override
		protected void drawContainer(Graphics2D graphics2d) {
			if(super.passengers != null){
				graphics2d.setColor(Color.GREEN);
				graphics2d.draw(new Rectangle2D.Double(0, 0, getSize().getWidth() - 1, 
						getSize().getHeight() - 1));
			}
		}
	}
	
	private PassengerContainer dispath;
	private PassengerContainer arrival;
	private PassengerContainer elevator;
	
	public PresentationStorey(Set<Passenger> dispathStory, Set<Passenger> arrivalStory) {
		super();

		setBackground(Color.white);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, SIZE_Y));

		dispath = new PassengerContainer(dispathStory);
		arrival = new PassengerContainer(arrivalStory);
		elevator = new Elevator(null);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(arrival);
		add(elevator);
		add(dispath);
		setVisible(true);
	}
	
	public void update(Set<Passenger> dispathStory, Set<Passenger> arrivalStory) {
		dispath.update(dispathStory);
		arrival.update(arrivalStory);
		Set<Passenger> ps = null;
		elevator.update(ps);
	}

	public void setElevatorPassengers(Set<Passenger> elevatorStory) {
		elevator.update(elevatorStory);
	}
}
