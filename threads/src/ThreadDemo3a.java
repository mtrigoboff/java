/* ThreadDemo3a:
 * 
 * Uses a synchronized block and a lock object.
 * 
 * Shows how to use wait() and notify() so that
 * the threads alternate in a clockwork manner.
 * 
 * Requires call to Thread.yield() to allow the
 * subordinate threads to start up and get to
 * their first call to wait().
 *
 * Not "industrial strength," can behave unpredictably.		*/

class ThreadDemo3a
    implements Runnable
{
    private int     id;
    private Object  lock;

    ThreadDemo3a(int id, Object lock)
    {
        this.id = id;
        this.lock = lock;
    }

    public void run()
    {
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

        // create the threads
        for (int i = 0; i < threads.length; i++)
            threads[i] = new Thread(new ThreadDemo3a(i, lock));

        System.out.print("3a: ");

        // start the threads
        for (int i = 0; i < threads.length; i++)
            threads[i].start();

        // give the threads the opportunity to run to their first wait().
        Thread.yield();

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
