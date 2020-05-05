package demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class PillDispenserTimedMotorDemo implements Runnable {
	public static void main(String[] args) throws InterruptedException {
		
		//printForAMinute();
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Runnable rmotor = new Runnable() {
			public void run() {
				// create gpio controller
				final GpioController gpio = GpioFactory.getInstance();

				// provision gpio pins #00 to #03 as output pins and ensure in LOW state
				final GpioPinDigitalOutput[] pins = { gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
						gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
						gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
						gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW) };

				// this will ensure that the motor is stopped when the program terminates
				gpio.setShutdownOptions(true, PinState.LOW, pins);

				// create motor component
				GpioStepperMotorComponent motor = new GpioStepperMotorComponent(pins);

				// @see http://www.lirtex.com/robotics/stepper-motor-controller-circuit/
				// for additional details on stepping techniques

				// create byte array to demonstrate a single-step sequencing
				// (This is the most basic method, turning on a single electromagnet every time.
				// This sequence requires the least amount of energy and generates the smoothest
				// movement.)
				byte[] single_step_sequence = new byte[4];
				single_step_sequence[0] = (byte) 0b0001;
				single_step_sequence[1] = (byte) 0b0010;
				single_step_sequence[2] = (byte) 0b0100;
				single_step_sequence[3] = (byte) 0b1000;

				// define stepper parameters before attempting to control motor
				// anything lower than 2 ms does not work for my sample motor using single step
				// sequence
				motor.setStepInterval(2);
				motor.setStepSequence(single_step_sequence);

				// There are 32 steps per revolution on my sample motor, and inside is a ~1/64
				// reduction gear set.
				// Gear reduction is actually: (32/9)/(22/11)x(26/9)x(31/10)=63.683950617
				// This means is that there are really 32*63.683950617 steps per revolution =
				// 2037.88641975 ~ 2038 steps!

				// Melissa: So, if our pill dispenser has 14 slots in it, one slot equals 2038 /
				// 14 = 145,5714285... ~ 146 steps
				
				motor.setStepsPerRevolution(2038);

				// Melissa: motor control : TAKING 'SLOT STEP', i.e. 146 steps / slot

				motor.step(146);

				// final stop to ensure no motor activity
				motor.stop();

				// stop all GPIO activity/threads by shutting down the GPIO controller
				// (this method will forcefully shutdown all GPIO monitoring threads and
				// scheduled tasks)
				gpio.shutdown();
			}
		};
		final ScheduledFuture<?> rmotorHandle = scheduler.scheduleAtFixedRate(rmotor, 5, 10, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				rmotorHandle.cancel(true);
			}
		}, 60, SECONDS);

	}

	public static void printForAMinute() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Runnable textMotor = new Runnable() {
			public void run() {
				System.out.println("Motor turning now");
			}
		};
		final ScheduledFuture<?> textMotorHandle = scheduler.scheduleAtFixedRate(textMotor, 2, 5, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				textMotorHandle.cancel(true);
			}
		}, 60, SECONDS);
	}

	@Override
	public void run() {
		
		// create gpio controller
		final GpioController gpio = GpioFactory.getInstance();

		// provision gpio pins #00 to #03 as output pins and ensure in LOW state
		final GpioPinDigitalOutput[] pins = { gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
				gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW) };

		// this will ensure that the motor is stopped when the program terminates
		gpio.setShutdownOptions(true, PinState.LOW, pins);

		// create motor component
		GpioStepperMotorComponent motor = new GpioStepperMotorComponent(pins);

		// @see http://www.lirtex.com/robotics/stepper-motor-controller-circuit/
		// for additional details on stepping techniques

		// create byte array to demonstrate a single-step sequencing
		// (This is the most basic method, turning on a single electromagnet every time.
		// This sequence requires the least amount of energy and generates the smoothest
		// movement.)
		byte[] single_step_sequence = new byte[4];
		single_step_sequence[0] = (byte) 0b0001;
		single_step_sequence[1] = (byte) 0b0010;
		single_step_sequence[2] = (byte) 0b0100;
		single_step_sequence[3] = (byte) 0b1000;

		// define stepper parameters before attempting to control motor
		// anything lower than 2 ms does not work for my sample motor using single step
		// sequence
		motor.setStepInterval(2);
		motor.setStepSequence(single_step_sequence);

		// There are 32 steps per revolution on my sample motor, and inside is a ~1/64
		// reduction gear set.
		// Gear reduction is actually: (32/9)/(22/11)x(26/9)x(31/10)=63.683950617
		// This means is that there are really 32*63.683950617 steps per revolution =
		// 2037.88641975 ~ 2038 steps!

		// Melissa: So, if our pill dispenser has 14 slots in it, one slot equals 2038 /
		// 14 = 145,5714285... ~ 146 steps
		
		motor.setStepsPerRevolution(2038);

		// Melissa: test motor control : TAKING 'SLOT STEP', i.e. 146 steps / slot

		motor.step(146);

		// final stop to ensure no motor activity
		motor.stop();

		// stop all GPIO activity/threads by shutting down the GPIO controller
		// (this method will forcefully shutdown all GPIO monitoring threads and
		// scheduled tasks)
		gpio.shutdown();

	}

}
