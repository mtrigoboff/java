package seasons;

public class SeasonDemo
{
    public static void main(String[] args)
    {
        // print values of Season
    	System.out.print("values of Season:       ");
        for (Season season : Season.values())
            System.out.print(season + " ");
        System.out.println();
        
        // print values of Season2
    	System.out.print("values of Season2:      ");
        for (Season2 season2 : Season2.values())
            System.out.print(season2 + " ");
        System.out.println();
        
        // ask Season2 to print its values to System.out
    	System.out.print("Season2 prints itself:  ");
        Season2.printValues(System.out);       
    } 
}
