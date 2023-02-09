package cardlayout;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import mlt.SwingWindow;

public class Cards {

    public static void main(String[] args) {
	// schedule a job for the event-dispatching thread,
	// creating and showing this application's GUI
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		SwingWindow wn = new SwingWindow("Cards Window", 600, 400, new CardsPanel());
		wn.setMinimumSize(new Dimension(200, 100));
		wn.setQuitChar(KeyEvent.VK_ESCAPE);
	    }
	});
    }
}
