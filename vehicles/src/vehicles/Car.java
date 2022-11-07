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
        setNEngines(N_ENGINES);
        weight = mlt.stdin.parseInteger(args[3]);
        if (weight == null)
        	throw new RuntimeException("weight must be a number");
        else if (weight < 0)
        	throw new RuntimeException("weight must be a positive number");
        this.weight = weight;
    }
    
    public String topSpeed()
    {
        return String.format("car speed for %s: %.2f mph%n", name, 200_000.0 / weight);
    }

    public String toString()
    {
        return String.format("car%n    weight: %d%n", weight) + super.toString();
   }
}
