import animals.Bird;
import animals.Fish;

public class Inheritance {

	public static void main(String[] args) {
		Bird eagle = new Bird("eagle", true);
		Bird penguin = new Bird("penguin", false);
		Fish shark = new Fish("shark", true);
		Fish goldfish = new Fish("goldfish", false);

		System.out.println(eagle);
		System.out.println(penguin);
		System.out.println(shark);
		System.out.println(goldfish);
	}

}
