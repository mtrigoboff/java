package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;

public class Simulator
		extends Canvas
		implements Runnable {

	private Boat boat;
	private int initialBoatX;
	private int initialBoatY;
	private float initialBoatHeading; // degrees, nautical interpretation, 0 == North, etc.

	private Marina marina;

	private Image marinaI;

	private Image offscreenI;
	private Graphics offscreenG;
	private Color frameColor;

	private Thread runner;
	private int frameMs; // frame time in milliseconds

	private SimulatorApplet applet;

	public Simulator(Color waterColor, Color boatColor, Color boatLineColor, Color frameColor,
			Rectangle[] dockRects, Color dockColor, Color dockLineColor, int dockBoardWidth,
			Boat boat, int initialBoatX, int initialBoatY, float initialBoatHeading,
			float pixelsPerFoot, int frameMs, SimulatorApplet applet, boolean showGauges) {
		this.frameColor = frameColor;
		setBackground(waterColor);
		marina = new Marina(dockRects, dockColor, dockLineColor, dockBoardWidth);
		this.boat = boat;
		boat.init(boatColor, boatLineColor, initialBoatX, initialBoatY, initialBoatHeading, this, showGauges);
		boat.setPixelsPerFoot(pixelsPerFoot);
		this.initialBoatX = initialBoatX;
		this.initialBoatY = initialBoatY;
		this.initialBoatHeading = initialBoatHeading;
		this.frameMs = frameMs;
		this.applet = applet;
	}

	public boolean checkCollision() {
		return boat.checkCollision(marina.dockCorners, marina.dockRects);
	}

	public GaugeDisplay getBoatGaugeDisplay() {
		return boat.getGaugeDisplay();
	}

	private synchronized void init() // synchronized because it can be called simultaneously when paint(g) is called
										// by update
	// and paint(g, rect) is called by the simulator starting to run
	{
		Dimension size = size();

		marinaI = createImage(size.width, size.height);
		marina.paint(marinaI.getGraphics());

		offscreenI = createImage(size.width, size.height);
		offscreenG = offscreenI.getGraphics();
	}

	public void paint(Graphics g) {
		Dimension size = size();

		if (offscreenI == null) {
			init();
		}

		offscreenG.drawImage(marinaI, 0, 0, null);
		offscreenG.setColor(frameColor);
		offscreenG.drawLine(0, 0, 0, size.height);
		boat.paint(offscreenG);
		g.drawImage(offscreenI, 0, 0, null);
	}

	void paint(Graphics g, Rectangle rect) {
		Graphics gc;

		if (offscreenI == null) {
			init();
		}

		gc = offscreenG.create();
		gc.clipRect(rect.x, rect.y, rect.width, rect.height);
		gc.drawImage(marinaI, 0, 0, null);
		boat.paint(gc);
		gc.dispose();

		gc = g.create();
		gc.clipRect(rect.x, rect.y, rect.width, rect.height);
		gc.drawImage(offscreenI, 0, 0, null);
		gc.dispose();
	}

	public void recoverFromCollision() {
		boat.recoverFromCollision();
		repaint();
	}

	public synchronized void reset() {
		boat.initPosn(initialBoatX, initialBoatY, initialBoatHeading);
		repaint();
	}

	public void run() {
		Graphics g = getGraphics();
		Rectangle rect;
		long prevTime = System.currentTimeMillis();
		long currentTime;

		while (runner != null) {
			synchronized (this) {
				currentTime = System.currentTimeMillis();
				if (currentTime > prevTime) { // allow for possibility of clock wrapping around
					rect = boat.updateTime(currentTime - prevTime);
					if (checkCollision()) {
						boat.setCollided();
						applet.boatHitDock();
					}
					paint(g, rect);
				}
				prevTime = currentTime;
			}
			try {
				Thread.sleep(frameMs);
			} catch (InterruptedException e) {
			}
		}
		g.dispose();
		runner = null;
	}

	public synchronized void setBoatRudderAngle(int rudderAngle) {
		boat.setRudderAngle(rudderAngle);
	}

	public synchronized void setBoatThrottlePosn(int throttlePosn) {
		boat.setThrottlePosn(throttlePosn);
	}

	public synchronized void setCurrent(float direction, float value) {
		boat.setCurrent(direction, value);
	}

	public synchronized void setWind(float direction, float value) {
		boat.setWind(direction, value);
	}

	public synchronized void start() {
		repaint();
		if (runner != null) {
			stop();
		}
		runner = new Thread(this);
		runner.start();
	}

	public synchronized void stop() {
		if (runner != null) {
			runner.stop();
			runner = null;
		}
	}

	public void update(Graphics g) {
		paint(g);
	}
}
