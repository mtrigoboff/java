package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;

public class VectorInput
	extends Canvas {

    private final static float piOverTwo = (float) Math.PI / 2;
    private final static float threePiOverTwo = 3 * piOverTwo;

    private SimulatorApplet applet;
    private int index;
    private Color vectorColor;
    private FontMetrics fm = null;
    private int wTotal;
    private int hTotal;
    private int wContents;
    private int border;
    private int lineHt;
    private String title;
    private String units;
    private int unitsStrWidth;
    private int titleX, titleY;
    private int valueY;
    private int circleCenterX, circleCenterY;
    private int radius;
    private int nRings;
    private int ringIncr;
    private int vectorTipX, vectorTipY;
    private float maxValue;
    private float value;
    private float direction;

    public VectorInput(SimulatorApplet applet, int index,
	    String title, String units, Color vectorColor,
	    int width, int height, int border,
	    int maxValue, int nRings, int centerOffsetX, int centerOffsetY) {
	int border2 = 2 * border;

	this.applet = applet;
	this.index = index;
	this.title = title;
	this.units = units;
	circleCenterX = width / 2;
	circleCenterY = circleCenterX;
	circleCenterX += centerOffsetX;
	circleCenterY += centerOffsetY;
	radius = (width - border2) / 2;
	this.maxValue = maxValue;
	this.nRings = nRings;
	wTotal = width;
	hTotal = height;
	wContents = width - border2;
	ringIncr = wContents / nRings;
	this.border = border;
	this.vectorColor = vectorColor;
	setZero();
    }

    public Dimension minimumSize() {
	return preferredSize();
    }

    public boolean mouseDown(Event event, int x, int y) {
	double xv = x - circleCenterX;
	double yv = y - circleCenterY;
	float length = (float) Math.sqrt(xv * xv + yv * yv);

	if (length < radius) {
	    vectorTipX = x;
	    vectorTipY = y;

	    value = length / (radius - 3) * maxValue;								// - 3 so that we can get a value == maxValue
	    value = (float) Math.rint((double) value * 10) / 10;
	    value = Math.min(value, maxValue);

	    direction = xv == 0 ? piOverTwo : Math.abs((float) Math.atan(yv / xv));
	    // 0 (horizontal) to 90 (vertical) degrees, identical in the 4 quadrants

	    // normalize direction to [0..2*pi) radians, nautical interpretation
	    if (xv == 0) {
		direction = yv <= 0 ? 0 : (float) Math.PI;
	    } else if (xv > 0) {
		if (yv < 0) {
		    direction = piOverTwo - direction;
		} else {
		    direction += piOverTwo;
		}
	    } else // xv < 0
	    if (yv > 0) {
		direction = threePiOverTwo - direction;
	    } else {
		direction += threePiOverTwo;
	    }

	    // tell the client about the new value
	    applet.setVectorValue(index, direction, value);

	    repaint();
	}
	return true;
    }

    public void paint(Graphics g) {
	if (fm == null) {
	    fm = getFontMetrics(getFont());
	    lineHt = fm.getHeight();
	    titleX = border + (wContents - fm.stringWidth(title)) / 2;
	    titleY = border + wContents + lineHt;
	    valueY = titleY + lineHt;
	    unitsStrWidth = fm.stringWidth(units);
	}

	g.setColor(Color.lightGray);
	g.fillRect(0, 0, wTotal - 1, hTotal - 1);

	// draw outer circle
	g.setColor(Color.darkGray);
	g.drawOval(border + 1, border + 1, wContents, wContents);

	// draw inner rings
	g.setColor(Color.gray);
	for (int i = 1, r = ringIncr, rtl;
		i < nRings;
		i++, r += ringIncr) {
	    rtl = border + r / 2 + 1;
	    g.drawOval(rtl, rtl, wContents - r, wContents - r);
	}

	// draw vector
	g.setColor(vectorColor);
	for (int x = -1; x <= 1; x++) {
	    for (int y = -1; y <= 1; y++) {
		g.drawLine(circleCenterX + x, circleCenterY + y, vectorTipX + x, vectorTipY + y);
	    }
	}

	// draw title
	g.setColor(Color.black);
	g.drawString(title, titleX, titleY);

	// draw value
	{
	    String v = Float.toString(value);
	    int vw = fm.stringWidth(v);
	    int x = border + (wContents - (vw + unitsStrWidth)) / 2;

	    g.drawString(v, x, valueY);
	    g.drawString(units, x + vw, valueY);
	}
    }

    public Dimension preferredSize() {
	return new Dimension(wTotal, hTotal);
    }

    public void reset() {
	setZero();
	applet.setVectorValue(index, direction, value);
	repaint();
    }

    private void setZero() {
	vectorTipX = circleCenterX;
	vectorTipY = vectorTipX;
	value = 0;
	direction = 0;
    }

    public void update(Graphics g) {
	paint(g);
    }
}
