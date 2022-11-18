/* ThreadDemo1b:
 * 
 * Just like ThreadDemo1a, but uses the "implements Runnable"
 * way of creating a thread instead of "extends Thread".
 * 
 * Shows 3 threads with their output interspersed like this:
 * 
 * 012102010210001122201201121022 |
 * 
 * Synchronizing the "run" method does not cause the output
 * to become like this:
 * 
 * 000000000011111111112222222222 |
 * 
 * because each thread is attempting to lock its own
 * separate instance of ThreadDemo1b, so there's no
 * competition for locks.									*/

class ThreadDemo1b
    implements Runnable
{
    private int     id;

    ThreadDemo1b(int id)
    {
        this.id = id;
    }

    public synchronized void run()
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
            threads[i] = new Thread(new ThreadDemo1b(i));

        System.out.print("1b: ");

        // start the threads
        for (int i = 0; i < threads.length; i++)
            threads[i].start();

        // wait for the threads to finish
        for (int i = 0; i < threads.length; i++)
            try { threads[i].join(); } catch (InterruptedException e) { }

        System.out.println(" |");
    }
}
