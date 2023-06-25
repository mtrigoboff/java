
import java.awt.event.*;

public class DockingSimulatorWindow {

	private static mlt.SwingWindow wn;
	private static DockingSimulator ds;

	public static void main(String[] args) {
		ds = new DockingSimulator();
		ds.init();
		wn = new mlt.SwingWindow("Docking Simulator", 600, 540, ds);
		wn.setResizable(false);
		ds.setFocusable(true);
		ds.addMouseListener(
				new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						System.out.println("mouse pressed");
					}

					public void mouseReleased(MouseEvent e) {
						System.out.println("mouse released");
					}
				});
		ds.addKeyListener(
				new KeyListener() {
					public void keyPressed(KeyEvent e) {
						// System.out.printf("key pressed %c\n", e.getKeyChar());
						ds.keyDown(e);
					}

					public void keyReleased(KeyEvent e) {
						// System.out.println("key released");
					}

					public void keyTyped(KeyEvent e) {
						// System.out.println("key typed");
					}
				});
		wn.addWindowListener(
				new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						// window has closed, allow waitForClose() to return
						ds.stop();
					}
				});

		ds.start();
	}
}
