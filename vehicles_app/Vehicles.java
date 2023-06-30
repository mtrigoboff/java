package vehicles_app;

import java.util.*;
import vehicles_app.vehicles.*;
import mlt.stdin;

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

        System.out.println("Vehicles 2.6");
    cmdLoop:
        for (;;) {
            System.out.print("> ");
            line = stdin.getLineWords();
            if (line == null)
                continue;
            cmd = line[0].toLowerCase();

            switch (cmd) {
            	case "count":
                    int nVehicles = items.size();
                    System.out.println(nVehicles + (nVehicles == 1 ? " item" : " items"));
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
                        items.put(key, v);
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
                        v.printTopSpeed();
                    else
                        System.out.println("command error: user typed \"" + line[1] + "\"");
            		break;
            	case "print":
                    if (line.length == 1)				// print all vehicles
                        for (Map.Entry<String,Vehicle> e : items.entrySet()) {
                            System.out.printf("name: %s%n", e.getKey());
                            e.getValue().printDescription();
                            System.out.println();
                            }
                    else if (line.length != 2) {
                        System.out.println("command requires name argument");
                        continue;
                        }
                    else {                              // "print <name>" command
                        v = items.get(line[1]);         // use name to get vehicle from HashMap
                        if (v != null)
                            v.printDescription();
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
        System.exit(0);
    }
}
