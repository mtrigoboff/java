package animate;

import java.awt.event.KeyEvent;

import mlt.SwingWindow;

public class Animate {

	public static void main(String[] args) {
		// schedule a job for the event-dispatching thread to open the app
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SwingWindow wn = new SwingWindow("Animate", 400, 400, new AnimatePanel());
				wn.setQuitChar(KeyEvent.VK_ESCAPE);
				wn.setResizable(false);
			}
		});
	}
}
