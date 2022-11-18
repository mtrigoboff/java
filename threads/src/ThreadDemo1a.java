/* ThreadDemo1a:
 * 
 * Shows 3 threads with their output mixed up like this:
 * 
 * 012102010210001122201201121022 |
 * 
 * Synchronizing the "run" method does not cause the output
 * to become like this:
 * 
 * 000000000011111111112222222222 |
 * 
 * because each thread is attempting to lock its own
 * separate instance of Thread1a, so there's no
 * competition for locks.										*/

class ThreadDemo1a
{
	static class Thread1a
    	extends Thread
	{
	    private int     id;
	
	    Thread1a(int id)
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
	}

    public static void main(String[] args)
    {
        Thread[]    threads = new Thread[3];

        // create the threads
        for (int i = 0; i < threads.length; i++)
            threads[i] = new Thread1a(i);

        System.out.print("1a: ");

        // start the threads
        for (int i = 0; i < threads.length; i++)
            threads[i].start();

        // wait for the threads to finish
        for (int i = 0; i < threads.length; i++)
            try { threads[i].join(); } catch (InterruptedException e) { }

        System.out.println(" |");
    }
}

