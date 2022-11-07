// demonstrates the new varargs feature of Java 5.0,
// which allows definition of methods that take
// a variable number of arguments

public class Varargs
{

    public static void main(String[] args)
    {
        // calling printNumbers with various numbers of arguments
        printNumbers("no args");
        printNumbers("one arg", 3);
        printNumbers("two args", 3, 6);
        printNumbers("three args", 3, 6, 9);

        // calling printNumbers with an array to demonstrate
        // the underlying mechanism of varargs
        printNumbers("called with array", new Integer[]{2, 4, 6, 8});
    }

    // method can be called with zero or more integer arguments
    // following the String argument.  This is indicated by
    // the ... following Integer in the argument list.
    // The vararg is required to be the last item in the
    // argument list.
    //
    // (Note: this code also uses the new autoboxing feature.)
    static void printNumbers(String msg, Integer... numbers)
    {
        boolean     first = true;
        
        // the numbers arg is implemented as an array in the compiled code,
        // so it can be used in any way an array can be used
        System.out.printf("msg: \"%s\"%ncalled with %d number argument%s:%n",
                          msg,
                          numbers.length,
                          numbers.length != 1 ? "s" : "");
                            // no plural when there is exactly 1 argument
        if (numbers.length > 0) {
            for (int i : numbers) {
                if (first)
                    first = false;
                else
                    System.out.print(", ");
                System.out.printf("%d", i);
                }
            System.out.println();
        }
        System.out.println();
    }
}
