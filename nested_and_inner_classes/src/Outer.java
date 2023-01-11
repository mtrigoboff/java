public class Outer
{
	private String	ocName;

	static class Nested
	{
		private String	ncName;

		Nested(String name)
		{
			ncName = name;
		}
		
		public String toString()
		{
			return String.format("Nested{%s}", ncName);
		}
	}
	
	public class Inner
	{
		private String	icName;		// must not be same as name in outer class

		Inner(String name)
		{
			icName = name;
		}
		
		String associatedOuterClassName ()
		{
			return ocName;
		}

		public String toString()
		{
			return String.format("Inner{%s} -> Outer{%s}", icName, ocName);
		}
	}
		
	Outer(String name)
	{
		this.ocName = name;
	}
	
	public String toString()
	{
		return String.format("Outer{%s}", ocName);
	}
}
