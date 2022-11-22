// demonstrates encapsulation, data hiding, data protection

public class Temperature {

    public enum TemperatureType {
	FAHRENHEIT("F"), CELSIUS("C"), KELVIN("K");
	private String unitsStr;

	private TemperatureType(String unitsStr) {
	    this.unitsStr = unitsStr;
	}

	public String getUnitsStr() {
	    return unitsStr;
	}
    }

    // ------------------------------------------------------------
    private static double celsiusToFahrenheit(double degreesC) {
	return 9 * degreesC / 5 + 32;
    }

    private static double celsiusToKelvin(double degreesC) {
	return degreesC + 273.15;
    }

    private static double fahrenheitToCelsius(double degreesF) {
	return 5 * (degreesF - 32) / 9;
    }

    private static double kelvinToCelsius(double degreesK) {
	return degreesK - 273.15;
    }

    private static String formatTemperature(double degrees, TemperatureType type) {
	return String.format("%.1f degrees %s", degrees, type.getUnitsStr());
    }

    public static double convert(TemperatureType type, double degrees, TemperatureType convertType) {
	return new Temperature(type, degrees).getDegrees(convertType);
    }

    // ------------------------------------------------------------
    private double degreesC;
    private double degreesF;
    private double degreesK;

    public Temperature(TemperatureType type, double degrees) {
	setDegrees(type, degrees);
    }

    public double getDegrees(TemperatureType type) {
	switch (type) {
	    case CELSIUS:
		return degreesC;
	    case FAHRENHEIT:
		return degreesF;
	    case KELVIN:
		return degreesK;
	    default:
		throw new RuntimeException("Temperature: illegal type");
	}
    }

    public void setDegrees(TemperatureType type, double degrees) {
	switch (type) {
	    case CELSIUS:
		this.degreesC = degrees;
		degreesF = celsiusToFahrenheit(degreesC);
		degreesK = celsiusToKelvin(degreesC);
		break;
	    case FAHRENHEIT:
		this.degreesF = degrees;
		degreesC = fahrenheitToCelsius(degreesF);
		degreesK = celsiusToKelvin(degreesC);
		break;
	    case KELVIN:
		this.degreesK = degrees;
		degreesC = kelvinToCelsius(degreesK);
		degreesF = celsiusToFahrenheit(degreesC);
		break;
	    default:
		throw new RuntimeException("Temperature: illegal type");
	}
    }

    public String toString() {
	StringBuilder strb = new StringBuilder();
	boolean first = true;

	strb.append("[");
	for (TemperatureType type : TemperatureType.values()) {
	    strb.append(first ? "" : ", ");
	    strb.append(String.format("%7s", formatTemperature(getDegrees(type), type)));
	    if (first) {
		first = false;
	    }
	}
	strb.append("]");

	return strb.toString();
    }

    // ------------------------------------------------------------
    public static String conversionStr(TemperatureType type, double degrees, TemperatureType convertType) {
	return String.format("%8s == %8s",
		formatTemperature(degrees, type),
		formatTemperature(convert(type, degrees, convertType), convertType));
    }

    public static void main(String[] args) {
	System.out.println(new Temperature(TemperatureType.CELSIUS, 100));
	System.out.println(new Temperature(TemperatureType.FAHRENHEIT, 32));

	System.out.println(conversionStr(TemperatureType.FAHRENHEIT, 77, TemperatureType.CELSIUS));
	System.out.println(conversionStr(TemperatureType.KELVIN, 0, TemperatureType.FAHRENHEIT));
    }
}
