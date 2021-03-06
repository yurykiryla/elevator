package console.logging;

import javax.swing.JTextArea;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * intended for messages application
 */
public class MessagesAreaAppender extends WriterAppender {
	private JTextArea messagesArea;
	private static final String LOG_PATTERN = " %m%n";

	public MessagesAreaAppender(JTextArea messagesArea) {
		super();
		this.messagesArea = messagesArea;
		setThreshold(Level.INFO);
		setLayout(new PatternLayout(LOG_PATTERN));
	}

	@Override
	public void append(LoggingEvent event) {
		messagesArea.append(layout.format(event));
		messagesArea.setCaretPosition(messagesArea.getText().length());
	}	
}
