package toiminnallisuus;

import org.json.simple.parser.ParseException;

public class Main {
	
	public static PillDispenser pl;
	
	public static void main(String[] args) throws Exception {
		
		
		pl = new PillDispenser();

		DataManager.CreateNewBaseData();
		
		///Kommentoi pois jos ei ole demo !!!!!!!!!!!!!!!!!!!!!!!
		pl.CreateDemo();
		
		DataManager.SaveData();
		//DataManager.LoadData();
		
		System.out.print(pl.slots[0].getState());
		
		pl.running = true;
		pl.main(null);
	}
	
	
	

}
