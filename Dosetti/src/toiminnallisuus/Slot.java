package toiminnallisuus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Slot {
	private int id;
	private boolean state;
	private List<Medicine> medicines;
	private LocalDateTime timeToTake;
	
	public Slot(int id, boolean state, List<Medicine> medicines, LocalDateTime timeToTake)
	{
		this.id = id;
		this.state = state;
		this.medicines = medicines;
		this.timeToTake = timeToTake;
	}
	
	public Slot()
	{
		this.id = 0;
		this.state = false;
		this.medicines = new ArrayList<Medicine>();
		this.timeToTake = null;
	}
	
	public void addMedicine(Medicine medicine) 
	{
		this.medicines.add(medicine);
	}
	
	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void addMedicineList(List<Medicine> medicines) 
	{
		for(Medicine medicine: medicines) 
		{
			this.medicines.add(medicine);
		}
	}

	
	//tyhjennä lokero metodi? pitäisikö olla mieluummin this.medicines.clear()?
	public void emptyMedicines() 
	{
		this.medicines = new ArrayList<Medicine>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//onko erityinen syy, ettei metodin nimi ole getState?
	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public LocalDateTime getTimeToTake() {
		return timeToTake;
	}

	public void setTimeToTake(LocalDateTime timeToTake) {
		this.timeToTake = timeToTake;
	}
	
	@Override
	public String toString() {
		if (medicines.isEmpty()) {
			return "No medicine in the slot " + this.id;
		} else {
			String msg = "The slot " + this.id + " contains medicines:";
			
			for (int i = 0; i < medicines.size(); i++) {
				String medicineString = medicines.get(i).toString();
				msg += "\n" + medicineString;
			}
			return msg;
		}
	}
}
