import java.util.InputMismatchException;
import java.util.Scanner;

public class BinarySearch {
	
	static String[] coastGuardLetters = {
		"alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf",
		"hotel", "india", "juliet", "kilo", "lima", "mike", "november",
		"oscar", "papa", "quebec", "romeo", "sierra", "tango", "uniform",
		"victor", "whiskey", "xray", "yankee", "zulu"};

static boolean binarySearch(String[] a, String s) {
		int start = 0;
		int end = a.length - 1;
		int half;
		
		while (end - start >= 0) {
			half = start + (end - start) / 2;
			// System.out.printf("%d, %d, %d%n", start, half, end);
			if (a[half].equalsIgnoreCase(s))
				return true;
			else if (s.compareToIgnoreCase(a[half]) < 0)
				end = half - 1;
			else
				start = half + 1;

		}
		return false;
	}

	static String[] createArray(Scanner sc)
	{
		String[] array;			// stupid compier demands this initialization
		int size;
		while (true) {
			System.out.print("enter array size, 1 through 26: ");
			try {
				size = sc.nextInt();
				sc.nextLine();
				if (size < 0 || size > 26)
					continue;
				break;
			} catch (InputMismatchException e) {
				sc.nextLine();		// clear input buffer
				continue;
			}
		}
		array = new String[size];
		for (int i = 0; i < size; i++)
			array[i] = coastGuardLetters[i];
		return array;
	}

	public static void main(String[] args) {
		String[] a;
		String str;
		Scanner sc = new Scanner(System.in);
		
		a = createArray(sc);
		System.out.println("enter search string, empty string to exit");
		while (true) {
			System.out.print("-> ");
			str = sc.nextLine();
			if (str.equals(""))
				break;
			System.out.println(binarySearch(a, str) ? "found" : "not found");
		}
		sc.close();
		System.out.println("done");
	}
}
