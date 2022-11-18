/* ThreadDemo2a:
 * 
 * Based on ThreadDemo1b.
 * 
 * Shows one way to synchronize the 3 threads so their output
 * becomes like this:
 * 
 * 000000000011111111112222222222 |
 * 
 * Uses a synchronized static method.			*/

class ThreadDemo2a
    implements Runnable
{
    private int     id;

    ThreadDemo2a(int id)
    {
        this.id = id;
    }

    public void run()
    {
        printId(id);
    }

    static synchronized void printId(int id)
    {
        // print id 10 times with pauses in between
        for (int i = 0; i < 10; i++) {
            System.out.print(id);
            try { Thread.sleep(500); } catch (InterruptedException e) { }
            }
    }

    public static void main(String[] args)
    {
        Thread[]    threads = new Thread[3];

        // create the threads
        for (int i = 0; i < threads.length; i++)
            threads[i] = new Thread(new ThreadDemo2a(i));

        System.out.print("2a: ");

        // start the threads
        for (int i = 0; i < threads.length; i++)
            threads[i].start();

        // wait for the threads to finish
        for (int i = 0; i < threads.length; i++)
            try { threads[i].join(); } catch (InterruptedException e) { }

        System.out.println(" |");
    }
}
