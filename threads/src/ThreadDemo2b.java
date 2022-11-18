/* ThreadDemo2b:
 * 
 * Based on ThreadDemo1b.
 * 
 * Shows one way to synchronize the 3 threads so their output
 * becomes like this:
 * 
 * 000000000011111111112222222222 |
 * 
 * Uses a synchronized block and a lock object.			*/

class ThreadDemo2b
    implements Runnable
{
    private int     id;
    private Object  lock;

    ThreadDemo2b(int id, Object lock)
    {
        this.id = id;
        this.lock = lock;
    }

    public void run()
    {
        synchronized (lock) {
            // print id 10 times with pauses in between
            for (int i = 0; i < 10; i++) {
                System.out.print(id);
                try { Thread.sleep(500); } catch (InterruptedException e) { }
                }
            }
    }

    public static void main(String[] args)
    {
        Thread[]    threads = new Thread[3];
        Object      lock = new Object();

        // create the threads
        for (int i = 0; i < threads.length; i++)
            threads[i] = new Thread(new ThreadDemo2b(i, lock));

        System.out.print("2b: ");

        // start the threads
        for (int i = 0; i < threads.length; i++)
            threads[i].start();

        // wait for the threads to finish
        for (int i = 0; i < threads.length; i++)
            try { threads[i].join(); } catch (InterruptedException e) { }

        System.out.println(" |");
    }
}
