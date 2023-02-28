package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;
import java.util.*;

class Marina {

	Rectangle[] dockRects;
	Corner[] dockCorners;

	private ArrayList corners;
	private Dock[] docks;

	final private class Corner
			extends Point {

		// a corner is "covered" when another corner exists to its (left, right, top,
		// bottom)
		// along a continuous edge. The point is to collect information that can be used
		// to
		// eliminate a corner from the corners list because it does not protrude from
		// the
		// assemblage of docks in a way that would permit a boat to collide with it.
		boolean lc = false; // covered on the left
		boolean rc = false; // covered on the right
		boolean tc = false; // covered on the top
		boolean bc = false; // covered on the bottom

		Corner(Point pt) {
			x = pt.x;
			y = pt.y;
		}
	}

	Marina(Rectangle[] dockRects, Color dockColor, Color dockLineColor, int dockBoardWidth) {
		Rectangle rect;

		docks = new Dock[dockRects.length];
		this.dockRects = dockRects;

		corners = new ArrayList();

		for (int i = 0; i < dockRects.length; i++) {
			rect = dockRects[i];
			docks[i] = new Dock(rect, dockColor, dockLineColor, dockBoardWidth);
			addCorner(new Point(rect.x, rect.y));
			addCorner(new Point(rect.x + rect.width, rect.y));
			addCorner(new Point(rect.x, rect.y + rect.height));
			addCorner(new Point(rect.x + rect.width, rect.y + rect.height));
		}

		dockCorners = new Corner[corners.size()];
		corners.toArray(dockCorners);
	}
	// add dock corners to array so that they can be used to determine when the boat
	// has
	// collided with the dock. Eliminate corners that are "buried" (in other words,
	// given
	// the structure of the dock, these are corners that the boat would not be able
	// to hit).

	private void addCorner(Point pt) {
		Corner c = new Corner(pt);
		Rectangle rect;

		for (int i = 0; i < dockRects.length; i++) {
			rect = dockRects[i];
			if (pt.y == rect.y || pt.y == rect.y + rect.height) {
				if (pt.x == rect.x) {
					c.rc = true;
				} else if (pt.x == rect.x + rect.width) {
					c.lc = true;
				} else if (pt.x > rect.x && pt.x < rect.x + rect.width) {
					c.lc = true;
					c.rc = true;
				}
			}
			if (pt.x == rect.x || pt.x == rect.x + rect.width) {
				if (pt.y == rect.y) {
					c.tc = true;
				} else if (pt.y == rect.y + rect.height) {
					c.bc = true;
				} else if (pt.y > rect.y && pt.y < rect.y + rect.height) {
					c.tc = true;
					c.bc = true;
				}
			}
		}
		if (!((c.lc && c.rc) || (c.tc && c.bc))) {
			corners.add(c);
		}
	}

	void paint(Graphics g) {
		for (int i = 0; i < docks.length; i++) {
			docks[i].paint(g);
		}
	}
}
