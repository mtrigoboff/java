import java.util.InputMismatchException;
import java.util.Scanner;

public enum Operation {
	// do arithmetic op represented by this constant
	PLUS("+") {
		double eval(double x, double y) {
			return x + y;
		}
	},
	MINUS("-") {
		double eval(double x, double y) {
			return x - y;
		}
	},
	TIMES("*") {
		double eval(double x, double y) {
			return x * y;
		}
	},
	DIVIDE("/") {
		double eval(double x, double y) {
			return x / y;
		}
	};

	final String opStr;

	// require every member of this enum to implement eval()
	abstract double eval(double x, double y);

	// enum constructors must be declared private
	private Operation(String opStr) {
		this.opStr = opStr;
	}

	public static void main(String args[]) {
		System.out.println("Operation");
		try (Scanner sc = new Scanner(System.in)) {
			for (;;) {
				double x, y;
				System.out.print("enter two doubles (non-number to exit): ");
				try {
					x = sc.nextDouble();
					y = sc.nextDouble();
				} catch (InputMismatchException e) {	// non-number
					break;
				}

				for (Operation op : Operation.values())
					System.out.printf("%5.1f %s %-5.1f = %5.1f%n", x, op.opStr, y, op.eval(x, y));
			}
		}
	}
}
