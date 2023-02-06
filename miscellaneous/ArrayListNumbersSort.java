// sorting an array of Number

import java.util.ArrayList;

public class ArrayListNumbersSort {

	public static void main (String[] args) {
		double[] ard = {1.2, 5.7, 3.6};
		int[] ari = {3, 2, 1};
		ArrayList<Number> arn = new ArrayList<>();

		for (int i : ari)
			arn.add(i);
		for (double d : ard)
			arn.add(d);
	
		arn.sort((Number n1, Number n2) ->
			{
				if (n1 instanceof Double && n2 instanceof Double)
					return ((Double) n1).compareTo((Double) n2);
				else if (n1 instanceof Integer && n2 instanceof Integer)
					return ((Integer) n1).compareTo((Integer) n2);
				else if (n1 instanceof Integer)
					return Double.compare(((double) ((Integer) n1.intValue())), ((Double) n2).doubleValue());
				else // (n1 instanceof Double)
					return Double.compare(((Double) n1).doubleValue(), ((double) ((Integer) n2.intValue())));
			});
		
		for (Number n : arn)
			System.out.println(n);
	}
	
}
