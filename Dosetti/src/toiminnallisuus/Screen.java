package toiminnallisuus;

import java.time.LocalDateTime;

public class Screen extends Part{
	private String name;
	private boolean status;
	
	public Screen() {
		this.name = "led screen";
		this.status = false;
	}
	
	public String msgDateAndTime() {
		String msg = "";
		LocalDateTime now = LocalDateTime.now();
		int day = now.getDayOfMonth();
		int month = now.getMonthValue();
		int year = now.getYear();
		int hour = now.getHour();
		int minutes = now.getMinute();
		msg = day + "." + month + "." + year + "\n" + hour + ":" + minutes;
		return msg;
	}
	
	public String msgNotification() {
		//TODO
		return "TODO";
	}
	
	public String msgAlarm() {
		//TODO
		return "TODO";
	}
}
