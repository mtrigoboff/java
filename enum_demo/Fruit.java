// the simplest possible enum

public enum Fruit {
	APPLE, BANANA, CHERRY, PEAR;

	public static void main(String[] args) {
		System.out.println("Fruit");

		Fruit f = PEAR;

		f = APPLE;
		if (f == APPLE)
			System.out.println("APPLE");
	}
}
