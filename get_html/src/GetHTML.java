import java.net.*;
import java.io.*;

public class GetHTML
{
    private static String[]     cmdLineArgs = {"http://spot.pcc.edu/~mtrigobo", "25"};

    public static void main (String[] args)
        throws Exception
    {
        if (args == null || args.length == 0)
            args = cmdLineArgs;

        URL             url;
        int             nLines;
        URLConnection   connection;
        BufferedReader  in;
        String          inputLine;

        // create URL
        url = new URL(args[0]);

        // get nLines
        nLines = mlt.stdin.parseInteger(args[1]);
        
        // set up connection, input stream
        connection = url.openConnection();
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        // read HTML from web site
        for (int i = 0; i < nLines; i++) {
            inputLine = in.readLine();
            if (inputLine == null) 
                break;
            System.out.println(inputLine);
            }
        
        // close connection
        in.close();
    }
}
