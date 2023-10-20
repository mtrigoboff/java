package animals;

abstract public class Animal {
	protected String species;

	Animal(String species) {
		this.species = species;
	}

	public String toString() {
		return species;
	}

}
