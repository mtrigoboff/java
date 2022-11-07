import java.util.*;
import vehicles.*;

// this version uses a name for each vehicle instead of a numerical index

class Vehicles
{
    static String[]     help = {        // command line help
        "      \"new\" command           (create new vehicle)",
        "          new car <name> <weight>",
        "          new boat <name> <nEngines> <length>",
        "          new plane <name> <nEngines> <wingspan>",
        "",
        "      speed <name>            (print speed of vehicle)",
        "",
        "      print <name>            (print details of vehicle)",
        "",
        "      print                   (print all the vehicles)",
        "",
        "      count                   (print number of vehicles)",
        "",
        "      exit                    (exit the application)",
        ""
        };

    static Vehicle newVehicle(String[] args)
    {
    	Vehicle		v;

        if (args[1].equalsIgnoreCase("car"))
            v = new Car(args);
        else if (args[1].equalsIgnoreCase("boat"))
            v = new Boat(args);
        else if (args[1].equalsIgnoreCase("plane"))
            v = new Plane(args);
        else
            throw new RuntimeException("command error: user typed \"" + args[1] + "\"");

        return v;
    }

    public static void main(String[] args)
    {
        Map<String,Vehicle>     items = new TreeMap<>();
        String[]                line;
        String					cmd;
        Vehicle                 v;
        String                  key;

        System.out.println("Vehicles 2.7");
    cmdLoop:
        for (;;) {
            System.out.print("> ");
            line = mlt.stdin.getLineWords();
            if (line == null)
                continue;
            cmd = line[0].toLowerCase();

            switch (cmd) {
            	case "count":
                    System.out.println(items.size() + " items");
            		break;
            	case "help":
                    for (String h : help)
                        System.out.println(h);
            		break;
            	case "new":
                    if (line.length < 2) {
                        System.out.println("new command requires at least one argument");
                        continue;
                        }
                    try {
    	                v = newVehicle(line);
                    	}
    	            catch (RuntimeException e) {
    	            	System.out.println(e.getMessage());
    	            	continue;
    	            	}
                	key = v.getName();
                    if (items.containsKey(key))
                		System.out.println("there is already a vehicle named \"" + key + "\"");
                	else {
                        items.put(key, v);              // add new vehicle to HashMap
                        System.out.println("item added");
                		}
            		break;
            	case "speed":
                    if (line.length != 2) {
                        System.out.println("command requires name argument");
                        continue;
                        }
                    v = items.get(line[1]);             // use name to get vehicle from HashMap
                    if (v != null)
                        System.out.println(v.topSpeed());
                    else
                        System.out.println("command error: user typed \"" + line[1] + "\"");
            		break;
            	case "print":
                    if (line.length == 1)				// print all vehicles
                        for (Map.Entry<String,Vehicle> e : items.entrySet()) {
                            System.out.printf("name: %s%n", e.getKey());
                            System.out.println(e.getValue());
                            }
                    else if (line.length != 2)
                        System.out.println("command requires name argument");
                    else {                              // "print <name>" command
                        v = items.get(line[1]);         // use name to get vehicle from HashMap
                        if (v != null)
                            System.out.println(v);
                        else
                            System.out.println("command error: user typed \"" + line[1] + "\"");
                        }
            		break;
            	case "exit":
            		break cmdLoop;
        		default:
                    System.out.println("command error: user typed \"" + line[0] + "\"");
            	}
            }

        System.out.printf("exiting...%n%n");
    }
}
