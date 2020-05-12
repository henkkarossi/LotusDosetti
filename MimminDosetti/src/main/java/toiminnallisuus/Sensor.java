package toiminnallisuus;

public class Sensor {

	//TODO aseta tänne oikeat raja arvot------------V--V--V
	public static int[] colorTreshold = new int[] { 0, 0, 0};
	
	public boolean CheckForPill() 
	{
		int[] colors = Scan();
		
		for(int i = 0; i < colors.length; i++) 
		{
			if(colors[i] > colorTreshold[i]) 
			{
				return true;
			}
		}
		return false;
	}
	
	public int[] Scan() 
	{
		int[] colors = new int[3];
		
		//TODO tänne koodi mikä lukee sensorin datan ja varastoi R, G ja B värien arvot colors[] arrayhin 
		
		return colors;
	}

}
