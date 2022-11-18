/* ThreadDemo3d:
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
 * Using private static and anonymous inner classes
 * shields things like the public run method from
 * outside users.										*/

class ThreadDemo3d
{
    private final static int        NTHREADS = 3;
    private static int              nextId = 0;

    private static class State
    {
        int     nActiveThreads = 0;
    }

    public static void main(String[] args)
    {
        Thread[]        threads = new Thread[NTHREADS];
        final State     stateObj = new State();

        // need to initialize this here because a static variable
        // IS ONLY INITIALIZED ONCE when the class is loaded.  So the
        // second time you run the code, it won't be its original value.
        nextId = 0;
        for (int i = 0; i < NTHREADS; i++) {
            threads[i] = new Thread(new Runnable()
            {
                private int     id = ThreadDemo3d.nextId++;;
                private State   state = stateObj;

                public void run()
                {
                    boolean     shouldWait = true;

                    synchronized (state) {
                        state.nActiveThreads++;
                        // print id with short pauses in between
                        for (int i = 0; i < Math.max(12 - 3 * id, 1); i++) {
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
            });
            }

        System.out.print("3d: ");

        // start the threads
        for (int i = 0; i < NTHREADS; i++)
            threads[i].start();

        synchronized (stateObj) {
            // wait for all the sub-threads to start and wait
            while (stateObj.nActiveThreads < NTHREADS)
                try { stateObj.wait(100); } catch (InterruptedException e) { }
            // kick one thread out of the state object's waiting area
            stateObj.notify();
            }

        // wait for the threads to finish
        for (int i = 0; i < NTHREADS; i++)
            try { threads[i].join(); } catch (InterruptedException e) { }

        System.out.println(" |");
    }
}
