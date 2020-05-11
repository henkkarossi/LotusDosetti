package toiminnallisuus;



import com.pi4j.io.gpio.GpioController;

import com.pi4j.io.gpio.GpioFactory;

import com.pi4j.io.gpio.GpioPinDigitalInput;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import com.pi4j.io.gpio.PinPullResistance;

import com.pi4j.io.gpio.RaspiPin;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;

import com.pi4j.io.gpio.event.GpioPinListenerDigital;


public class Blink {

	static boolean running = true;
	
	static boolean on = true;

    public static void main(String[] args) throws InterruptedException {

    	
        final GpioController gpio = GpioFactory.getInstance();

        final GpioPinDigitalOutput led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);

        while(running) 
        {
        	
        	if(on)
        		led1.blink(500, 15000);
        
        }

    }
    
    public void OnOff(boolean on) 
    {
    	this.on = on;
    }
    
    public void setRunning(boolean running) 
    {
    	this.running = running;
    }

}


