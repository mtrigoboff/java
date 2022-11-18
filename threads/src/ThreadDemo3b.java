/* ThreadDemo3b:
 * 
 * Uses a synchronized block and a lock object.
 * 
 * Shows how to use wait() and notify() so that
 * the threads alternate in a clockwork manner.
 * 
 * Doesn't require call to Thread.yield() because
 * "main" thread sets subordinate thread priorities
 * to be higher than its own.
 *
 * Not "industrial strength," can behave unpredictably.		*/

class ThreadDemo3b
    implements Runnable
{
    private int     id;
    private Object  lock;

    ThreadDemo3b(int id, Object lock)
    {
        this.id = id;
        this.lock = lock;
    }

    public void run()
    {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        synchronized (lock) {
            // print id 10 times with short pauses in between
            for (int i = 0; i < 10; i++) {
                try { lock.wait(); } catch (InterruptedException e) { }
                System.out.print(id);
                try { Thread.sleep(200); } catch (InterruptedException e) { }
                lock.notify();
                }
            }
    }

    public static void main(String[] args)
    {
        Thread[]    threads = new Thread[3];
        Object      lock = new Object();

        // create the threads to run at max priority
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new ThreadDemo3b(i, lock));
            threads[i].setPriority(Thread.MAX_PRIORITY);
            }

        System.out.print("3b: ");

        // start the threads
        for (int i = 0; i < threads.length; i++)
            threads[i].start();

        // note: no call to Thread.yield() is required here

        // kick one thread out of lock's waiting area
        synchronized (lock) {
            lock.notify();
            }

        // wait for the threads to finish
        for (int i = 0; i < threads.length; i++)
            try { threads[i].join(); } catch (InterruptedException e) { }

        System.out.println(" |");
    }
}
