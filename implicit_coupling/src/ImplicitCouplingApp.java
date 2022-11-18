// demonstrates implicit coupling:
// how a seemingly innocuous change in the underlying implementation
// can produce a significant difference in behavior

public class ImplicitCouplingApp
{

    public static void main(String[] args)
    {
        Divider idiv = new IntegerDivider();
        Divider fdiv = new FloatingDivider();
        int     quotient;
        
        System.out.println("integer division:");
        try {
            quotient = idiv.divide(1, 0);
            System.out.printf("quotient = %d%n", quotient);
            }
        catch (Throwable t) {
            System.out.println(t.getMessage());
            }

        System.out.printf("%nfloating point division:%n");
        try {
            quotient = fdiv.divide(1, 0);
            System.out.printf("quotient = %X%n", quotient);
        }
        catch (Throwable t) {
            System.out.println(t.getMessage());
            }
    }

}
