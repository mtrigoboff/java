
import java.util.List;
import java.util.ArrayList;

public class SortList {

    private static List<Student> students = new ArrayList<>();

    public static void print() {
	for (Student student : students) {
	    System.out.println(student);
	}
    }

    public static void main(String[] args) {

	students.add(new Student("Bob", (float) 3.84));
	students.add(new Student("Deb", (float) 1.23));
	students.add(new Student("Ann", (float) 2.84));
	students.add(new Student("Jim", (float) 0.34));

	// using a lambda comparator to specify the sort order
	students.sort((s1, s2) -> Float.compare(s1.getGpa(), s2.getGpa()));

	print();
    }

}
