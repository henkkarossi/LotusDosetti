package toiminnallisuus;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


public class Motor extends Part {
	private String name; //our motor for pill dispenser is a step motor
	private boolean status; //on (=true) or off (=false)
	//private int pin; selvitä tätä varten moottorin pinni vai pinnilista Part- luokasta?
	private GpioController gpio = GpioFactory.getInstance();
	private GpioPinDigitalOutput[] pins;
	private GpioStepperMotorComponent motor;
	private byte[] single_step_sequence;
	
	public Motor() {
		this.name = "askelmoottori"; //vai alustetaanko? = new GpioStepperMotorComponent(pins);
		this.status = false;
		this.gpio = GpioFactory.getInstance();
		this.pins = new GpioPinDigitalOutput[4];
        pins[0] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
        pins[1] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW);
        pins[2] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW);
        pins[3] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW);
        this.motor = new GpioStepperMotorComponent(pins);
        gpio.setShutdownOptions(true, PinState.LOW, pins);
        this.single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;
        motor.setStepInterval(2);
        motor.setStepSequence(single_step_sequence);
	}
	
	//moottorille käsky ottaa yksi basicStep vai onko valmiina step?
	/*apuna tutorial: http://www.savagehomeautomation.com/jj-stepper
	 *  // There are 32 steps per revolution on my sample motor,
        // and inside is a ~1/64 reduction gear set.
        // Gear reduction is actually: (32/9)/(22/11)x(26/9)x(31/10)=63.683950617
        // This means is that there are really 32*63.683950617 steps per revolution
        // =  2037.88641975 ~ 2038 steps! 
         * --> joten yksi askel on 2037.88641975/14 = 145.5633156... askelta per lokero?*/
	
	@Override
	public String toString() {
		if (this.status) {
			return "The motor " + this.name + " is ON.";
		} else {
			return "The motor " + this.name + " is OFF.";
		}
	}
	
	public void TakeStep(int amount) throws InterruptedException 
	{
		int fullRotation = 2038;
		long slot = fullRotation / 14;
		
		long move = slot * amount;
        
        // There are 32 steps per revolution on my sample motor, and inside is a ~1/64 reduction gear set.
        // Gear reduction is actually: (32/9)/(22/11)x(26/9)x(31/10)=63.683950617
        // This means is that there are really 32*63.683950617 steps per revolution =  2037.88641975 ~ 2038 steps!
        
        //Melissa: So, if our pill dispenser has 14 slots in it, one slot equals 2038 / 14 = 145,5714285... ~ 146 steps 
        motor.setStepsPerRevolution(fullRotation);

        motor.step(move);
        
        motor.stop();

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
	}
	

	


}
