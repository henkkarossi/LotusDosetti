package toiminnallisuus;

public class UseSensor {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		RgbSensor sensor = new RgbSensor();
		
		for (int i = 0; i < 10; i++) {
			System.out.println(sensor.getReading());
			Thread.sleep(500);
		}
		
	}

}
