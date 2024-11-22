
// (c) 1995 - 2022 Michael Trigoboff.  All Rights Reserved.
import java.awt.*;

final class SunClockCanvas
		extends Canvas
		implements Runnable {

	DateInfo dateInfo;
	Image dayMapImage;
	int mapHeight;
	int mapWidth; // legal values: 512 or 128
	Image nightMapImage;
	boolean running = false;
	ShadowCurve shadowCurve;
	int shadowIncr;
	Thread timer = null;
	long updateInterval;

	SunClockCanvas(String appDirectory, Image dayMapImage, Image nightMapImage) {
		this.dayMapImage = dayMapImage;
		this.nightMapImage = nightMapImage;

		// size the Panel to the images (day and night are the same size)
		mapWidth = dayMapImage.getWidth(this);
		mapHeight = dayMapImage.getHeight(this);
		resize(mapWidth + 2, mapHeight + 2);
		shadowIncr = mapWidth == 512 ? 1 : 4;
		// System.out.println(
		// "SunClockPanel: mapWidth " + mapWidth + ", mapHeight " + mapHeight);

		// compute update interval (milliseconds representing 1 pixel on the map)
		updateInterval = 1000 * 86400 / mapWidth;
		// System.out.println("SunClock.init: updateInterval " + updateInterval);

		// get date as late as possible, to synchronize as well as we can with
		// the timer thread that will keep the shadow in pace with the sun
		dateInfo = new DateInfo();
		// dateInfo.print();

		// compute time-dependent information needed to draw the shadow
		shadowCurve = new ShadowCurve(dateInfo, appDirectory);
	}

	private int divByShadowIncr(int n, boolean nightTop) {
		// this is a total hack, needed because we're dividing a full-size curve
		// table by shadowIncr (4, currently) and the numerics of it leave us
		// missing the top or bottom of the small Sun Clock display, depending
		// on the value of nightTop (we'd miss the bottom for nightTop == true,
		// the top for nightTop == false). This hack adjusts things so we don't.
		// Wasn't a problem on the Mac because of QuickDraw's between-the-pixels
		// coordinate system.

		// 2/10/97 - furthermore, it causes shadow slide updating to not work properly
		// in the large Sun Clock. So I fixed it by only letting it happen for
		// the small one.
		// 7/21/97 - this made the curve look jagged and wierd when nightTop == false,
		// so I eliminated the 'else' clause in the if (nightTop) ... stmt below
		if (shadowIncr == 1) {
			return n;
		} else {
			int q;

			q = n / shadowIncr;
			if (nightTop) {
				if (n % shadowIncr != 0) // round up so we don't miss a pixel
				{
					q++;
				}
				q++; // bump up so we can hit bottom of map
			}
			return q;
		}
	}

	void drawMapCol(Graphics g, Image mapImage, int x, int yTop, int yBottom) {
		Graphics gCol;

		gCol = g.create();
		// need to create a new Graphics object because there is no way to
		// expand the clipping rectangle of a graphics object once it
		// has been contracted
		gCol.clipRect(x, yTop, 1, yBottom - yTop);
		gCol.drawImage(mapImage, 0, 0, this);
		gCol.dispose();
	}

	public Dimension minimumSize() {
		return preferredSize();
	}

	public synchronized void paint(Graphics g) {
		g.drawImage(dayMapImage, 0, 0, this);

		if (dateInfo.nightTop) {
			for (int x = 0, s = 0; x < mapWidth; x++, s += shadowIncr) {
				drawMapCol(g, nightMapImage, x, 0,
						divByShadowIncr(shadowCurve.mapY[s], true));
			}
		} else {
			for (int x = 0, s = 0; x < mapWidth; x++, s += shadowIncr) {
				drawMapCol(g, nightMapImage, x,
						divByShadowIncr(shadowCurve.mapY[s], false), mapHeight);
			}
		}
	}

	public Dimension preferredSize() {
		return new Dimension(mapWidth + 2, mapHeight + 2);
	}

	public synchronized void run() {
		Graphics g;
		DateInfo newDateInfo;
		Image topImage;
		Image bottomImage;
		int yOld, yNew;

		while (running) {
			try {
				wait(updateInterval);
			} catch (Exception e) {
			}
			// System.out.println("SunClock: timer click");

			newDateInfo = new DateInfo();
			// newDateInfo.print();
			if (shadowCurve.update(newDateInfo)) {
				g = getGraphics();
				if (newDateInfo.nightTop) {
					topImage = nightMapImage;
					bottomImage = dayMapImage;
				} else {
					topImage = dayMapImage;
					bottomImage = nightMapImage;
				}
				if (newDateInfo.nightTop != dateInfo.nightTop) {
					for (int x = 0, s = 0; x < mapWidth; x++, s += shadowIncr) {
						yOld = shadowCurve.oldMapY[s];
						yNew = shadowCurve.mapY[s];
						if (shadowIncr == 4) {
							yOld = divByShadowIncr(yOld, newDateInfo.nightTop);
							yNew = divByShadowIncr(yNew, newDateInfo.nightTop);
						}
						if (yNew <= yOld) {
							drawMapCol(g, topImage, x, 0, yNew);
							drawMapCol(g, bottomImage, x, yOld, mapHeight);
						} else if (yNew > yOld) {
							drawMapCol(g, topImage, x, 0, yOld);
							drawMapCol(g, bottomImage, x, yNew, mapHeight);
						}
					}
				} else {
					for (int x = 0, s = 0; x < mapWidth; x++, s += shadowIncr) {
						yOld = shadowCurve.oldMapY[s];
						yNew = shadowCurve.mapY[s];
						if (shadowIncr == 4) {
							yOld = divByShadowIncr(yOld, newDateInfo.nightTop);
							yNew = divByShadowIncr(yNew, newDateInfo.nightTop);
						}
						if (yNew < yOld) {
							drawMapCol(g, bottomImage, x, yNew, yOld);
						} else if (yNew > yOld) {
							drawMapCol(g, topImage, x, yOld, yNew);
						}
					}
				}
				dateInfo = newDateInfo;
			}
		}
	}

	synchronized void start() {
		// System.out.println("SunClock: start");
		running = true;

		// set up and start timer
		if (timer == null) {
			timer = new Thread(this);
			timer.start();
		}
	}

	synchronized void stop() {
		// System.out.println("SunClock: stop");
		if (timer != null) {
			timer.stop();
			timer = null;
		}
		running = false;
	}

	public void update(Graphics g) {
		// override normal behavior of Component, which is to clear first, then paint.
		// That creates flashing we don't want.
		paint(g);
	}
}
