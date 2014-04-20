package console.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ElevatorLogger {
	private static final String FILENAME_LOG_PROPERTIES = "src/source/log4j.properties";
	
	static{
		PropertyConfigurator.configure(FILENAME_LOG_PROPERTIES);
	}
	
	public static final Logger LOGGER = Logger.getRootLogger();
}
