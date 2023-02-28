package animals;

public class Bird extends Animal {
	private boolean canFly;

	public Bird(String species, boolean canFly) {
		super(species);
		this.canFly = canFly;
	}

	public String toString() {
		return "The " + super.toString() + (canFly ? " can " : " cannot ") + "fly.";
	}
}
