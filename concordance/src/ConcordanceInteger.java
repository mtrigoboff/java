import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;

public class ConcordanceInteger
{
	public static void main(String[] args)
	{
		String[]    line;
		String		word;
		Integer		count;
		
		SortedMap<String,Integer>   map = new TreeMap<>();
				
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
					map.put(word, new Integer(1));
				else
					map.put(word, new Integer(count.intValue() + 1));
				}
			}
		
		System.out.println();
		for (Map.Entry<String,Integer> entry : map.entrySet())
			System.out.printf("%-10s%7d%n", entry.getKey() + ":", entry.getValue().intValue());
		
		System.out.printf("%ndone...%n");
	}
}
