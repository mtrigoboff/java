import java.util.Scanner;

public class ParseSeason {

	// demonstrates checking whether user typed in a valid season name
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			String line;
			Season season;

			System.out.println("ParseSeason");
			System.out.println("type a blank line to end execution");
			for (;;) {
				System.out.print("type a season: ");
				line = sc.nextLine();
				if (line == null || (line.length() == 0)) {
					break;
				}
				try {
					season = Season.valueOf(line.toUpperCase());
					// use of toUpperCase to make program accept season names
					// no matter how they are typed in:
					// SUMMER will be matched by "summer", "Summer", "SUMMER", etc.
					System.out.printf("\"%s\" is season %s%n", line, season);
				} catch (IllegalArgumentException e) {
					System.out.printf("\"%s\" is not a season%n", line);
				}
			}
		}
		System.out.println("bye...");
	}
}
