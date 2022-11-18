public enum Operation
{
    // do arithmetic op represented by this constant
    PLUS    ("+")   { double eval(double x, double y) { return x + y; } },
    MINUS   ("-")   { double eval(double x, double y) { return x - y; } },
    TIMES   ("*")   { double eval(double x, double y) { return x * y; } },
    DIVIDE  ("/")   { double eval(double x, double y) { return x / y; } };
    
    final String  opStr;
    
    // require every member of this enum to implement eval()
    abstract double eval(double x, double y);
    
    // enum constructors must be declared private
    private Operation(String opStr)
    {
        this.opStr = opStr;
    }

    public static void main(String args[])
    {
        double  x = 6.0;
        double  y = 3.0;
        
        if (args.length >= 2) {
            try {
	            x = Double.parseDouble(args[0]);
	            y = Double.parseDouble(args[1]);
            	}
            catch (NumberFormatException e) {
            	System.out.println("Argument to main() was not a number.");
            	return;
            	}
            }
        
        for (Operation op : Operation.values())
            System.out.printf("%5.1f %s %-5.1f = %5.1f%n", x, op.opStr, y, op.eval(x, y));
    }
}