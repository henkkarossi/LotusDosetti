package toiminnallisuus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Master {

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
		//TODO tÃ¤ydennÃ¤
		//Jos load dataa ei löydy luo uudet tyhjät
		
		if(!LoadData()) 
		{
			patientName = "Anna potilaan nimi";
					
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
		//TODO tÃ¤ssÃ¤ pohja
		//if(Katsoo lÃ¶ytyykÃ¶ edellistÃ¤ tallennusta)
		//	Asettaa arvot kuten lokerolista samaksi kuin tallennetussa savessa
		//return true;
		//else
		return false;
	}
	
	public static void DeleteSaveData() 
	{
		//TODO poista tallennettu data filestÃ¤
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
		//TODO tee metodi joka siirtÃ¤Ã¤ slotin oikeaan kohtaan muistiinpanoja tÃ¤hÃ¤n liittyen lÃ¶ytyy planneristÃ¤
		
		//TODO tee metodi ja/tai luokka sensorille jota tÃ¤Ã¤ltÃ¤ kutsumalla selvittÃ¤Ã¤ 
		
		//TODO jos sensori palauttaa arvon tyhjÃ¤ niin kutsu lokeron metodia tyhjennÃ¤
	}
	
	public static void NotTaken() 
	{
		
	}
	
	public static void Refill() 
	{
		//TODO moodi jonka aikana kannen voi avata ja lokerot voi tÃ¤yttÃ¤Ã¤
		
		//TODO metedi joka kÃ¤y kaikki lokerot lÃ¤pi
		//Jos lokero johon on merkitty ettÃ¤ pitÃ¤isi olla lÃ¤Ã¤kkeitÃ¤ ja timeToTake
		//Jos sensori sitten nÃ¤kee ettÃ¤ lokerossa on lÃ¤Ã¤ke se merkitsee lokeron tilan true ja false jos ei nÃ¤e
	}
	
	public static List<Slot> CheckSlots(int timeThreshold)
	{
		//TODO katso onko missÃ¤Ã¤n lokerolistan lokerossa otto aika = nyt + ja - annettu aika esim 5 min vÃ¤hemmÃ¤n tai enemmÃ¤n
		
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
		//YleensÃ¤ 0-1 mutta jos enmmÃ¤n niin palauttaa kaikki joiden aika on
		//TÃ¤hÃ¤n pitÃ¤Ã¤ keksiÃ¤ ratkaisu esim 
		//ettÃ¤ siirtÃ¤Ã¤ liian lÃ¤hellÃ¤ samaan aikaan olevia tai ettÃ¤ sovelluksen pÃ¤Ã¤ssÃ¤ lokeroiden ottoaikaa rajoitetaan ettei pÃ¤Ã¤llekkÃ¤isyyksiÃ¤ synny
		return found;
	}
	
	public void Notification() 
	{
		String notification = "Patient.name" + " on aika ottaa lÃ¤Ã¤ke!";
		
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
