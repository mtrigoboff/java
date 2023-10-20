public class PrintfDemo {

	static int sum(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) {
		int i = 3;
		System.out.printf("i = %d%n", i);

		double pi = 3.1415928;
		System.out.printf("pi = %-14.4f%n", pi);

		String str = "xxxx";
		System.out.printf("the string is: %30s%d%n", str, 3);

		System.out.printf("$%,.2f", 123456789.0);
	}
}