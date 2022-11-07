package vehicles;

abstract public class Vehicle
{
    String      name;
    int         nEngines;

    protected Vehicle(String[] args)
    {
    	try {
    		name = args[2];
    		}
        catch (ArrayIndexOutOfBoundsException e) {
        	throw new RuntimeException("new vehicle requires name");
        	}
    }
    
    protected void setNEngines(String nEngStr)
    {
    	Integer		nEng;
    	
        nEng = mlt.stdin.parseInteger(nEngStr);
        if (nEng == null || nEng < 0)
            throw new RuntimeException("number of engines must be a positive number");
        nEngines = nEng;
    }

    abstract public String topSpeedStr();

    public String toString()
    {
        return "    engines: " + nEngines + String.format("%n");
    }

    public  String getName()
    {
        return name;
    }
}
