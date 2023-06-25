package vehicles_app.vehicles;

import mlt.stdin;

public class Plane
    extends Vehicle
{
    int     wingSpan;

    public Plane(String[] args)
    {
    	super(args);
    	
        Integer     wSpan;

        if (args.length != 5)
        	throw new RuntimeException("new plane requires name, nEngines, wingSpan");
        setNEngines(args[3]);
        wSpan = stdin.parseInteger(args[4]);
        if (wSpan == null)
        	throw new RuntimeException("wingspan must be a number");
        else if (wSpan <= 0)
        	throw new RuntimeException("wingSpan must be a positive number");
        wingSpan = wSpan;
    }

    public void printTopSpeed()
    {
        System.out.printf("plane speed for %s: %.2f mph%n", name, wingSpan * 4.5);
    }

    public void printDescription()
    {
        System.out.println("plane");
        System.out.println("    wing span: " + wingSpan);
        super.printDescription();
    }
}
