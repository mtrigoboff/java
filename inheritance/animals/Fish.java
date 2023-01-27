package animals;

public class Fish extends Animal {
	private boolean dangerous;
	
	public Fish(String kind, boolean dangerous) {
		super(kind);
		this.dangerous = dangerous; 
	}
	
	public String toString() {
		return "The " + super.toString() + (dangerous ? " is " : " is not ") + "dangerous.";
	}

}
