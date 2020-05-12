package toiminnallisuus;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.i2c.I2CFactory;

import toiminnallisuus.RgbSensor.ColorReading;

public class PillDispenser {

	public static boolean running = false;
	
	public enum State{ idle, takeMedicine, notTaken}
	
	static State state = State.idle;
	
	static ColorReading colorTreshold;
	
	public Led led = new Led();
	
	RgbSensor sensor;
	Motor motor = new Motor();
	
	public void run() throws Exception {
		running = true;
		sensor = new RgbSensor();
		colorTreshold = getColorReading(2000, 2000,  2000, 5000);

		while(running) 
		{
			switch(state) 
			{
			
			case idle:
				Idle();
				break;
				
			case takeMedicine:
				TakeMedicine();
				break;
				
			case notTaken:
				NotTaken();
				break;
				
			default:
				break;
			
			}
		}

	}
	
	private static ColorReading getColorReading(int i, int j, int k, int l) throws Exception {
		RgbSensor sensor = new RgbSensor();
		ColorReading cr = sensor.getManualReading(i, j, k, l);
		return cr;
	}

	public static void Idle() throws InterruptedException 
	{
		
		TimeUnit.SECONDS.sleep(3);
		
		System.out.println("idle running");
		boolean idle = true;

		while(idle) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime timeToTake = getDateTime();
			
			if (now.isAfter(timeToTake)) {
				idle = false;
				state = State.takeMedicine;
			}

		}
	}
	
	public static LocalDateTime getDateTime() {
		Firebase fb = new Firebase();
		String time = null;
		LocalDate date = LocalDate.now();
		
		try {
			Data data = fb.getData();
			time = data.getTimeToTake();

		} catch (Exception e) {

			e.printStackTrace();
		}
		String[] parts = time.split(":");
		
		int hour = Integer.valueOf(parts[0]);
		int minute = Integer.valueOf(parts[1]);
		
		LocalDateTime timeToTake = date.atTime(hour, minute);
		
		return timeToTake;
	}
	
	public void TakeMedicine() throws Exception 
	{
		//TODO tee metodi joka siirtÃ¤Ã¤ slotin oikeaan kohtaan muistiinpanoja tÃ¤hÃ¤n liittyen lÃ¶ytyy planneristÃ¤
		
		//TODO tee metodi ja/tai luokka sensorille jota tÃ¤Ã¤ltÃ¤ kutsumalla selvittÃ¤Ã¤ 
		
		
		
		Firebase fb = new Firebase(); 
			
		led.setHigh();
		
		System.out.print("step");
		motor.TakeStep(1);
		
		boolean medicineTaken = false;
		
		for(int timer = 0; timer < 200 && medicineTaken == false;) 
		{
			//TODO 
			//Kutsu sensoria jos palauttaa 
			//false jatkaa koska lääkettä ei ole vielä otettu
			//true lopettaa loopin koska lääke on otettu ja palauttaa state idleen
			System.out.println(timer);
			TimeUnit.SECONDS.sleep(3);
			ColorReading color = sensor.getReading();
			System.out.println(color);
			
			if(color.getRed() < colorTreshold.getRed() && color.getGreen() < colorTreshold.getGreen() && color.getBlue() < colorTreshold.getBlue())
			{
				//jos luukku on tyhja eli arvot ovat matalemmat kuin treshold merkkaa luukku tyhjaksi ja palaa idleen
				
				fb.updateTime();	
				fb.close();
				led.setLow();
				medicineTaken = true;
				
			}
			else
			{
				// luukku ei ole tyhjä eli arvot ovat isompia kuin treshold toista tarkastamistasecunnin päästä uudestaan
				TimeUnit.SECONDS.sleep(1);
				timer++;
				
			}
			
			//jos loopin loputtua lääkettä ei ole vieläkään otettu mene not Taken

		}
		if (medicineTaken) {
			state = State.idle;
		} else {
			state = State.notTaken;
		}
	}
	
	public void NotTaken() throws IOException 
	{
		Firebase fb = new Firebase();
		fb.updateStatus(false);
		fb.close();	
		led.setLow();
		System.out.println("Not taken");
		state = State.idle;
	}



}
