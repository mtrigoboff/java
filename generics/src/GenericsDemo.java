public class GenericsDemo
{
	public static <N extends Number> double sum(ArrayList<N> a)
	{
		double	sum = 0;
		
		for (N number : a)
			sum += number.doubleValue();
		return sum;
	}
	
	public static void main(String[] args)
	{
		{
		ArrayList<Double>	doubles = new ArrayList<>(4);
				
		doubles.add(1.414);
		doubles.add(3.14);
		doubles.add(6.02e23);
		//doubles.add(1);				// can't add an int
		
		System.out.println(doubles);
		}
	
		{
		ArrayList<String>	strings = new ArrayList<>(4);
		
		strings.add("abc");
		strings.add("def");
		strings.add("ghi");
		
		System.out.println(strings);
		for (String str : strings)
			System.out.println(str);
		}
		
		{
		//ArrayList<Number>				nums = new ArrayList<Integer>(4);
		// ArrayList<Integer> is not a subtype of ArrayList<Number>

		ArrayList<Integer>				integers = new ArrayList<>(4);
		ArrayList<? extends Number>		numbers = integers;
		
		//numbers.add(1);				// compiler doesn't know datatype contained by list -
										// what if the capture of <? extends Number> is Double?
		integers.add(1);
		integers.add(2);
		integers.add(3);
		
		System.out.println(numbers);
		
		System.out.println(sum(numbers));
		}
	}
}
