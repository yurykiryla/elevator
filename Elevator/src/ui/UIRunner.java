package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import console.items.Properties;

public class UIRunner {
	private static Properties properties;
	
	public UIRunner() {
		JFrame jFrame = new JFrame("Elevator");
		jFrame.setLayout(new FlowLayout());
		jFrame.setSize(800, 600);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		JPanel jPanelLeft = new JPanel();
		jPanelLeft.setPreferredSize(new Dimension(560, 555));
		jPanelLeft.setOpaque(true);
		jPanelLeft.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		jFrame.add(jPanelLeft);
		
		JPanel jPanelRight = new JPanel(new FlowLayout());
		jPanelRight.setPreferredSize(new Dimension(220, 555));
		jPanelRight.setOpaque(true);
		jPanelRight.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.ipadx = 5;
		gridBagConstraints.weightx = 1.0;
		JPanel jPanelParameters = new JPanel(gridBagLayout);
		jPanelParameters.setBorder(BorderFactory.createEtchedBorder());
		
		JLabel jLabelParameters = new JLabel("Parameters");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		gridBagLayout.setConstraints(jLabelParameters, gridBagConstraints);
		jPanelParameters.add(jLabelParameters);
		
		JLabel jLabelStoriesNumber = new JLabel("Stories number", SwingConstants.RIGHT);
		JSpinner jSpinnerStoriesNumber;
		SpinnerNumberModel spinnerNumberModelStoriesNumber = new SpinnerNumberModel(properties.getStoriesNumber(), 2, 9999, 1);
		jSpinnerStoriesNumber = new JSpinner(spinnerNumberModelStoriesNumber);
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jLabelStoriesNumber, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(jSpinnerStoriesNumber, gridBagConstraints);
		jPanelParameters.add(jLabelStoriesNumber);
		jPanelParameters.add(jSpinnerStoriesNumber);
		
		JLabel jLabelElevatorCapacity = new JLabel("Elevator capacity", SwingConstants.RIGHT);
		SpinnerNumberModel spinnerNumberModelElevatorCapacity = new SpinnerNumberModel(properties.getElevatorCapacity(), 1, 9999, 1);
		JSpinner jSpinnerElevatorCapacity = new JSpinner(spinnerNumberModelElevatorCapacity);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jLabelElevatorCapacity, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(jSpinnerElevatorCapacity, gridBagConstraints);
		jPanelParameters.add(jLabelElevatorCapacity);
		jPanelParameters.add(jSpinnerElevatorCapacity);
		
		JLabel jLabelPassengersNumber = new JLabel("Passengers number", SwingConstants.RIGHT);
		SpinnerNumberModel spinnerNumberModelPassengersNumber = new SpinnerNumberModel(properties.getPassengersNumber(), 1, 9999, 1);
		JSpinner jSpinnerPassengersNumber = new JSpinner(spinnerNumberModelPassengersNumber);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jLabelPassengersNumber, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(jSpinnerPassengersNumber, gridBagConstraints);
		jPanelParameters.add(jLabelPassengersNumber);
		jPanelParameters.add(jSpinnerPassengersNumber);
		
		final JSlider jSliderAnimationBoost = new JSlider(0, 100, properties.getAnimationBoost());
		jSliderAnimationBoost.setMajorTickSpacing(20);
		jSliderAnimationBoost.setMinorTickSpacing(5);
		jSliderAnimationBoost.setLabelTable(jSliderAnimationBoost.createStandardLabels(20));
		jSliderAnimationBoost.setPaintTicks(true);
		jSliderAnimationBoost.setPaintLabels(true);
		final JLabel jLabelAnimationBoost = new JLabel("Animation Boost (" + jSliderAnimationBoost.getValue() + ")");
		jSliderAnimationBoost.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				jLabelAnimationBoost.setText("Animation Boost (" + jSliderAnimationBoost.getValue() + ")");
			}
		});
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		gridBagLayout.setConstraints(jSliderAnimationBoost, gridBagConstraints);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.insets = new Insets(0, 0, 15, 0);
		gridBagLayout.setConstraints(jLabelAnimationBoost, gridBagConstraints);
		jPanelParameters.add(jSliderAnimationBoost);
		jPanelParameters.add(jLabelAnimationBoost);
		
		Box box = Box.createVerticalBox();
		box.add(jPanelParameters);
		
		box.add(Box.createRigidArea(new Dimension(100, 30)));
		
		JButton jButton = new JButton("START");
		JPanel jPanelButton = new JPanel();
		jPanelButton.add(jButton);
		box.add(jPanelButton);
	
		box.add(Box.createRigidArea(new Dimension(100, 30)));
		
		JTextArea jTextArea = new JTextArea("Please select parameters and Start");
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setEditable(false);
		JScrollPane jScrollPaneText = new JScrollPane(jTextArea);
		jScrollPaneText.setPreferredSize(new Dimension(200, 220));
		box.add(jScrollPaneText);
		
		jPanelRight.add(box);
		
		jFrame.add(jPanelRight);
		
		jFrame.setVisible(true);
	}
	
	

	public static void start(Properties properties){
		UIRunner.properties = properties;
		System.out.println("UI realisation");
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new UIRunner();
			}
		});
	}
}
