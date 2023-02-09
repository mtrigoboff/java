package docksim.util;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
public class PersistentStatusMsg
	extends Thread {

    private PersistentStatusMsgClient client;

    private String msg;
    private long persistenceMs;					// in milliseconds

    public PersistentStatusMsg(PersistentStatusMsgClient client, String msg, int persistenceSec) {
	this.client = client;
	this.msg = msg;
	persistenceMs = persistenceSec * 1000;
	start();
    }

    synchronized public void run() {
	long stopTimeMs = 0;

	stopTimeMs = System.currentTimeMillis() + persistenceMs;
	setPriority(Math.min(getPriority() + 1, Thread.MAX_PRIORITY));
	loop:
	while (System.currentTimeMillis() < stopTimeMs) {
	    client.showStatus(msg);
	    try {
		Thread.sleep(250);								// sleep for 250 msec
	    } catch (InterruptedException e) {
		break loop;
	    }
	}
	client.blankStatus();
    }
}
