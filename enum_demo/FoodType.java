
import java.util.*;

import mlt.stdin;

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
		String[] line;
		FoodType foodType;

		System.out.println("FoodType 1.1");

		for (;;) {
			System.out.print("> ");
			line = stdin.getLineWords();
			if (line == null || line.length == 0) {
				continue;
			}
			if (line[0].equalsIgnoreCase("EXIT")) {
				break;
			}
			foodType = exampleMap.get(line[0]);
			if (foodType == null) {
				System.out.printf("%s is not an example of any food type%n", line[0]);
			} else {
				System.out.printf("%s is an example of food type %s%n", line[0], foodType);
			}
		}

		System.out.println("done...");
	}
}
