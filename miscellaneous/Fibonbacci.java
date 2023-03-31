public class Fibonbacci {

		public static int fibIterative(int n) {
			int fPrev2 = 0;
			int fPrev = 1;
			int fCurr = -1;		// unneccessary iteration, but Java compiler insists >:-(

			if (n <= 0)
				return fPrev2;
			else if (n == 1)
				return fPrev;
			else {
				while (n-- - 2 >= 0) {
					fCurr = fPrev + fPrev2;
					fPrev2 = fPrev;
					fPrev = fCurr;
				}
				return fCurr;
			}
		}

		public static int fibRecursive(int n) {
			if (n <= 0)
				return 0;
			else if (n == 1)
				return 1;
			else
				return fibRecursive(n - 1) + fibRecursive(n - 2);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
			System.out.printf("fib(%d) = %2d, %2d%n", i, fibIterative(i), fibRecursive(i));
	}
	
}
