package console.factories;

import console.implementations.FilePropertiesReaderImpl;
import console.interfaces.IPropertiesReader;

public class PropertiesFactory {
	public static IPropertiesReader getClassFromFactory(){
		return new FilePropertiesReaderImpl();
	}
}
