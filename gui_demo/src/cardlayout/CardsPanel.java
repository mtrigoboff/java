package cardlayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
class CardsPanel extends JPanel {
	final static String ESC_ACTION = "ESC_ACTION";
	final static String prevBtnName = "Prev";
	final static String nextBtnName = "Next";
	final static Color[] colors = { Color.red, Color.yellow, Color.green, Color.blue };

	private CardLayout cards;
	private JPanel content;

	CardsPanel() {
		JPanel btnBar = new JPanel();
		JPanel btnBarLeft = new JPanel();
		JPanel btnBarRight = new JPanel();
		JButton prevBtn = new JButton(prevBtnName);
		JButton nextBtn = new JButton(nextBtnName);

		setLayout(new BorderLayout());

		// set up button bar
		btnBar.setLayout(new BorderLayout());
		// set up left half
		((FlowLayout) btnBarLeft.getLayout()).setAlignment(FlowLayout.LEFT);
		prevBtn.setMnemonic(KeyEvent.VK_P);
		btnBarLeft.add(prevBtn);
		// set up right half
		((FlowLayout) btnBarRight.getLayout()).setAlignment(FlowLayout.RIGHT);
		nextBtn.setMnemonic(KeyEvent.VK_N);
		btnBarRight.add(nextBtn);
		// add left and right halves to main button bar
		btnBar.add(btnBarLeft, BorderLayout.WEST);
		btnBar.add(btnBarRight, BorderLayout.EAST);
		// add button bar to this panel
		add(btnBar, BorderLayout.SOUTH);

		// set up action listeners for buttons
		prevBtn.addActionListener(e -> { cards.previous(content); });
		nextBtn.addActionListener(e -> { cards.next(content); });

		// set up ESC key action
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), ESC_ACTION);
		getActionMap().put(ESC_ACTION, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Container frame = getParent();
				do
					frame = frame.getParent();
				while (!(frame instanceof JFrame));
				((JFrame) frame).dispose();
			}
		});

		// set up content pane
		cards = new java.awt.CardLayout(6, 6);
		content = new JPanel();
		content.setLayout(cards);
		for (Color color : colors) {
			JPanel colorPanel = new JPanel();
			colorPanel.setBackground(color);
			content.add(color.toString(), colorPanel);
		}
		// add content pane to this panel
		add(content, BorderLayout.CENTER);
	}
}
