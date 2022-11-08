package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;

public class HelpText
		extends Canvas {

	private int border = 8;
	private FontMetrics fm = null;
	private int lineHt;
	private int leading;
	private int ascent;
	private String[] text;
	private int w;

	public HelpText(String[] text) {
		setBackground(Color.lightGray);
		setForeground(Color.gray);
		this.text = text;
	}

	public Dimension minimumSize() {
		return preferredSize();
	}

	public void paint(Graphics g) {
		if (fm == null) {
			setupFont();
		}

		for (int i = 0, y = border + leading + ascent; i < text.length; i++, y += lineHt) {
			g.drawString(text[i], border, y);
		}
	}

	public Dimension preferredSize() {
		int border2 = 2 * border;

		if (fm == null) {
			setupFont();
		}

		return new Dimension(w + border2, text.length * (lineHt + leading) - leading + border2);
	}

	private void setupFont() {
		int strWidth;

		fm = getFontMetrics(getFont());
		lineHt = fm.getHeight();
		leading = fm.getLeading();
		ascent = fm.getAscent();
		for (int i = 0; i < text.length; i++) {
			strWidth = fm.stringWidth(text[i]);
			if (w < strWidth) {
				w = strWidth;
			}
		}
	}
}
