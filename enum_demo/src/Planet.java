public enum Planet
{
    //       mass       radius
    MERCURY (3.303e+23, 2.4397e6),
    VENUS   (4.869e+24, 6.0518e6),
    EARTH   (5.976e+24, 6.37814e6)
    {
    	// Earth overrides toString() to add a touching comment
    	public String toString()
    	{
    		return String.format("%s(our home)", super.toString());
    	}
    },
    MARS    (6.421e+23, 3.3972e6),
    JUPITER (1.9e+27,   7.1492e7),
    SATURN  (5.688e+26, 6.0268e7),
    URANUS  (8.686e+25, 2.5559e7),
    NEPTUNE (1.024e+26, 2.4746e7),
    PLUTO   (1.27e+22,  1.137e6);		// demoted, but still on our list

    // universal gravitational constant  (meters^3 kg^-1 sec^-2)
    public static final double G = 6.67300E-11;

    private final double mass;   // in kilograms
    private final double radius; // in meters

    // this constructs instances of enum constants,
    // NOT instances of the entire enum
    private Planet(double mass, double radius)
    {
        this.mass = mass;
        this.radius = radius;
    }
    
    public double mass()   { return mass; }
    public double radius() { return radius; }

    public double surfaceGravity()
    {
        return G * mass / (radius * radius);
    }
    
    public double surfaceWeight(double mass)
    {
        return mass * surfaceGravity();
    }

    // override the method in java.lang.Enum,
    // return a word with first letter uppercase, the rest lowercase
    public String toString()
    {
        String  str = super.toString();
        
        return str.charAt(0) + str.substring(1).toLowerCase();
    }

    public static void main(String[] args)
    {
        double  earthWeight = (args != null && args.length > 0) ?
                                 Double.parseDouble(args[0]) :
                                 100;
        double  mass = earthWeight/EARTH.surfaceGravity();
        
        for (Planet p : Planet.values())
            System.out.printf(
                "%-36s%8.2f%n",
                "Your weight on " + p + " is: ",
                p.surfaceWeight(mass));
    }
}
