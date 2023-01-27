package animals;

abstract class Animal {
	String species;

	Animal(String species) {
		this.species = species;
	}

	public String toString() {
		return species;
	}

}
