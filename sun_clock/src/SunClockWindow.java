import java.awt.event.*;

public class SunClockWindow {

    private static mlt.SwingWindow wn;
    private static SunClockApplet sca;

    public static void main(String[] args) {
	sca = new SunClockApplet();
	sca.init();
	wn = new mlt.SwingWindow("Sun Clock", 600, 540, sca);
	System.out.println("adding listeners");
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
