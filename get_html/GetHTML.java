
import java.net.*;
import java.io.*;

public class GetHTML {

	private static String[] cmdLineArgs = { "http://spot.pcc.edu/~mtrigobo", "25" };

	public static void main(String[] args)
			throws Exception {
		if (args == null || args.length == 0)
			args = cmdLineArgs;

		URL url;
		int nLines;
		URLConnection connection;
		BufferedReader in;
		String inputLine;

		// get nLines
		nLines = mlt.stdin.parseInteger(args[1]);

		// print args
		System.out.printf("printing first %d lines from %s%n---%n", nLines, args[0]);

		// set up connection, input stream
		url = new URL(args[0]);
		connection = url.openConnection();
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		// read HTML from web site
		for (int i = 0; i < nLines; i++) {
			inputLine = in.readLine();
			if (inputLine == null) {
				break;
			}
			System.out.println(inputLine);
		}

		// close connection
		in.close();
	}
}
