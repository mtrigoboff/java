import java.util.ArrayList;

public class Fibonbacci {

	// non-recursive implementation
	public static int fibIterative(int n) {
		int fPrev2 = 0;
		int fPrev = 1;
		int fCurr = -1;		// unneccessary initialization, but Java compiler insists >:-(

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

	// inefficient: does many recursions over the same values
	public static int fibRecursive(int n) {
		if (n <= 0)
			return 0;
		else if (n == 1)
			return 1;
		else
			return fibRecursive(n - 1) + fibRecursive(n - 2);
	}

	// efficient but more complicated recursion: stores series in an ArrayList
	public static void fibRecursive1R(int i, int n, ArrayList<Integer> fibSeries) {
		if (i >= n)
			fibSeries.add(0);
		else if (i == n - 1) {
			fibSeries.add(0);
			fibSeries.add(1);
		} else {
			fibRecursive1R(i + 1, n, fibSeries);								// add previous members of series
			int size = fibSeries.size();
			fibSeries.add(fibSeries.get(size - 1) + fibSeries.get(size - 2));	// add next member of series
		}
	}

	// not recursive: sets up ArrayList, calls fibRecursive1R for recursion
	public static int fibRecursive1(int n) {
		ArrayList<Integer> fibSeries = new ArrayList<>();
		fibRecursive1R(0, n, fibSeries);
		return fibSeries.get(fibSeries.size() - 1);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
			System.out.printf("fib(%d) = %2d, %2d, %2d%n", i,
							  fibIterative(i), fibRecursive(i), fibRecursive1(i));
	}
	
}
