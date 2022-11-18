// implements a Divider that translates ints to floats,
// does the computation with floats,
// and converts the result back into an int

public class FloatingDivider
	implements Divider
{
	public int divide (int dividend, int divisor)
	{
		float	dd = dividend;
		float	dv = divisor;
		float	q = dd / dv;
		
		return (int) q;
	}
}
