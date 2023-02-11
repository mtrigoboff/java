import java.util.ArrayList;

public class ArrayListBinarySearch {
	static ArrayList<Integer> ali = new ArrayList<>();

	static {
		for (int i = 0; i < 10; i++)
			ali.add(2 * i + 1);	
	}

	static boolean binarySearch(int n, int start, int end) {
		int half = start + (end - start) / 2;
		int val = ali.get(half);
		
		if (n == val)
			return true;
		else if (end <= start + 1)
			return false;
		else if (n < val)
			return binarySearch(n, start, half);
		else
			return binarySearch(n, half + 1, end);
	}
	
	static boolean binarySearch(int n) {
		return binarySearch(n, 0, ali.size());
	}
	
	public static void main(String[] args) {
		System.out.print("array: ");
		for (int i : ali)
			System.out.print(i + " ");
		System.out.println();

		int searchFor = 12;
		System.out.printf("search for %d returns %b%n", searchFor, binarySearch(searchFor));
	}
}
