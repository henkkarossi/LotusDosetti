package toiminnallisuus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Master {
	//paljon viel� teht�v��

	public enum State{ idle, medicine, notTaken, refill}
	static State state = State.idle;
	
	static String patientName;
	
	static Slot[] slots = new Slot[14];
	
	//TODO miten lokerolista 14kpl luodaan ensimmäisellä avauskerralla
	//Ehkä mukaan Save Load logiikka
	//Avatessa loadaa ja jos ei löydy savea luo uudet lokerot
	
	public static void main(String[] args) throws InterruptedException {
		
		AtStart();
		
		boolean running = true;
		
		while(running) 
		{
			switch(state) 
			{
			
			case idle:
				Idle();
				break;
				
			case medicine:
				Medicine();
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
	
	public static void AtStart() 
	{
		//TODO täydennä
		//Jos load dataa ei löydy luo uudet tyhjät
		
		if(!LoadData()) 
		{
			patientName = "Täytä potilaan nimi";
					
			for(int i = 0; i < slots.length; i++) 
			{
				slots[i].setId(i + 1);
				slots[i].setState(false);
				slots[i].emptyMedicines();
				slots[i].setTimeToTake(null);
			}
		}
	}
	
	public static boolean LoadData() 
	{
		//TODO tässä pohja
		//if(Katsoo löytyykö edellistä tallennusta)
		//	Asettaa arvot kuten lokerolista samaksi kuin tallennetussa savessa
		//return true;
		//else
		return false;
	}
	
	public static void DeleteSaveData() 
	{
		//TODO poista tallennettu data filestä
	}
	
	public static void Idle() throws InterruptedException 
	{
		TimeUnit.SECONDS.sleep(1);

		if(CheckSlots(3).size() > 0) 
		{
			state = State.medicine;
		}
	}
	
	public static void Medicine() 
	{
		//TODO tee metodi joka siirtää slotin oikeaan kohtaan muistiinpanoja tähän liittyen löytyy planneristä
		
		//TODO tee metodi ja/tai luokka sensorille jota täältä kutsumalla selvittää 
		
		//TODO jos sensori palauttaa arvon tyhjä niin kutsu lokeron metodia tyhjennä
	}
	
	public static void NotTaken() 
	{
		
	}
	
	public static void Refill() 
	{
		//TODO moodi jonka aikana kannen voi avata ja lokerot voi täyttää
		
		//TODO metedi joka käy kaikki lokerot läpi
		//Jos lokero johon on merkitty että pitäisi olla lääkkeitä ja timeToTake
		//Jos sensori sitten näkee että lokerossa on lääke se merkitsee lokeron tilan true ja false jos ei näe
	}
	
	public static List<Slot> CheckSlots(int timeThreshold)
	{
		//TODO katso onko missään lokerolistan lokerossa otto aika = nyt + ja - annettu aika esim 5 min vähemmän tai enemmän
		
		List<Slot> found = new ArrayList<Slot>();
		LocalDateTime now = LocalDateTime.now(); 
		
		for(Slot slot:slots) 
		{
			if(slot.timeToTake.getYear() == now.getYear())
				if(slot.timeToTake.getDayOfYear() == now.getDayOfYear())
					if(slot.timeToTake.getHour() == now.getHour())
						if(slot.timeToTake.getMinute() < now.getMinute() + timeThreshold && slot.timeToTake.getMinute() > now.getMinute() - timeThreshold)
							found.add(slot);
			
		}
		
		//Palauttaa listan lokeroista joiden aika ottaa on nyt
		//Yleensä 0-1 mutta jos enmmän niin palauttaa kaikki joiden aika on
		//Tähän pitää keksiä ratkaisu esim 
		//että siirtää liian lähellä samaan aikaan olevia tai että sovelluksen päässä lokeroiden ottoaikaa rajoitetaan ettei päällekkäisyyksiä synny
		return found;
	}
	
	public void Notification() 
	{
		String notification = "Patient.name" + " on aika ottaa lääke!";
		
		System.out.print(notification);
		
		//TODO tänne koodipätkä että saadaan valo vilkkumaan Dosetissa
		
		//TODO tänne koodipätkä että Dosetti Ilmoittamaan äänellä että lääke pitää ottaa
	}
	
	public void SendMessage() 
	{
		//TODO Kun Patient/User ja Medicine luokat on tehty SendMessage(User user, Medicine medicine) jotta saadaa viestiin potilas, lääke ja lääkkeenottoaika
		
		String message = patientName + " on unohtanut ottaa lääkkeen " + "Medicine.name" + " aikaan " + "Medicine.timeToTake";
		
		System.out.print(message);
		
		//TODO pitää löytää tapa jolla message saadaan lähetettyä sovelluksee/tietokoneelle
	}
	

}
