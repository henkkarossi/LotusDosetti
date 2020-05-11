package toiminnallisuus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import toiminnallisuus.RgbSensor.ColorReading;

public class PillDispenser {

	public static boolean running = false;
	
	public enum State{ idle, takeMedicine, notTaken, refill}
	
	static State state = State.idle;
	
	static String patientName;
	
	public static Slot[] slots;
	
	//index
	public static int currentSlot;
	
	static ColorReading colorTreshold;
	
	public static void main(String[] args) throws Exception {
		
		int ontop = 30;
		
		colorTreshold = ColorReading(386 + ontop, 178 + ontop, 220 + ontop, 762 + ontop);

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
				
			case refill:
				Refill();
				break;
				
			default:
				break;
			
			}
		}

	}
	
	private static ColorReading ColorReading(int i, int j, int k, int l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void Idle() throws InterruptedException 
	{
		TimeUnit.SECONDS.sleep(1);
		
		System.out.println("idle running");

		List<Slot> found = CheckSlots(0.3);
		
		if(found.size() > 0) 
		{
			currentSlot = found.get(0).getId();
			
			state = State.takeMedicine;
		}
	}
	
	public static void TakeMedicine() throws Exception 
	{
		//TODO tee metodi joka siirtÃ¤Ã¤ slotin oikeaan kohtaan muistiinpanoja tÃ¤hÃ¤n liittyen lÃ¶ytyy planneristÃ¤
		
		//TODO tee metodi ja/tai luokka sensorille jota tÃ¤Ã¤ltÃ¤ kutsumalla selvittÃ¤Ã¤ 
		
		Motor motor = new Motor();
		
		System.out.print("step");
		motor.TakeStep(1);
		
		boolean medicineTaken = false;
		
		for(int timer = 0; timer < 100 && !medicineTaken;) 
		{
			//TODO 
			//Kutsu sensoria jos palauttaa 
			//false jatkaa koska lääkettä ei ole vielä otettu
			//true lopettaa loopin koska lääke on otettu ja palauttaa state idleen
			
			RgbSensor sensor = new RgbSensor();
			
			ColorReading color = sensor.getReading();
			
			
			if(color.getRed() < colorTreshold.getRed() && color.getGreen() < colorTreshold.getGreen() && color.getBlue() < colorTreshold.getBlue())
			{
				//jos luukku on tyhja eli arvot ovat matalemmat kuin treshold merkkaa luukku tyhjaksi ja palaa idleen
				slots[currentSlot].setState(false);
				medicineTaken = true;
				state = State.idle;
			}
			else
			{
				// luukku ei ole tyhjä eli arvot ovat isompia kuin treshold toista tarkastamistasecunnin päästä uudestaan
				TimeUnit.SECONDS.sleep(1);
				timer++;
				
			}
			
			//jos loopin loputtua lääkettä ei ole vieläkään otettu mene not Taken
			state = State.notTaken;
		}
		
	}
	
	public static void NotTaken() 
	{
		//TODO lähetä firebaseen viesti että lääke on jäänyt ottamatta
	}
	
	public static void Refill() 
	{
		//TODO moodi jonka aikana kannen voi avata ja lokerot voi tÃ¤yttÃ¤Ã¤
		
		//TODO metedi joka kÃ¤y kaikki lokerot lÃ¤pi
		//Jos lokero johon on merkitty ettÃ¤ pitÃ¤isi olla lÃ¤Ã¤kkeitÃ¤ ja timeToTake
		//Jos sensori sitten nÃ¤kee ettÃ¤ lokerossa on lÃ¤Ã¤ke se merkitsee lokeron tilan true ja false jos ei nÃ¤e
	}
	
	public static List<Slot> CheckSlots(double d)
	{
		//TODO katso onko missÃ¤Ã¤n lokerolistan lokerossa otto aika = nyt + ja - annettu aika esim 5 min vÃ¤hemmÃ¤n tai enemmÃ¤n
		
		List<Slot> found = new ArrayList<Slot>();
		LocalDateTime now = LocalDateTime.now(); 
		
		for(Slot slot:slots) 
		{
			if(slot.getTimeToTake().getYear() == now.getYear())
				if(slot.getTimeToTake().getDayOfYear() == now.getDayOfYear())
					if(slot.getTimeToTake().getHour() == now.getHour())
						if(slot.getTimeToTake().getMinute() < now.getMinute() + d && slot.getTimeToTake().getMinute() > now.getMinute() - d)
							found.add(slot);
			
		}
		
		//Palauttaa listan lokeroista joiden aika ottaa on nyt
		//YleensÃ¤ 0-1 mutta jos enmmÃ¤n niin palauttaa kaikki joiden aika on
		//TÃ¤hÃ¤n pitÃ¤Ã¤ keksiÃ¤ ratkaisu esim 
		//ettÃ¤ siirtÃ¤Ã¤ liian lÃ¤hellÃ¤ samaan aikaan olevia tai ettÃ¤ sovelluksen pÃ¤Ã¤ssÃ¤ lokeroiden ottoaikaa rajoitetaan ettei pÃ¤Ã¤llekkÃ¤isyyksiÃ¤ synny
		return found;
	}
	
	public static void CreateSlots(int numberOfSlots) 
	{
		slots = new Slot[numberOfSlots];
		
		for(int i = 0; i < slots.length; i++) 
		{
			slots[i] = new Slot();
			
			slots[i].setId(i);
			slots[i].setState(false);
			slots[i].clearMedicines();
			slots[i].setTimeToTake(null);
		}
	}
	
	public static void CreateDemo() 
	{
		slots = new Slot[14];
		
		slots[0].setId(0);
		slots[0].setState(true);
		slots[0].clearMedicines();
		slots[0].setTimeToTake(LocalDateTime.now().plusSeconds(30));
	}
	
	
	public void Notification() 
	{
		String notification = "Patient.name" + " on aika ottaa laake!";
		
		System.out.print(notification);
		
		//TODO tÃ¤nne koodipÃ¤tkÃ¤ ettÃ¤ saadaan valo vilkkumaan Dosetissa
		
		//TODO tÃ¤nne koodipÃ¤tkÃ¤ ettÃ¤ Dosetti Ilmoittamaan Ã¤Ã¤nellÃ¤ ettÃ¤ lÃ¤Ã¤ke pitÃ¤Ã¤ ottaa
	}
	
	public void SendMessage() 
	{
		//TODO Kun Patient/User ja Medicine luokat on tehty SendMessage(User user, Medicine medicine) jotta saadaa viestiin potilas, lÃ¤Ã¤ke ja lÃ¤Ã¤kkeenottoaika
		
		String message = patientName + " on unohtanut ottaa lÃ¤Ã¤kkeen " + "Medicine.name" + " aikaan " + "Medicine.timeToTake";
		
		System.out.print(message);
		
		//TODO pitÃ¤Ã¤ lÃ¶ytÃ¤Ã¤ tapa jolla message saadaan lÃ¤hetettyÃ¤ sovelluksee/tietokoneelle
	}
	
	
	
	

}
