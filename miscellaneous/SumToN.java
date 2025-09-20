public class SumToN {	// recursively computes the sum of integers from 1 to n

	int sum(int n) {
		if (n < 1)
			return 0;
		else
			return n + sum(n - 1);
	}

	void main() {
		System.out.println("SumToN Demo");

		int n = 5;
		int s = sum(n);
		System.out.printf("sum of the integers from 1 to %d is %d%n", n, s);
	}
}
