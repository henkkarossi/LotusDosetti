package toiminnallisuus;

import java.util.concurrent.TimeUnit;

import toiminnallisuus.RgbSensor.ColorReading;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		
		PillDispenser pl = new PillDispenser();
		//Firebase fb = new Firebase();
		//RgbSensor sensor = new RgbSensor();
		
		//for (int i = 0; i < 10; i++) {
		//	TimeUnit.SECONDS.sleep(3);
		//	ColorReading r = sensor.getReading();
		//	System.out.println(r);
		//}
		//fb.updateTimeToTake("10:30");
		
		
		///Kommentoi pois jos ei ole demo !!!!!!!!!!!!!!!!!!!!!!!
		//pl.CreateDemo();

		pl.run();
	}
	
	
	

}
