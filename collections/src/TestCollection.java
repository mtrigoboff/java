
/**
 * Demonstrates the usage for core collection interfaces/classes.
 * 
 * @author (Li Liang) 
 * @version (1.0)
 */
import java.util.*;

public class TestCollection
{
    private static final Student[] students = {
        new Student("G10", 4.0),
        new Student("G20", 3.8),
        new Student("G30", 3.9),
        new Student("G40", 3.7)};
        
    public static void main(String [] args)
    {
       testList();
       testMap();
       testSortedMap();
       testMultipleMap();
       testMapOfMap();
       testAlgorithms();
    }
    
    public static void testAlgorithms()
    {
        List<Student> studentList = Arrays.asList(students);
        System.out.println("\nThe original list: ");
        displayStudents(studentList);
       
        Collections.shuffle(studentList);
        System.out.println("\nThe shuffled list: ");
        displayStudents(studentList);
        
        Collections.sort(studentList);
        System.out.println("\nSorted by natural order: ");
        displayStudents(studentList);
        
        Collections.sort(studentList, new Comparator<Student>()
            {
                public int compare(Student s1, Student s2)
                {
                    if(s1.getGpa() < s2.getGpa())
                        return -1;
                    else if(s1.getGpa() > s2.getGpa())
                        return 1;
                    return 0;
                }
            });
        System.out.println("\nSorted by gpa: ");
        displayStudents(studentList);
        
    }
    public static void testMapOfMap()
    {
        //a enrollment map which maps each campus to its enrollment in different program
        Map<String, Map<String, Integer>> enrollment =
            new HashMap<String, Map<String, Integer>>();
        
        //enroll a student in Sylvania CS program
        enrollStudent(students[0], "SYL", "CS", enrollment);
        enrollStudent(students[1], "SYL", "CIS", enrollment);
        enrollStudent(students[2], "RC", "CAS", enrollment);
        enrollStudent(students[3], "SYL", "CS", enrollment);
        
        //run some query
        System.out.println("\nEnrollment number for Sylvania CS program: "
        + enrollment.get("SYL").get("CS"));
        
        //display the enrollment sheet
        System.out.println("\nEnrollment report: ");
        Set<Map.Entry<String, Map<String, Integer>>> enrollSet
            = enrollment.entrySet();
        for(Map.Entry<String, Map<String, Integer>> e: enrollSet)
        {
            System.out.println(e);
        }
    }
    
    private static void enrollStudent
    (Student s, String campus, String program, Map<String, Map<String,Integer>> sheet)
    {
        Map<String, Integer> curCampus = sheet.get(campus);
        
        //the campus doesn't exist, create entry for the campus
        if(curCampus == null)
        {
            curCampus = new HashMap<String, Integer>();
            sheet.put(campus, curCampus);
        }
        
        //the program doesn't exist on the campus, create entry for the program
        Integer curProgram = curCampus.get(program);
        if(curProgram == null)
        {
            curProgram = 0;
            curCampus.put(program, curProgram);
        }
        curCampus.put(program, curProgram+1);
    }            
        
    public static void testMultipleMap()
    {
        //a roster which map each class name to its students
        Map<String, Set<Student>> roster = new HashMap<String, Set<Student>>();
        
        //add the first half of the array to cs160 
        //and the second half of the array to cs261
        int i;
        int sizeClass = students.length / 2;
        for(i=0; i<sizeClass; i++)
        {
            addStudentToClass(students[i], "cs160", roster);
        }
        
        for(i=sizeClass; i<students.length; i++)
        {
            addStudentToClass(students[i], "cs261", roster);
        }
        
        //display students in cs160 and cs261
        System.out.println("\nCS160 students: ");
        System.out.println(roster.get("cs160"));
        System.out.println("\nCS261 students: ");
        System.out.println(roster.get("cs261"));
        
    }
    
    private static void addStudentToClass
    (Student s, String className, Map<String, Set<Student>> classes)
    {
        Set<Student> classToAdd = classes.get(className);
        if(classToAdd == null)
        {
            classToAdd = new HashSet<Student>();
            classes.put(className, classToAdd);
        }
        classToAdd.add(s);
    }

