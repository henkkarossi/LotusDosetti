package toiminnallisuus;

public class Data {
	public String nimi;
	public String osoite;
	public String syntyma;
	public String laake;
	public String timeToTake;
	public boolean ok;
	public String time;
	
	public Data(String nimi, String osoite, String syntyma, String laake, String timeToTake, boolean ok, String time) {
		this.nimi = nimi;
		this.osoite = osoite;
		this.syntyma = syntyma;
		this.laake = laake;
		this.timeToTake = timeToTake;
		this.ok = ok;
		this.time = time;
	}
	
	public Data() {
	}

	public String getTimeToTake() {
		return this.timeToTake;
	}
	
	
	
}
