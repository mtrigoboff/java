import java.util.InputMismatchException;
import java.util.Scanner;

public class BinarySearch {
	
	static String[] a26 = {
		"alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf",
		"hotel", "india", "juliet", "kilo", "lima", "mike", "november",
		"oscar", "papa", "quebec", "romeo", "sierra", "tango", "uniform",
		"victor", "whiskey", "xray", "yankee", "zulu"};
	static String[] a1 = {"alpha"};
	static String[] a2 = {"alpha", "bravo"};

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

	static String[] selectArray(Scanner sc)
	{
		String[] a = null;			// stupid compier demands this initialization
		int select;
		boolean selected = false;	// stupid compier demands this initialization
		do {
			System.out.print("enter 1 for a1, 2 for a2, 3 for a26: ");
			try {
				select = sc.nextInt();
			} catch (InputMismatchException e) {
				sc.nextLine();		// empty input buffer
				continue;
			}
			sc.nextLine();
			selected = true;
			switch (select) {
				case 1: a = a1; break;
				case 2: a = a2; break;
				case 3: a = a26; break;
				default: selected = false; break;
			}
		} while (! selected);
		return a;
	}

	public static void main(String[] args) {
		String[] a;
		String str;
		Scanner sc = new Scanner(System.in);
		
		a = selectArray(sc);
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
