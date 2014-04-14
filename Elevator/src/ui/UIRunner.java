package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import console.items.Building;

public class UIRunner {
	
	public UIRunner() {
		JFrame jFrame = new JFrame("Elevator");
		jFrame.setLayout(new FlowLayout());
		jFrame.setSize(800, 600);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane jPanelLeft = new JScrollPane();
		jPanelLeft.setPreferredSize(new Dimension(560, 555));
		jPanelLeft.setOpaque(true);
		jPanelLeft.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		jFrame.add(jPanelLeft);
		
		JPanel jPanelRight = new JPanel();
		jPanelRight.setPreferredSize(new Dimension(220, 555));
		jPanelRight.setOpaque(true);
		jPanelRight.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		JLabel jLabelStoriesNumber = new JLabel("Stories number", SwingConstants.RIGHT);
		JSpinner jSpinnerStoriesNumber;
		SpinnerNumberModel snmStoriesNumber = new SpinnerNumberModel(5, 2, 9999, 1);
		jSpinnerStoriesNumber = new JSpinner(snmStoriesNumber);
		jPanelRight.add(jLabelStoriesNumber);
		jPanelRight.add(jSpinnerStoriesNumber);
		
		JLabel jLabelElevatorCapacity = new JLabel("Elevator capacity", SwingConstants.RIGHT);
		SpinnerNumberModel snmElevatorCapacity = new SpinnerNumberModel(2, 1, 9999, 1);
		JSpinner jSpinnerElevatorCapacity = new JSpinner(snmElevatorCapacity);
		jPanelRight.add(jLabelElevatorCapacity);
		jPanelRight.add(jSpinnerElevatorCapacity);

		JLabel jLabelPassengersNumber = new JLabel("Passengers number", SwingConstants.RIGHT);
		SpinnerNumberModel spinnerNumberModelPassengersNumber = new SpinnerNumberModel(5, 1, 9999, 1);
		JSpinner jSpinnerPassengersNumber = new JSpinner(spinnerNumberModelPassengersNumber);
		jPanelRight.add(jLabelPassengersNumber);
		jPanelRight.add(jSpinnerPassengersNumber);
		
		JSlider jSliderAnimationBoost = new JSlider(0, 100, 5);
		jSliderAnimationBoost.setMajorTickSpacing(20);
		jSliderAnimationBoost.setMinorTickSpacing(5);
		jSliderAnimationBoost.setLabelTable(jSliderAnimationBoost.createStandardLabels(20));
		jSliderAnimationBoost.setPaintTicks(true);
		jSliderAnimationBoost.setPaintLabels(true);
		JLabel jLabelAnimationBoost = new JLabel("Animation Boost");
		jPanelRight.add(jSliderAnimationBoost);
		jPanelRight.add(jLabelAnimationBoost);
		
		JButton jButton = new JButton("START");
		jPanelRight.add(jButton);
		
		
		jFrame.add(jPanelRight);
		
		jFrame.setVisible(true);
	}

	public static void start(Building building){
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
