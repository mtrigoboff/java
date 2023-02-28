package mlt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 * Opens a Swing window for display of the specified window contents.
 *
 * @version 2.0
 * @author Michael Trigoboff
 */
@SuppressWarnings("serial")
public class SwingWindow extends JFrame {

	private JComponent contents;

	/**
	 * Creates a Swing window that will hold the specified contents.
	 *
	 * @param title    The window title.
	 * @param width    The window width.
	 * @param height   The window height.
	 * @param contents The window contents.
	 */
	public SwingWindow(String title, int width, int height, JComponent contents) {
		super(title);
		this.contents = contents;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// default content pane layout manager is BorderLayout
		getContentPane().add(contents, BorderLayout.CENTER);
		setSize(width, height);
		// center window in screen
		setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
		setVisible(true);
	}

	/**
	 * Sets a character that can be typed to close the window.
	 *
	 * @param vkChar The character, which must be a VK_... constant from
	 *               java.awt.KeyEvent.
	 */
	public void setQuitChar(int vkChar) {
		final String QUIT_ACTION = "SW_QUIT";

		contents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(vkChar, 0, false),
				QUIT_ACTION);
		contents.getActionMap().put(QUIT_ACTION, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
