package circlepainter;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import mlt.SwingWindow;

public class CirclePainter {

    public static void main(String[] args) {
	// schedule a job for the event-dispatching thread to open the app
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		SwingWindow wn = new SwingWindow("Circle Painter", 560, 560, new CirclePainterPanel());
		wn.setMinimumSize(new Dimension(500, 500));
		wn.setQuitChar(KeyEvent.VK_ESCAPE);
	    }
	});
    }
}
