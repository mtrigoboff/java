package vehicles_app.vehicles;

import mlt.stdin;

public class Car
    extends Vehicle
{
    private final static int        N_ENGINES = 1;          // standard number of engines

    int         weight;

    public Car(String[] args)
    {
    	super(args);

    	Integer		weight;
    	
        if (args.length != 4)
            throw new RuntimeException("new car requires name, weight");
        setNEngines(N_ENGINES);
        weight = stdin.parseInteger(args[3]);
        if (weight == null)
        	throw new RuntimeException("weight must be a number");
        else if (weight < 0)
        	throw new RuntimeException("weight must be a positive number");
        this.weight = weight;
    }
    
    public void printTopSpeed()
    {
        System.out.printf("car speed for %s: %.2f mph%n", name, 200_000.0 / weight);
    }

    public void printDescription()
    {
        System.out.println("car");
        System.out.println("    weight: " + weight);
        super.printDescription();
    }
}
