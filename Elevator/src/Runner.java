
import ui.UIRunner;
import console.items.Building;
import console.items.Properties;
import console.readers.FilePropertiesReader;
import console.transportation.Controller;

/**
 * Start application
 */
public class Runner {

	public static void main(String[] args) {
		Properties properties = FilePropertiesReader.getProperties();
				
		if(properties.getAnimationBoost() == 0){
			Building building = new Building(properties);
			Controller controller = building.getController();
			controller.run();
		}else{
			UIRunner.run(properties);
		}
	}
}
