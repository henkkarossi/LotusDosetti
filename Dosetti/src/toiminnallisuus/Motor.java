package toiminnallisuus;

public class Motor{
	private String nimi;
	private boolean status; //on (=true) or off (=false)
	
	//TODO: tee metodiin oikeasti sammutus ja käynnistys

	public void switchButton() {
		if (this.status == true) {
			this.status = false;
		} else {
			this.status = true;
		}
	}


	public boolean getStatus() {
		return this.status;
	}

}
