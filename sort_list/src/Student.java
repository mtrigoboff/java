public class Student {
    private String  name;
    private float   gpa;
    
    public Student(String name, float gpa) {
	this.name = name;
	this.gpa = gpa;
    }
    
    public String getName() {
	return name;
    }

    public float getGpa() {
	return gpa;
    }
    
    public String toString() {
	return String.format("%s, %.2f", name, gpa);
    }
}
