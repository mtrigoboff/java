import java.util.*;

public class ListDemo
{
	private static void listDemo(List<String> list)
	{
		list.add("abc");
		list.add("def");
		list.add("ghi");
		list.add(0, "start");
		
		System.out.println(list);
		
		list.set(3, "end");
		System.out.println(list);
		
		if (list.contains("abc"))
			System.out.println("list contains \"abc\"");
		System.out.println();
	}
	
	public static void main(String[] args)
	{
		System.out.println("ArrayList");
		listDemo(new ArrayList<String>());

		System.out.println("LinkedList");
		listDemo(new LinkedList<String>());		
	}
}
