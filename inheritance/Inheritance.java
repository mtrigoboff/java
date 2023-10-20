import animals.Animal;
import animals.Bird;
import animals.Fish;

public class Inheritance {

	public static void main(String[] args) {
		Animal eagle = new Bird("eagle", true);
		Animal penguin = new Bird("penguin", false);
		Animal shark = new Fish("shark", true);
		Animal goldfish = new Fish("goldfish", false);

		System.out.println(eagle);
		System.out.println(penguin);
		System.out.println(shark);
		System.out.println(goldfish);
	}

}
