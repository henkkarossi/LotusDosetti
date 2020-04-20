package toiminnallisuus;

public class Medicine {

	String name;
	String description;
	double dose;
	
	public Medicine(String name, String description, double dose) 
	{
		this.name = name;
		this.description = description;
		this.dose = dose;
	}
	
	public Medicine() 
	{
		this.name = "Name here";
		this.description = "Write Description here";
		this.dose = 0.0;
	}
}
