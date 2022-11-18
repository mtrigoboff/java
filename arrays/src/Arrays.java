public class Arrays
{
	static int[]    dim1 = new int [4];         // 1 dimensional array
	static int[][]  dim2 = new int [4][3];      // 2 dimensional array
	static int[][]  tri =  new int [4][];       // triangular array
        // row subarrays are not automatically created by this declaration -
        // they will be created at runtime

    public static void main(String[] args)
    {
        int     n = 100;

        // initialize one dimensional array
        for (int i = 0; i < dim1.length; i++)
            dim1[i] = n++;

        // print one dimensional array
        System.out.println("1 dimensional array:");
        for (int element : dim1)
            System.out.print(element + " ");
        System.out.printf("%n%n");
        
        // initialize two dimensional array
        n = 200;
        for (int i = 0; i < dim2.length; i++)
            for (int j = 0; j < dim2[i].length; j++)
                dim2[i][j] = n++;

        // print two dimensional array
        System.out.println("2 dimensional array:");
        for (int[] row : dim2) {
            for (int element : row)
                System.out.print(element + " ");
            System.out.println();
            }
        System.out.println();
        
        // build triangular array - each row is of a different length
        n = 300;
        for (int i = 0; i < tri.length; i++) {
            tri[i] = new int[i + 1];                // create row subarray
            for (int j = 0; j < tri[i].length; j++)
                tri[i][j] = n++;
            }

        // print triangular array
        System.out.println("triangular array:");
        for (int[] row : tri) {
            for (int element : row)
                System.out.print(element + " ");
            System.out.println();
            }
        System.out.printf("%n%n");
    }
}
