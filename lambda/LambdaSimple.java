import java.util.Arrays;
import java.util.List;

public class LambdaSimple {

	public static void main(String[] args) {
		System.out.println("Lambda");

		int[] ar = {1, 2, 3};
		Arrays.stream(ar).forEach( i -> System.out.println(i + 1) );
		System.out.println();

		Integer[] ari = {4, 5, 6};
		List<Integer> arli = Arrays.asList(ari);
		arli.forEach( i -> System.out.println(i + 1) );
		System.out.println();

		String[] ars = {"abc", "def", "ghi"};
		List<String> arls = Arrays.asList(ars);
		arls.forEach( str -> System.out.println("\"" + str + "\"") );
		System.out.println();
	}
}
