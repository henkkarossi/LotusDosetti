package toiminnallisuus;

import java.util.concurrent.TimeUnit;

import toiminnallisuus.RgbSensor.ColorReading;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		
		PillDispenser pl = new PillDispenser();

		pl.run();
	}
	
	
	

}
