package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import console.items.Building;
import console.items.Elevator;
import console.items.Properties;
import console.items.Storey;
import console.logging.ActionButtonAppender;
import console.logging.ElevatorLogger;
import console.logging.MessagesAreaAppender;

public class UIRunner implements ActionListener{
	private static Properties properties;
	private Building building;
	private static final String BUTTON_START = "START";
	private static final String BUTTON_ABORT = "ABORT";
	private static final String BUTTON_VIEW_LOG = "VIEW LOG";
	private static final String MESSAGE_START = "Please select parameters and Start";
	private JFrame jFrame;
	private JButton actionButton;
	private JTextArea messagesArea;
	private PropertiesPanel propertiesPanel;
	private Map<Storey, PresentationStorey> storeysMap;
	
	public UIRunner() {
		
		jFrame = new JFrame("Elevator");
		jFrame.setLayout(new FlowLayout());
		jFrame.setSize(UIDimensions.WINDOW_SIZE_START);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		JPanel jPanelControls = new JPanel(new FlowLayout());
		jPanelControls.setPreferredSize(UIDimensions.CONTROLS_PANEL_DIMENSION);
		jPanelControls.setOpaque(true);
		jPanelControls.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		Box box = Box.createVerticalBox();
		
		propertiesPanel =  new PropertiesPanel(properties);
		box.add(propertiesPanel);
		
		box.add(Box.createRigidArea(UIDimensions.RIGID_AREA_DIMENSION));
		
		actionButton = new JButton(BUTTON_START);
		actionButton.addActionListener(this);
		JPanel jPanelButton = new JPanel();
		jPanelButton.add(actionButton);
		box.add(jPanelButton);
	
		box.add(Box.createRigidArea(UIDimensions.RIGID_AREA_DIMENSION));
		
		messagesArea = new JTextArea(MESSAGE_START);
		messagesArea.setEditable(false);
		JScrollPane jScrollPaneText = new JScrollPane(messagesArea);
		jScrollPaneText.setPreferredSize(UIDimensions.TEXT_PANEL_SIZE);
		box.add(jScrollPaneText);
		
		jPanelControls.add(box);
		
		jFrame.add(jPanelControls);
		
		jFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()){
			case BUTTON_START:
				properties = propertiesPanel.getProperties();
				building = new Building(properties);
				if(properties.getAnimationBoost() == 0){
					jFrame.setVisible(false);
					jFrame.dispose();
					building.getController().run();
				}else{
					propertiesPanel.disableProperties();
					actionButton.setText(BUTTON_ABORT);
					jFrame.setSize(UIDimensions.WINDOW_FULL_SIZE);
					
					storeysMap = new HashMap<Storey, PresentationStorey>(properties.getStoriesNumber());
					JPanel presentationArea = new JPanel();
					presentationArea.setLayout(new BoxLayout(presentationArea, BoxLayout.Y_AXIS));
					PresentationStorey.setPassengersViewNumber(properties.getElevatorCapacity());
					
					PresentationStorey presentationStorey;
					List<Storey> storeys = building.getStoreys();
					Elevator elevator = building.getElevator();
					Storey storey;
					for(int i = storeys.size() - 1; i > -1; i--){
						storey = storeys.get(i);
						presentationStorey = 
								new PresentationStorey(storey.getDispatchStoryContainer(), 
										storey.getArrivalStoryContainer());
						presentationArea.add(presentationStorey);
						storeysMap.put(storey, presentationStorey);
					}
					presentationStorey = storeysMap.get(storeys.get(elevator.getCurrentStory()));
					presentationStorey.setElevatorPassengers(elevator.getElevatorContainer());
					presentationArea.setVisible(true);
					presentationArea.setPreferredSize(UIDimensions.BUILDING_PANEL_DIMENSION);
					presentationArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					presentationArea.setBackground(Color.WHITE);
					jFrame.add(presentationArea);
					Timer timer = new Timer(properties.getDelay(), new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							for(Storey storey : building.getStoreys()){
								storeysMap.get(storey).update(storey.getDispatchStoryContainer(), storey.getArrivalStoryContainer());
								Elevator elevator = building.getElevator();
								storeysMap.get(building.getStoreys().get(elevator.getCurrentStory())).setElevatorPassengers(elevator.getElevatorContainer());
								jFrame.repaint();
							}
						}
					});
					timer.start();
					
					ElevatorLogger.LOGGER.addAppender(new MessagesAreaAppender(messagesArea));
					ElevatorLogger.LOGGER.addAppender(new ActionButtonAppender(actionButton, BUTTON_VIEW_LOG));
					new Thread(building.getThreadGroup(), building.getController()).start();
										
				}
				break;
				
			case BUTTON_ABORT:
				building.getThreadGroup().interrupt();
				building.getController().abortTransportation();
				actionButton.setText(BUTTON_VIEW_LOG);
				break;
			case BUTTON_VIEW_LOG:
				new ViewLogFile();
				break;
		}
	}
	
	public static void run(Properties properties){
		UIRunner.properties = properties;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new UIRunner();
			}
		});
	}
}
