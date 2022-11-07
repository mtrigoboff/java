
/**
 * The Student class demonstrates the creation of a simple class. 
 * Please pay attention to the following usages:
 * 
 * no header file
 * private/public, final, static modifiers
 * the constructors
 * toString method
 * equals method
 * static variable/methods vs instance variable/methods
 * Comparable interface
 * override hashCode method
 * 
 * @author Li Liang 
 * @version version 1.1
 */
public class Student implements Comparable <Student>
{
    private String id;
    private double gpa;
    public final static int MAX_CREDIT = 100;
    private static int numStudentsCreated = 0;

    /**
     * Returns the number of student objects created so far.
     * @return  The number of student objects created
     */
    public static int getNumberOfStudents()
    {
        return numStudentsCreated;
    }
    
    /**
     * Initializes a student object so that the id contains null and gpa contains 0.
     */
    public Student()
    {
        id = null;
        gpa = 0;
        numStudentsCreated++;
    }
    
    /**
     * Initializes a student object with the passed id and gpa.
     */
    public Student(String id, double gpa)
    {
        this.id = id;
        this.gpa = gpa;
        numStudentsCreated++;
    }

    /**
     * Returns the student id.
     * @return     student id
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * Returns the student gpa.
     * @return     student gpa
     */
    public double getGpa()
    {
        return gpa;
    }
    
    /**
     * Sets the student gpa to the passed in value.
     * @param   gpa - the new gpa
     */
    public void setGpa(double gpa)
    {
        this.gpa = gpa;
    }
    
    /**
     * Returns a string representation of the student object.
     * @return  a string that describes the student object
     */
    public String toString()
    {
        return id + '\t' + gpa;
    }
    
    /**
     * Returns true if the two Student objects have the same id.
     * @return true if the two Student objects have the same id.
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Student)
            return id.equals(((Student)obj).id);
        else
            return false;
    }
    
    /**
     * Returns the hash code for the current student object.
     * @return the hash code
     */
    public int hashCode()
    {
        return id.hashCode();
    }
   
    /**
     * Compares this object with the specified object for order.
     * @return 0 if this object has the same order as the specifed object.
     * >0 if this object has a higher order than the specifed object.
     * <0 if this object has a lower order than the specifed object.
     */
    public int compareTo(Student s)
    {
        return id.compareTo(s.id);
    }
}
     
