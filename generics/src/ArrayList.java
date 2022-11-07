// stores items in an array of the generic type

import java.util.Iterator;

public class ArrayList<I>
	implements Iterable<I>
{
	private int		nItems;
	private I[]		items;
	
	@SuppressWarnings("unchecked")
	public ArrayList(int arraySize)
	{
		items = (I[]) new Object[arraySize];	// can't do this: items = new I[];		
		this.nItems = 0;
	}
	
	public boolean add(I item)
	{
		if (nItems < items.length) {
			items[nItems++] = item;
			return true;
			}
		else
			return false;
	}
	
	public I get(int index)
	{
		if (index < 0 || index >= nItems)
			throw new ArrayIndexOutOfBoundsException();
		return items[index];
	}
	
	public int nItems()
	{
		return nItems;
	}
	
	public String toString()
	{
		StringBuilder	sb = new StringBuilder();
		
		sb.append(String.format("ArrayList2 of %s:%n", items.getClass().getComponentType().getName()));
		for (I item : this)
			sb.append(String.format("%s%n", item));
		return sb.toString();
	}
	
	public Iterator<I> iterator()
	{
		return new Iterator<I>()
		{
			private int		index = 0;
			
			public boolean hasNext() { return index < nItems; }
			
			public I next() { return items[index++]; }
			
			public void remove() { throw new RuntimeException("ArrayList Iterator.remove() not implemented."); }
		};
	}
}
