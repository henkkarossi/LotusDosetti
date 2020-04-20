package toiminnallisuus;

public class Part {
	private String name;
	private boolean status;
	//pinnilista oliomuuttujaksi?
	
	public Part() {
		this.name = null;
		this.status = false;
	}
	
	//TODO: tee metodiin oikeasti sammutus ja käynnistys, PinState.HIGH/LOW

	//metodi toimii setStatus tyyppisenä ja toimii "katkaisijana"
	public void switchButton() {
		if (this.status == true) {
			this.status = false;
			//PinState.LOW
		} else {
			this.status = true;
			//PinState.HIGH
		}
	}

	public boolean getStatus() {
		return this.status;
	}
	
	@Override
	public String toString() {
		if (this.status) {
			return "The part " + name + " is ON.";
		} else {
			return "The part " + name + " is OFF.";
		}
	}
}
