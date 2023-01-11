package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;

class Dock {

    Rectangle rect;
    Point[] corners;

    private int x, y, width, height;
    private Color color;
    private Color lineColor;
    private int boardWidth;

    Dock(Rectangle rect, Color color, Color lineColor, int boardWidth) {
	this.rect = rect;
	x = rect.x;
	y = rect.y;
	width = rect.width;
	height = rect.height;

	corners = new Point[4];
	corners[0] = new Point(x, y);
	corners[1] = new Point(x + width, y);
	corners[2] = new Point(x, y + height);
	corners[3] = new Point(x + width, y + height);

	this.color = color;
	this.lineColor = lineColor;
	this.boardWidth = boardWidth;
    }

    void paint(Graphics g) {
	g.setColor(color);
	g.fillRect(x, y, width, height);
	g.setColor(lineColor);
	g.drawRect(x, y, width, height);
	if (height > width) {
	    for (int yc = y; yc < y + height; yc += boardWidth) {
		g.drawLine(x, yc, x + width, yc);
	    }
	} else {
	    for (int xc = x; xc < x + width; xc += boardWidth) {
		g.drawLine(xc, y, xc, y + height);
	    }
	}
    }
}
