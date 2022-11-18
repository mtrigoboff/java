public class Lambda {
	// lambda that takes one argument

	interface Calculation1 {
		// "functional interface" must have exactly ONE method definition
		int calculate(int x);
	}

	static void printValues1(int[] xValues, String name, Calculation1 c) {
		System.out.printf("%s%n", name.substring(0, 1).toUpperCase() + name.substring(1));
		for (int i : xValues)
			System.out.printf("the %s of %d is %3d%n", name, i, c.calculate(i));
		System.out.println();
	}

	static void test1(int[] xValues) {

		// without lambda
		printValues1(xValues, "square (no lambda)", new Calculation1() {
			public int calculate(int x) {
				return x * x;
			}
		});

		// with lambda
		printValues1(xValues, "square", x -> x * x);
		printValues1(xValues, "cube", x -> x * x * x);
		printValues1(xValues, "remainder divided by 3", i -> i % 3);
		printValues1(xValues, "integer square root", i -> (int) Math.sqrt((double) i));
	}

	// lambda that takes two arguments

	interface Calculation2 {
		// "functional interface" must have exactly ONE method definition
		int calculate(int x, int y);
	}

	static void printValues2(int[] xValues, int[] yValues, String name, Calculation2 c) {
		System.out.printf("%s%n", name.substring(0, 1).toUpperCase() + name.substring(1));
		for (int i = 0; i < xValues.length; i++)
			System.out.printf("the %s of %d and %d is %2d%n", name, xValues[i], yValues[i],
					c.calculate(xValues[i], yValues[i]));
		System.out.println();
	}

	static void test2(int[] xValues, int[] yValues) {

		// without lambda
		printValues2(xValues, yValues, "sum (no lambda)", new Calculation2() {
			public int calculate(int x, int y) {
				return x + y;
			}
		});

		// with lambda
		printValues2(xValues, yValues, "sum", (x, y) -> x + y);
		printValues2(xValues, yValues, "product", (x, y) -> x * y);
	}

	// run lambda demo

	public static void main(String[] args) {
		System.out.printf("Lambda Demo 1.0%n%n");

		int[] xValues = new int[] { 1, 2, 3, 4, 5 };
		int[] yValues = new int[] { 2, 4, 6, 8, 9 };

		test1(xValues);
		test2(xValues, yValues);
	}
}
