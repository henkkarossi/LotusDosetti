package toiminnallisuus;

public class Motor extends Part {
	private String name; //our motor for pill dispenser is a step motor
	private boolean status; //on (=true) or off (=false)
	private double basicStep; //normal step that motor need to take to travel one slot width
	private int position; //=how many steps taken
	//private int pin; selvitä tätä varten moottorin pinni vai pinnilista Part- luokasta?
	
	
	public Motor() {
		this.name = "askelmoottori"; //vai alustetaanko? = new GpioStepperMotorComponent(pins);
		this.status = false;
		this.basicStep = 145.56; //kts. metodista step() selitys 
		this.position = 0; //starting position, koko kierros on 2038 askelta
		//this.pin = null;
	}
	
	//moottorille käsky ottaa yksi basicStep vai onko valmiina step?
	/*apuna tutorial: http://www.savagehomeautomation.com/jj-stepper
	 *  // There are 32 steps per revolution on my sample motor,
        // and inside is a ~1/64 reduction gear set.
        // Gear reduction is actually: (32/9)/(22/11)x(26/9)x(31/10)=63.683950617
        // This means is that there are really 32*63.683950617 steps per revolution
        // =  2037.88641975 ~ 2038 steps! 
         * --> joten yksi askel on 2037.88641975/14 = 145.5633156... askelta per lokero?*/
	
	public void step() {
		if (this.getStatus() == false) {
			//käynnistä moottori
			//this.switchButton();
			//this.pin =  PinState.HIGH; //tms?
			this.status = true;
			this.position += this.basicStep;
		}	
	}
	
	//moottorin nollaaminen alkukohtaan
	public void resetStartPosition() {
		//motor REVERSE otetut askeleet? jolloin ->
		this.position = 0;
	}
	
	
	@Override
	public String toString() {
		if (this.status) {
			return "The motor " + name + " is ON.";
		} else {
			return "The motor " + name + " is OFF.";
		}
	}

	


}
