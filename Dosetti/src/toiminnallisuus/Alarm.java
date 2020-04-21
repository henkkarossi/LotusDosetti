package toiminnallisuus;

public class Alarm extends Part{
	private String name;
	private boolean status;
	
	public Alarm(String name) {
		this.name = name;
		this.status = false;
	}
	
	public void giveAlarmSound() {
		//TODO
	}
	
	public void giveNotificationLight() {
		//TODO
	}
	
	public void giveNotificationSound() {
		//TODO
	}
	
	@Override
	public String toString() {
		if (this.status) {
			return "Alarm device " + this.name + " is ON.";
		} else {
			return "Alarm device " + this.name + " is OFF";
		}
	}
}
