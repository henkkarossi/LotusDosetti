package toiminnallisuus;



import com.pi4j.io.gpio.GpioController;

import com.pi4j.io.gpio.GpioFactory;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import com.pi4j.io.gpio.RaspiPin;


public class Led {
    final GpioController gpio = GpioFactory.getInstance();

    final GpioPinDigitalOutput led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
    
    public void toggle() throws InterruptedException {
     
        led1.toggle();   
       
    }
    
    public void setHigh() {
    	if  (led1.isLow()) {
    		led1.high();
    	}
    			
    }
    
    public void setLow() {
    	
    	if (led1.isHigh()) {
    		led1.low();
    	}
    	
    }
    


}


