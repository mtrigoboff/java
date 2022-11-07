// this version uses a name for each vehicle instead of a numerical index

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import vehicles.Vehicle;

class Vehicles
{
    public static void main(String[] args)
    {
    	PrintStream		out = System.out;
        String[]        line;
        Command			command;
        boolean			stop;
        
    	Map<String,Vehicle>		items = new HashMap<>();

        out.println("Vehicles 4.0");
        for (;;) {
            out.print("> ");
            line = mlt.stdin.getLineWords();
            if (line == null)
                continue;

            command = Command.parse(line[0]);
            if (command != null)
            	try { stop = command.doCommand(out, line, items); }
            	catch (RuntimeException e) { out.println(e.getMessage()); continue; }
            else {
                out.printf("command error: user typed \"%s\"%n", line[0]);
                continue;
            	}
            if (stop)
            	break;
        	}
    }
}