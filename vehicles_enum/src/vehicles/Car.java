package vehicles;

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
        nEngines = N_ENGINES;
        weight = mlt.stdin.parseInteger(args[3]);
        if (weight == null)
        	throw new RuntimeException("weight must be a number");
        else if (weight < 0)
        	throw new RuntimeException("weight must be a positive number");
        this.weight = weight;
    }
    
    public String topSpeedStr()
    {
        return String.format("car speed for %s: %.2f mph%n", name, 200000.0 / weight);
    }

    public String toString()
    {
    	StringBuilder	str = new StringBuilder();
    	
        str.append("car" + String.format("%n"));
        str.append("    weight: " + weight + String.format("%n"));
        str.append(super.toString());
        
        return str.toString();
    }
}
