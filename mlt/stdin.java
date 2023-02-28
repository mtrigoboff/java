package mlt;

import java.util.Scanner;

/**
 * Defines static methods for getting user input from the console window, and
 * for parsing
 * numerical input.
 *
 * @version 3.2
 * @author Michael Trigoboff
 */
public class stdin {

	private static Scanner in = new Scanner(System.in);

	/**
	 * Attempts to parse input string into an Integer.
	 *
	 * @param str The string to be parsed.
	 * @return An Integer if successful, null otherwise.
	 */
	public static Integer parseInteger(String str) {
		try {
			return new Integer(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Attempts to parse input string into a Double.
	 *
	 * @param str The string to be parsed.
	 * @return A Double if successful, null otherwise.
	 */
	public static Double parseDouble(String str) {
		try {
			return new Double(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Returns line of typed input as an array of Strings. Each String in the array
	 * contains one word of the input.
	 *
	 * @return A String[], or null if the line is empty.
	 */
	public static String[] getLineWords() {
		String line = getLine();
		String[] words;

		if (line.length() == 0) {
			return null;
		}
		words = line.trim().split("\\s+");
		// need to trim() before split() because otherwise
		// leading spaces cause words[0] to be ""
		return words.length == 0 || (words.length == 1 && words[0].equals(""))
				? // can get "" as only element of array if user types in only spaces
				null
				: words;
	}

	/**
	 * Returns line of typed input as a string.
	 *
	 * @return A String. If the line is empty, the String will be zero-length.
	 */
	public static String getLine() {
		in.hasNextLine();
		return in.nextLine();
	}

	/**
	 * Private default constructor prevents creation of instances of this class.
	 */
	private stdin() {
	}
}
