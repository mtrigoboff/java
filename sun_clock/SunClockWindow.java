import java.awt.Dimension;
import java.awt.event.*;

public class SunClockWindow {

	private static mlt.SwingWindow wn;
	private static SunClockApplet sca;

	public static void main(String[] args) {
		Dimension mapDimensions;

		sca = new SunClockApplet();
		mapDimensions = sca.init();
		// add 33 for window header height
		wn = new mlt.SwingWindow("Sun Clock", mapDimensions.width, mapDimensions.height + 33, sca);
		wn.setResizable(false);
		sca.setFocusable(true);
		wn.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				// window has closed, allow waitForClose() to return
				sca.stop();
			}
		});

		sca.start();
	}
}
