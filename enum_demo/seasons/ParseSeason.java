package seasons;

import mlt.stdin;

public class ParseSeason {

	// demonstrates checking whether user typed in a valid season name
	public static void main(String[] args) {
		String[] line;
		Season season;

		System.out.println("type a blank line to end execution");
		for (;;) {
			System.out.print("type a season: ");
			line = stdin.getLineWords();
			if (line == null || line.length == 0) {
				break;
			}
			try {
				season = Season.valueOf(line[0].toUpperCase());
				// use of toUpperCase to make program accept season names
				// no matter what case they are typed in
				// (SUMMER will be matched by "summer", "Summer", "SUMMER", etc.
				System.out.printf("\"%s\" is season %s%n", line[0], season);
			} catch (IllegalArgumentException e) {
				System.out.printf("\"%s\" is not a season%n", line[0]);
			}
		}
		System.out.println("bye...");
	}
}
