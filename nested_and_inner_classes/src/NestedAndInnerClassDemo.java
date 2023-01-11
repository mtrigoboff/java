public class NestedAndInnerClassDemo
{
	public static void main(String[] args)
	{
		Outer.Nested	nc = new Outer.Nested("nested");

		Outer			oc1 = new Outer("outer 1");
		Outer.Inner		ic1a = oc1.new Inner("inner 1a");
		Outer.Inner		ic1b = oc1.new Inner("inner 1b");

		Outer			oc2 = new Outer("outer 2");
		Outer.Inner		ic2 = oc2.new Inner("inner 2");
		
		class Local
		{
			String lcName;

			Local(String name)
			{
				lcName = name;
			}
			
			public String toString()
			{
				return String.format("Local{%s}", lcName);
			}
		}
		
		Local	lc = new Local("local");
		
		// anonymous classes
		
		Local	alc = new Local("anonymous local")
		{
			String		additionalInfo = "more information (alc)";
			
			public String toString()
			{
				return String.format("<anon alc>{%s, %s}", super.toString(), additionalInfo);
			}
		};

		Outer	olc = new Outer("anonymous outer")
		{
			String		additionalInfo = "more information (olc)";
			
			public String toString()
			{
				return String.format("<anon olc>{%s, %s}", super.toString(), additionalInfo);
			}
		};

		Outer.Inner	ilc = olc.new Inner("anonymous inner")
		{
			String		additionalInfo = "more information (ilc)";
			
			public String toString()
			{
				return String.format("<anon ilc>{%s, %s}", super.toString(), additionalInfo);
			}
		};

		System.out.println(nc);

		System.out.println(oc1);
		System.out.println(ic1a);
		System.out.println(ic1b);
		
		System.out.println(oc2);
		System.out.println(ic2);		

		System.out.println(lc);

		System.out.println(alc);
		System.out.println(olc);
		System.out.println(ilc);
	}
}