    public static void testSortedMap()
    {
        //populate a sorted map
        SortedMap<String, Student> cs261 = new TreeMap<String, Student>();
        for(Student s: students)
            cs261.put(s.getId(), s);
            
        //iterate through the sorted map
        Set<Map.Entry<String, Student>> studentSet = cs261.entrySet();
        System.out.println("\nThe sorted student map: ");
        for(Map.Entry<String, Student> e: studentSet)
            System.out.println(e); 
    }
    
    public static void testMap()
    {
        //populate a map, put 
        Map<String, Student> cs261 = new HashMap<String, Student>();
        for(Student s: students)
        {
            cs261.put(s.getId(), s);
        }
        
        //retrive by key, get
        System.out.println("\nStudent with ID G20: "
        + cs261.get("G20"));
        
        //remove by key
        cs261.remove("G20");
        System.out.println("\nStudent with ID G20 after removal: "
        + cs261.get("G20"));
        
        //iterate through the map, map to collection
        Set<Map.Entry<String, Student>> studentSet =
            cs261.entrySet();
        System.out.println("\nStudents in the map: ");
        for(Map.Entry<String, Student> e: studentSet)
            System.out.println(e);
        System.out.println("\nStudents in the map with customized display: ");
        for(Map.Entry<String, Student> e: studentSet)
            System.out.println(e.getKey() + "->" + e.getValue());
        
        Set<String> keys = cs261.keySet();
        System.out.println("\nKeys in cs261: ");
        for(String k: keys)
            System.out.println(k);
        Collection<Student> vals = cs261.values();
        System.out.println("\nValues in cs261: ");
        for(Student v: vals)
            System.out.println(v);          
        
    }
    
    public static void testList()
    {
        //populate the list
        List<Student> studentList = new ArrayList<Student>();
        for(Student s: students)
            studentList.add(s);     
        List<Student> studentListBackup = new ArrayList<Student>(studentList);
        
        //display the list
        System.out.println("\nStudentList: ");
        displayStudents(studentList);
        
        //filter the list using the iterator
        Iterator<Student> listIt = studentList.iterator();
        while(listIt.hasNext())
        {
            if(listIt.next().getGpa() < 3.9)
                listIt.remove();
        }
        System.out.println("\nStudentList with high GPA: ");
        displayStudents(studentList);
        
        //search the list, use equals to compare, see api doc
        System.out.println("\nIndex of student with ID G10: "
        + studentList.indexOf(new Student("G10", 4.0)));
        
        //access element by position
        System.out.println("\nThe last student in the studentList: "
        + studentList.get(studentList.size()-1));
        
        //bulk operations
        Set<Student> studentSet = new LinkedHashSet<Student>();
        studentSet.add(new Student("G60", 3.5));
        studentSet.add(new Student("G80", 3.6));
        studentList.addAll(1, studentSet);
        System.out.println("\nThe bigger studentList: ");
        displayStudents(studentList);
        studentList.removeAll(studentSet);
        System.out.println("\nBack to the high Gpa studentList: ");
        displayStudents(studentList);
        
        studentList.clear();
        System.out.println("\nNow the empty list: ");
        displayStudents(studentList);
        
        //sublist
        studentList = studentListBackup;
        System.out.println("\nStart over with studentList: ");
        displayStudents(studentList);
        List<Student> aSubList = studentList.subList(1, 3);
        System.out.println("\nstudentList [1, 3]: ");
        displayStudents(aSubList);
        aSubList.clear();
        System.out.println("\nstudentList after the clear operation for sublist: ");
        displayStudents(studentList);       
        
        //List to array and array to List
        List<Student> studentList2 = Arrays.asList(students);
        studentList2.set(1, new Student("G100", 2.0));
        System.out.println("\nA changed studentList2: ");
        displayStudents(studentList2);
        
        System.out.println("\nThe original array: ");
        for(Student s: students)
            System.out.println(s);
            
        //the asList operation returns a fixed size list
        //studentList2.add(new Student("G200", 2.4));
        
        Student [] studentArray = studentList2.toArray(new Student[0]);
        System.out.println("\nThe converted array: ");
        for(Student s: studentArray)
            System.out.println(s);
        System.out.println("\nSize of studentArray: "
        + studentArray.length);   
       
            
        
        
    }
    
    private static void displayStudents(Collection<Student> studentCollection)
    {
        for(Student s: studentCollection)
            System.out.println(s);
    }
}
