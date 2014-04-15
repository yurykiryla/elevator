package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
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

import console.items.Properties;

public class UIRunner {
	private static Properties properties;
	
	public UIRunner() {
		JFrame jFrame = new JFrame("Elevator");
		jFrame.setLayout(new FlowLayout());
		jFrame.setSize(800, 600);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		JScrollPane jPanelLeft = new JScrollPane();
		jPanelLeft.setPreferredSize(new Dimension(560, 555));
		jPanelLeft.setOpaque(true);
		jPanelLeft.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		jFrame.add(jPanelLeft);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		JPanel jPanelRight = new JPanel(gridBagLayout);
		jPanelRight.setPreferredSize(new Dimension(220, 555));
		jPanelRight.setOpaque(true);
		jPanelRight.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		JLabel jLabelStoriesNumber = new JLabel("Stories number", SwingConstants.RIGHT);
		JSpinner jSpinnerStoriesNumber;
		SpinnerNumberModel snmStoriesNumber = new SpinnerNumberModel(properties.getStoriesNumber(), 2, 9999, 1);
		jSpinnerStoriesNumber = new JSpinner(snmStoriesNumber);
		gridBagConstraints.ipadx = 5;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jLabelStoriesNumber, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(jSpinnerStoriesNumber, gridBagConstraints);
		jPanelRight.add(jLabelStoriesNumber);
		jPanelRight.add(jSpinnerStoriesNumber);
		
		
		JLabel jLabelElevatorCapacity = new JLabel("Elevator capacity", SwingConstants.RIGHT);
		SpinnerNumberModel snmElevatorCapacity = new SpinnerNumberModel(properties.getElevatorCapacity(), 1, 9999, 1);
		JSpinner jSpinnerElevatorCapacity = new JSpinner(snmElevatorCapacity);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jLabelElevatorCapacity, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(jSpinnerElevatorCapacity, gridBagConstraints);
		jPanelRight.add(jLabelElevatorCapacity);
		jPanelRight.add(jSpinnerElevatorCapacity);
		
		JLabel jLabelPassengersNumber = new JLabel("Passengers number", SwingConstants.RIGHT);
		SpinnerNumberModel spinnerNumberModelPassengersNumber = new SpinnerNumberModel(properties.getPassengersNumber(), 1, 9999, 1);
		JSpinner jSpinnerPassengersNumber = new JSpinner(spinnerNumberModelPassengersNumber);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jLabelPassengersNumber, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(jSpinnerPassengersNumber, gridBagConstraints);
		jPanelRight.add(jLabelPassengersNumber);
		jPanelRight.add(jSpinnerPassengersNumber);
		
		JSlider jSliderAnimationBoost = new JSlider(0, 100, properties.getAnimationBoost());
		jSliderAnimationBoost.setMajorTickSpacing(20);
		jSliderAnimationBoost.setMinorTickSpacing(5);
		jSliderAnimationBoost.setLabelTable(jSliderAnimationBoost.createStandardLabels(20));
		jSliderAnimationBoost.setPaintTicks(true);
		jSliderAnimationBoost.setPaintLabels(true);
		JLabel jLabelAnimationBoost = new JLabel("Animation Boost");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		gridBagLayout.setConstraints(jSliderAnimationBoost, gridBagConstraints);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		gridBagLayout.setConstraints(jLabelAnimationBoost, gridBagConstraints);
		jPanelRight.add(jSliderAnimationBoost);
		jPanelRight.add(jLabelAnimationBoost);
		
		JButton jButton = new JButton("START");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		gridBagLayout.setConstraints(jButton, gridBagConstraints);
		jPanelRight.add(jButton);
		
		
		JTextArea jTextArea = new JTextArea("Please select parameters and Start");
		jTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		jTextArea.setVisible(true);
		JScrollPane jScrollPaneText = new JScrollPane(jTextArea);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		gridBagLayout.setConstraints(jScrollPaneText, gridBagConstraints);
		jPanelRight.add(jScrollPaneText);
		
		
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
