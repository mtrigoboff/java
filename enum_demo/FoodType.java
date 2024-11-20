
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public enum FoodType {
	DAIRY("milk", "cheese"),
	FRUIT("apple", "banana", "pear"),
	GRAIN("rice", "cereal"),
	MEAT("chicken", "beef", "pork"),
	VEGETABLE("carrot", "corn", "broccoli");

	private String[] examples;

	static Map<String, FoodType> exampleMap = new HashMap<>();

	static {
		// store examples of each food type in exampleMap
		for (FoodType foodType : FoodType.values()) {
			for (String example : foodType.examples) {
				exampleMap.put(example, foodType);
			}
		}
	}

	FoodType(String... examples) {
		this.examples = examples;
	}

	public static void main(String[] args) {
		String line;
		FoodType foodType;

		System.out.println("FoodType 1.1");

		try (Scanner sc = new Scanner(System.in)) {
			for (;;) {
				System.out.print("> ");
				line = sc.nextLine();
				if (line == null || line.length() == 0) {
					break;
				}
				foodType = exampleMap.get(line);
				if (foodType == null) {
					System.out.printf("%s is not an example of any food type%n", line);
				} else {
					System.out.printf("%s is an example of food type %s%n", line, foodType);
				}
			}
		}

		System.out.println("done...");
	}
}
