package toiminnallisuus;

public class Medicine {

	String name;
	String description;
	double dose; //how many pills, pills can be divided
	
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
	
	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return description;
	}

	public double getDose() {
		return dose;
	}

	public void setDose(double dose) {
		this.dose = dose;
	}

	@Override
	public String toString() {
		return name + ", " + dose;
	}
}
