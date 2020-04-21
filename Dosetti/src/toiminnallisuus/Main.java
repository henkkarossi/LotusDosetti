package toiminnallisuus;

public class Main {
	
	public static void main(String[] args) {
		//TODO tÃ¤ydennÃ¤
				//Jos load dataa ei löydy luo uudet tyhjät
				
		if(!LoadData()) 
		{
			CreateNewData();
		}
				
		PillDispencer.running = true;
	}
	
	public static boolean LoadData() 
	{
		//Minna testaa githubia
		//TODO tÃ¤ssÃ¤ pohja
		//if(Katsoo lÃ¶ytyykÃ¶ edellistÃ¤ tallennusta)
		//	Asettaa arvot kuten lokerolista samaksi kuin tallennetussa savessa
		//System.out.print("Data Loaded");
		//return true;
		//else{
		System.out.print("Data not loaded ");
		return false;
		//}
	}
	
	public static void CreateNewData() 
	{
		PillDispencer.patientName = "Anna potilaan nimi";
		
		PillDispencer.CreateSlots(14);
		
		for(int i = 0; i < PillDispencer.slots.length; i++) 
		{
			PillDispencer.slots[i].setId(i + 1);
			PillDispencer.slots[i].setState(false);
			PillDispencer.slots[i].emptyMedicines();
			PillDispencer.slots[i].setTimeToTake(null);
			
			System.out.print("new slot created " + i);
		}
		
		System.out.print("new Data created ");
	}
	

}
