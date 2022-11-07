import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import vehicles.Vehicle;
import vehicles.VehicleType;

public enum Command {			// defines commands available in the user interface

	COUNT							// print number of vehicles stored
	    (new String[]{"count", "print number of vehicles stored"},
	     null,
	     null)
	{
		public boolean doCommand(PrintStream out, String[] line, Map<String,Vehicle> items) {
			int		nItems = items.size();
			
            out.printf("%d vehicle%s%n", nItems, (nItems == 1 ? "" : "s"));
			return false;
		}
	},

	EXIT							// stop running
	    (new String[]{"exit", "exit the application",},
		 null,
		 new String[]{"QUIT", "BYE"})
	{
		public boolean doCommand(PrintStream out, String[] line, Map<String,Vehicle> items) {
			out.println("exiting...");
			return true;
		}
	},
		
	HELP							// print help information
	    (new String[]{"help", "print descriptions of the available commands"},
		 null,
		 new String[]{"?"})
	{
		public boolean doCommand(PrintStream out, String[] line, Map<String,Vehicle> items) {
			out.println();
			for (Command cmd : Command.values()) {
				boolean		firstAlias = true;
				out.printf("    %-20s%s%n", cmd.description[0], cmd.description[1]);
				if (cmd.variants != null)
					for (int i = 0; i < cmd.variants.length; i += 2)
						out.printf("        %s%s%n", cmd.variants[i], cmd.variants[i + 1]);
				if (cmd.aliases != null) {
					out.print(cmd.aliases.length == 1
							  ? "      alias: "
							  : "      aliases: ");
					for (String alias : cmd.aliases) {
						out.printf("%s%s", firstAlias ? "" : ", ", alias.toLowerCase());
						firstAlias = false;
						}
					out.println();
					}
				out.println();
				}
			return false;
		}
	},
	
	NEW							// add a new vehicle
	    (new String[]{"new", "create new vehicle"},
	     new String[]{
		        "new car <name> <weight>", "",
		        "new boat <name> <nEngines> <length>", "",
		        "new plane <name> <nEngines> <wingspan>", ""
	    		},
	     new String[]{"MAKE"})
	{
		public boolean doCommand(PrintStream out, String[] line, Map<String,Vehicle> items) {
	        Vehicle		v;
	        String		key;

	        if (line.length < 2)
	        	throw new RuntimeException(String.format("vehicle type must be specified"));
	        try {
	        	VehicleType vt = VehicleType.valueOf(line[1].toUpperCase());
	        	v = vt.newInstance(line);
	        } catch (IllegalArgumentException e) {
	        	throw new RuntimeException(String.format("\"%s\" is not a vehicle", line[1]));
	        }
            key = v.getName();
            if (items.containsKey(key))
        		throw new RuntimeException(String.format("there is already a vehicle named \"%s\"", key));
            items.put(key, v);              // add new vehicle to Map
            out.println("item added");
			return false;
		}
	},

	PRINT							// print information for a vehicle or for all vehicles
	    (new String[]{"print", "print vehicle(s)"},
		 new String[]{
	    		"print <name>        ", "print named vehicle",
	    		"print               ", "print all the vehicles"
	    		},
	     null)
	{
		public boolean doCommand(PrintStream out, String[] line, Map<String,Vehicle> items) {
	        Vehicle		v;

	        if (line.length == 1)                   // print all vehicles
                for (Map.Entry<String,Vehicle> e : items.entrySet()) {
                    out.printf("name: %s%n", e.getKey());
                    out.print(e.getValue());
                    out.println();
                    }
            else if (line.length != 2)
            	throw new RuntimeException("vehicle name required");
            else {                              // "print <name>" command
                v = items.get(line[1]);         // use name to get vehicle from Map
                if (v != null)
                	out.print(v);
                else
	                throw new RuntimeException("there is no vehicle named \"" + line[1] + "\"");
                }
			return false;
		}
	},

	SPEED							// request speed of a vehicle
	    (new String[]{"speed <name>", "print speed of named vehicle"},
		 null,
		 new String[] {"VELOCITY"})
	{
	    public boolean doCommand(PrintStream out, String[] line, Map<String,Vehicle> items) {
	        Vehicle		v;

	        if (line.length != 2)
	        	throw new RuntimeException("vehicle name required");
            v = items.get(line[1]);             // use name to get vehicle from Map
            if (v != null)
                out.print(v.topSpeedStr());
            else
                throw new RuntimeException("there is no vehicle named \"" + line[1] + "\"");
			return false;
		}
	}

	;		// end of enum members, start of non-member-specific code
	
	private static HashMap<String,Command> aliasMap = new HashMap<>();
	
	// set up command aliases: this is run when the Command enum .class file is loaded,
	//						   the first time that a command is invoked by the user
	static
	{
		String cmdName;

		for (Command cmd : Command.values()) {
			cmdName = cmd.toString();
			if (cmdName.length() > 1)
				aliasMap.put(cmdName.substring(0, 1), cmd);
			if (cmd.aliases != null)
				for (String alias : cmd.aliases) {
					aliasMap.put(alias, cmd);
					if (alias.length() != 1)
						aliasMap.put(alias.substring(0, 1), cmd);
					}
			}
	}
	
	private String[] description;	// help prints this description of the command
	private String[] variants;		// help prints the variants of the command
	private String[] aliases;		// alternate names that can be typed in for the command
	
	Command(String[] description, String[] variants, String[] aliases)
	{
		// error checking required for help command to work correctly
		if (description != null && description.length != 2)
			throw new RuntimeException("Command.<init>: description length must be 2");
		if (variants != null && variants.length % 2 != 0)
			throw new RuntimeException("Command.<init>: variants length must be multiple of 2");

		this.description = description;
		this.variants = variants;
		this.aliases = aliases;
	}

	// performs command, returns true if application should stop afterwards
	abstract public boolean doCommand(PrintStream out, String[] line, Map<String,Vehicle> items);
	
	public static Command parse(String str) {
		str = str.toUpperCase();
		try {
			return Command.valueOf(str);
		} catch (IllegalArgumentException e) {
			return aliasMap.get(str);
		} catch (NullPointerException e) {
			return null;
		}
	}
}
