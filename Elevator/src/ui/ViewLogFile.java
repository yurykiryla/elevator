package ui;

import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Window for view loging file
 * @author Yury
 *
 */
public class ViewLogFile extends JFrame {
	private static final long serialVersionUID = 3157349286705659734L;
	
	private static final String FILENAME = "elevator.log";
	private static final String TITLE = "View Log File";

	public ViewLogFile() throws HeadlessException {
		super();
		setTitle(TITLE);
		setSize(UIDimensions.VIEW_LOG_DIMENSION);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JTextArea jTextArea = new JTextArea();
		FileReader fileReader = null;;
		try{
			fileReader = new FileReader(FILENAME);
			jTextArea.read(fileReader, FILENAME);
			JScrollPane jScrollPane = new JScrollPane(jTextArea);
			add(jScrollPane);
			setVisible(true);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
