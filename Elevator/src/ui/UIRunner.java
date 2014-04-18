package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import console.items.Building;
import console.items.Properties;
import console.transportation.Controller;

public class UIRunner implements ActionListener{
	private static Properties properties;
	private Building building;
	private static final String BUTTON_START = "START";
	private static final String BUTTON_ABORT = "ABORT";
	private static final String BUTTON_VIEW_LOG = "VIEW LOG";
	private static final String MESSAGE_ABORT = "Transportation is aborted";
	private static final String MESSAGE_PROGRESS = "Transportation in progress";
	private static final String MESSAGE_START = "Please select parameters and Start";
	private JFrame jFrame;
	private JButton jButton;
	private JPanel jPanelBuilding;
	private JTextArea jTextArea;
	private ParametersPanel parametersPanel;
	
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
		
		parametersPanel =  new ParametersPanel(properties);
		box.add(parametersPanel);
		
		box.add(Box.createRigidArea(UIDimensions.RIGID_AREA_DIMENSION));
		
		jButton = new JButton(BUTTON_START);
		jButton.addActionListener(this);
		JPanel jPanelButton = new JPanel();
		jPanelButton.add(jButton);
		box.add(jPanelButton);
	
		box.add(Box.createRigidArea(UIDimensions.RIGID_AREA_DIMENSION));
		
		jTextArea = new JTextArea(MESSAGE_START);
		jTextArea.setEditable(false);
		JScrollPane jScrollPaneText = new JScrollPane(jTextArea);
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
				properties = parametersPanel.getProperties();
				building = new Building(properties);
				if(properties.getAnimationBoost() == 0){
					Controller controller = building.getController();
					jFrame.setVisible(false);
					jFrame.dispose();
					controller.run();
				}else{
					parametersPanel.disableProperties();
					jButton.setText(BUTTON_ABORT);
					jTextArea.setText(MESSAGE_PROGRESS);
					jFrame.setSize(UIDimensions.WINDOW_FULL_SIZE);
					jPanelBuilding = new BuildingPanel();
					jPanelBuilding.setPreferredSize(UIDimensions.BUILDING_PANEL_DIMENSION);
					jPanelBuilding.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					jPanelBuilding.setBackground(Color.WHITE);
					jFrame.add(jPanelBuilding);
				}
				break;
			case BUTTON_ABORT:
				building.getThreadGroup().interrupt();
				jButton.setText(BUTTON_VIEW_LOG);
				jTextArea.setText(MESSAGE_ABORT);
				break;
			case BUTTON_VIEW_LOG:
				break;
		}
	}
	
	private class BuildingPanel extends JPanel{
		private static final long serialVersionUID = -1962069883230791714L;
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			int storyNumber = properties.getStoriesNumber();
			int x, y, h, w;
			
			Graphics2D graphics2d = (Graphics2D) g;
			Font font = new Font(getFont().getName(), Font.BOLD, 20);
			graphics2d.setFont(font);
			graphics2d.setColor(Color.BLACK);
			
			for(int i = 1; i <= storyNumber; i++){
				x = 0;
				y = UIDimensions.ELEVATOR_HEIGHT * i;
				String storeyNumber = String.valueOf(storyNumber - i + 1);
				graphics2d.drawString(storeyNumber, x, y);
				h = 3;
				w = 100;
				graphics2d.fillRect(x, y, w, h);
				x = getWidth() - w;
				graphics2d.fillRect(x, y, w, h);
			}
		}
		
	}

	public static void start(Properties properties){
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
