package toiminnallisuus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class DataManager {

	static String filename = "slotdata.txt";
	
	public static void SaveData() 
	{
		JSONObject jo = new JSONObject();
		
		JSONArray ja = new JSONArray(); 
		
		Map m = new LinkedHashMap(2);
		
	    for(Slot slot: Main.pl.slots) 
	    {
	    	 m = new LinkedHashMap(slot.getClass().getFields().length); 
	    	 
	    	 m.put("id", slot.getId()); 
	         m.put("state", slot.getState()); 
	         m.put("timeToTake", slot.getTimeToTake()); 
	         
	         ja.add(m);
	    }
	    
	    jo.put("slotData", ja);

	    System.out.print(jo);
	      
	    WriteToFile(jo.toJSONString());
	}
	
	public static void LoadData() throws ParseException 
	{
		Object obj = new JSONParser().parse(ReadFile() ); 
       
        JSONObject jo = (JSONObject) obj; 

        JSONArray ja = (JSONArray) jo.get("slotData");   

        Iterator itr2 = ja.iterator();
        
        List<Boolean> states = new ArrayList<Boolean>();
        List<LocalDateTime> dates = new ArrayList<LocalDateTime>();
        
        while (itr2.hasNext())  
        { 
        	Iterator<Map.Entry> itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            while (itr1.hasNext()) { 
                Map.Entry pair = itr1.next(); 
                
                if(pair.getKey().equals("state")) 
                {
                	states.add((Boolean) pair.getValue());
                }
                
                if(pair.getKey().equals("timeToTake")) 
                {
                	dates.add((LocalDateTime) pair.getValue());
                }
            } 
        } 
        
        for(int i = 0; i < Main.pl.slots.length; i++) 
        {
        	Main.pl.slots[i].setId(i);
        	Main.pl.slots[i].setState(states.get(i));
        	Main.pl.slots[i].setTimeToTake(dates.get(i));
        }
	     
        
	}
	
	
	
	public static void CreateFile() 
	{
		try {
		      File myObj = new File(filename);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	public static void WriteToFile(String writeThis) 
	{
		try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write(writeThis);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	public static void ClearFile() 
	{
		try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write("");
		      myWriter.close();
		      System.out.println("Successfully deleted the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	public static String ReadFile() 
	{
		try {
		      File myObj = new File(filename);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        return data;
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("No file found");
		      e.printStackTrace();
		      return null;
		    }
		
		return null;
	}
	
	public static void CreateNewBaseData() 
	{
		PillDispencer.patientName = "Anna potilaan nimi";
		
		PillDispencer.CreateSlots(14);
	
	}
}





