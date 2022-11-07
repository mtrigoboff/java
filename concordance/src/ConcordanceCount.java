import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;

public class ConcordanceCount
{
	private static class Count
	{
		int		n = 1;
	}

	public static void main(String[] args)
	{
		String[]    line;
		String		word;
		Count		count;
		
		SortedMap<String,Count>   map = new TreeMap<>();
		
	words:
		for (;;) {
			System.out.print("> ");
			line = mlt.stdin.getLineWords();
			if (line == null)
				continue;
			for (int i = 0; i < line.length; i++) {
				word = line[i];
				if (word.equals("---"))
					break words;
				count = map.get(word);
				if (count == null)
					map.put(word, new Count());
				else
					count.n++;
				}
			}
		
		System.out.println();
		for (Map.Entry<String,Count> entry : map.entrySet())
			System.out.printf("%-10s%7d%n", entry.getKey() + ":", entry.getValue().n);
		
		System.out.printf("%ndone...%n");
	}
}
