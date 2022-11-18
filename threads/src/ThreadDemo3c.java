/* ThreadDemo3c:
 * 
 * Uses a synchronized block and a lock object.
 * 
 * Shows how to use wait() and notify() so that
 * the threads alternate in a "clockwork" manner.
 * 
 * Does "industrial strength" checking to make sure
 * that all sub-threads are waiting before starting
 * the "clockwork" by doing a notify.
 * 
 * Using private static inner classes shields things
 * like the public run method from outside users.		*/

class ThreadDemo3c
{
    private final static int        NTHREADS = 3;

    private static class Thread3c
        extends Thread
    {
        private static class State
        {
            int     nActiveThreads = 0;
        }

    	private int     id;
        private State   state;

        private Thread3c(int id, State state)
        {
            this.id = id;
            this.state = state;
        }

        public void run()
        {
            boolean     shouldWait = true;

            synchronized (state) {
                state.nActiveThreads++;
                // print id with short pauses in between
                for (int i = 0; i < 12 - 3 * id; i++) {
                    if (shouldWait)
                        try { state.wait(); } catch (InterruptedException e) { }
                    System.out.print(id);
                    try { Thread.sleep(200); } catch (InterruptedException e) { }
                    if (state.nActiveThreads > 1)   // this sub-thread is not alone
                        state.notify();
                    else                            // this sub-thread is alone
                        shouldWait = false;
                    }
                state.nActiveThreads--;
                }
        }
    }

    public static void main(String[] args)
    {
        Thread[]    	threads = new Thread[NTHREADS];
        Thread3c.State	state = new Thread3c.State();

        for (int i = 0; i < NTHREADS; i++)
            threads[i] = new Thread3c(i, state);

        System.out.print("3c: ");

        // start the threads
        for (int i = 0; i < NTHREADS; i++)
            threads[i].start();

        synchronized (state) {
            // wait for all the sub-threads to start and wait
            while (state.nActiveThreads < NTHREADS)
                try { state.wait(100); } catch (InterruptedException e) { }
            // kick one thread out of the state object's waiting area
            state.notify();
            }

        // wait for the threads to finish
        for (int i = 0; i < NTHREADS; i++)
            try { threads[i].join(); } catch (InterruptedException e) { }

        System.out.println(" |");
    }
}
