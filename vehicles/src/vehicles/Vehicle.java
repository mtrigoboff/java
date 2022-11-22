package vehicles;

abstract public class Vehicle {

    protected String name;
    private int nEngines;

    protected Vehicle(String[] args) {
	try {
	    name = args[2];
	} catch (ArrayIndexOutOfBoundsException e) {
	    throw new RuntimeException("new vehicle requires name");
	}
    }

    protected void setNEngines(String nEngStr) {
	Integer nEng;

	nEng = mlt.stdin.parseInteger(nEngStr);
	if (nEng == null || nEng < 0) {
	    throw new RuntimeException("number of engines must be a positive number");
	}
	nEngines = nEng;
    }

    protected void setNEngines(int nEng) {
	if (nEng < 0) {
	    throw new RuntimeException("number of engines must be a positive number");
	}
	nEngines = nEng;
    }

    abstract public String topSpeed();

    public String toString() {
	return String.format("    engines: %d%n", nEngines);
    }

    public String getName() {
	return name;
    }
}
