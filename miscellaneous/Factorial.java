public class Factorial {

	static int n = 3;

	public static int factIterative(int n) {
		int f = 1;

		while (n > 1)
			f *= n--;
		
		return f;
	}

	public static int factRecursive(int n) {
		if (n <= 1)
			return 1;
		else
			return n * factRecursive(n - 1);
	}
	
	public static void main(String[] args) {
		System.out.printf("factIterative(%d) = %d%n", n, factIterative(n));
		System.out.printf("factRecursive(%d) = %d%n", n, factRecursive(n));
	}
}
