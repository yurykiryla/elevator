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
	private Map<Integer, PresentationStorey> storeysMap;
	
	public UIRunner() {
		jFrame = new JFrame("ElevatorContainer");
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

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Thread controllerThread = null;
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
					
					JPanel presentation = new JPanel();
					presentation.setLayout(new BoxLayout(presentation, BoxLayout.Y_AXIS));
					PresentationStorey.setPassengersViewNumber(properties.getElevatorCapacity());
					
					PresentationStorey presentationStorey;
					List<Storey> storeys = building.getStoreys();
					storeysMap = new HashMap<Integer, PresentationStorey>(properties.getStoriesNumber());
					Elevator elevator = building.getElevator();
					for(int i = storeys.size() - 1; i > -1; i--){
						Storey storey = storeys.get(i);
						presentationStorey = 
								new PresentationStorey(i, storey.getDispatchStoryContainer(), 
										storey.getArrivalStoryContainer());
						presentation.add(presentationStorey);
						storeysMap.put(i, presentationStorey);
					}
					presentationStorey = storeysMap.get(elevator.getCurrentStory());
					presentationStorey.setElevatorPassengers(elevator.getElevatorContainer());
					presentation.setVisible(true);
					JScrollPane presentationArea = new JScrollPane(presentation);
					presentationArea.setPreferredSize(UIDimensions.BUILDING_PANEL_DIMENSION);
					presentationArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					presentationArea.setBackground(Color.WHITE);
					jFrame.add(presentationArea);
					Timer timer = new Timer(properties.getDelay(), new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							for(Storey storey : building.getStoreys()){
								storeysMap.get(storey.getStoreyNumber()).update(storey.getDispatchStoryContainer(), storey.getArrivalStoryContainer());
								Elevator elevator = building.getElevator();
								storeysMap.get(elevator.getCurrentStory()).setElevatorPassengers(elevator.getElevatorContainer());
								jFrame.repaint();
							}
						}
					});
					timer.start();
					
					ElevatorLogger.LOGGER.addAppender(new MessagesAreaAppender(messagesArea));
					ElevatorLogger.LOGGER.addAppender(new ActionButtonAppender(actionButton, BUTTON_VIEW_LOG));
					controllerThread = new Thread(building.getController());
					controllerThread.start();
				}
				break;
				
			case BUTTON_ABORT:
				if(controllerThread != null){
					controllerThread.interrupt();
				}
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
