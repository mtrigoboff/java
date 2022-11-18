package colorbtns;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class ColorBtnsPanel extends JPanel {
	final static String UP_KEY = "UP_KEY";
	final static String DOWN_KEY = "DOWN_KEY";

	private ColorRBtn[] 	btns = new ColorRBtn[BtnSpec.values().length];
	private ButtonGroup		btnGroup = new ButtonGroup();
	private int				btnIndex = 0;

	private enum BtnSpec {
		Red(Color.RED, KeyEvent.VK_R), Orange(Color.ORANGE, KeyEvent.VK_O),
		Yellow(Color.YELLOW, KeyEvent.VK_Y), Green(Color.GREEN, KeyEvent.VK_G),
		Blue(Color.BLUE, KeyEvent.VK_B);

		private Color	color;
		private int		mnemonicIndex;

		BtnSpec(Color color, int mnemonicIndex) {
			this.color = color;
			this.mnemonicIndex = mnemonicIndex;
		}
	}

	// an inner (i.e. non-static) class of CardsPanel
	private class ColorRBtn extends JRadioButton {
		private Color	color;

		private ColorRBtn(String label, Color color, int mnemonicIndex) {
			super(label); // do JRadioButton setup
			this.color = color;
			setOpaque(false); // make button transparent
			setMnemonic(mnemonicIndex);
			ColorBtnsPanel.this.add(this); // add button to panel
			addActionListener(e -> {
				ColorBtnsPanel.this.setBackground(color);
				btnIndex = BtnSpec.valueOf(e.getActionCommand()).ordinal();
			});
		}
	}

	public ColorBtnsPanel() {
		ColorRBtn	topBtn = null;
		ColorRBtn	btn;

		setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// up, down arrow key actions
		{
		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), UP_KEY);
		getActionMap().put(UP_KEY, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (btnIndex > 0) {
					btnIndex--;
					btns[btnIndex].doClick();
					btns[btnIndex].setFocusPainted(true); // does not seem to work
					}
			}
		});
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), DOWN_KEY);
		getActionMap().put(DOWN_KEY, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (btnIndex < BtnSpec.values().length - 1) {
					btnIndex++;
					btns[btnIndex].doClick();
					btns[btnIndex].setFocusPainted(true); // does not seem to work
					}
			}
		});
		}

		{
		int i = 0;
		for (BtnSpec btnSpec : BtnSpec.values()) {
			btn = new ColorRBtn(btnSpec.toString(), btnSpec.color, btnSpec.mnemonicIndex);
			btnGroup.add(btn);
			if (topBtn == null)
				topBtn = btn; // will use top btn below to set initial color
			btns[i++] = btn;
			}
		}

		// set up initial color and selected radio button
		btnGroup.setSelected(topBtn.getModel(), true);
		setBackground(topBtn.color);
	}
}
