package colorbtns;

import java.awt.event.KeyEvent;
import mlt.SwingWindow;

public class ColorBtns {

    public static void main(String[] args) {
	// schedule a job for the event-dispatching thread to open the app
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		SwingWindow wn = new SwingWindow("CBtns", 172, 212, new ColorBtnsPanel());
		wn.setQuitChar(KeyEvent.VK_ESCAPE);
		wn.setResizable(false);
	    }
	});
    }
}
