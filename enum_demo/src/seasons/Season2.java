package seasons;

import java.io.PrintStream;

public enum Season2
{
    SPRING, SUMMER, FALL, WINTER;

    // print list of values with commas in between
    static void printValues(PrintStream out)
    {
        boolean     first = true;       // no comma before first item
        
        for (Season2 season : Season2.values()) {
            out.print((first ? "" : ", ") + season);
            first = false;
            }
        out.println();
    }

    // override toString so that strings for this enum will be
    // lower case except for an upper case first character
    public String toString()
    {
        String      name = super.toString();
        
        return name.substring(0, 1) + name.substring(1).toLowerCase() +
               "(" + ordinal() + ")";
    }
}
