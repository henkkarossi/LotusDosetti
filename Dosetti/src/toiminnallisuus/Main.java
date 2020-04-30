package toiminnallisuus;

import org.json.simple.parser.ParseException;

public class Main {
	
	public static PillDispenser pl;
	
	public static void main(String[] args) throws ParseException {
		
		pl = new PillDispenser();

		DataManager.CreateNewBaseData();
		//DataManager.SaveData();
		DataManager.LoadData();
		
		System.out.print(pl.slots[4].getState());
				
		PillDispenser.running = true;
	}
	
	
	

}
