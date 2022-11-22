package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;

public class FramedPanel
	extends Panel {

    private Insets insets;

    public FramedPanel(Color frameColor, Color bgColor, Insets insets, LayoutManager layoutMgr) {
	setForeground(frameColor);
	setBackground(bgColor);
	this.insets = insets;
	setLayout(layoutMgr);
    }

    public Insets insets() {
	return insets;
    }

    public void paint(Graphics g) {
	g.fillRect(0, 0, size().width, size().height);
	super.paint(g);
    }

    public void paintComponents(Graphics g) {
	super.paintComponents(g);
    }
}
