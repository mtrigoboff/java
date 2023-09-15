
// (c) 1995 - 1998 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

final public class SunClockApplet
		extends javax.swing.JPanel {

	SunClockCanvas scCanvas;

	BufferedImage dayMapImage;
	BufferedImage nightMapImage;

	Object imageLock = new Object();

	public Dimension init() {
		MediaTracker mt = new MediaTracker(this);
		Dimension mapDimensions = new Dimension();

		// find and preload the map images
		try {
			dayMapImage = ImageIO.read(new File("sun_clock/maps/daymap.gif"));
			mt.addImage(dayMapImage, 0);
			nightMapImage = ImageIO.read(new File("sun_clock/maps/niteMap.gif"));
			mt.addImage(nightMapImage, 0);
			mt.waitForAll();
		} catch (IOException e) {
			throw new Error("SunClockApplet.init: couldn't load images");
		} catch (InterruptedException e) {
			throw new Error("SunClockApplet.init: interrupted exception");
		}

		// report image width, height (day and night are the same size)
		mapDimensions.width = dayMapImage.getWidth(this) + 2;
		mapDimensions.height = dayMapImage.getHeight(this) + 2;

		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		// after much frustrating experimentation, it turns out that this is the
		// best way to add a panel to an applet so that there are no unwanted
		// margins "helpfully" provided for us by the API
		scCanvas = new SunClockCanvas(dayMapImage, nightMapImage);
		add("Sun Clock", scCanvas);
		// adding the name argument makes the panel get added to the layout mgr
		// (this was discovered by examination of the Container class code)

		return mapDimensions;
	}

	public synchronized void start() {
		scCanvas.start();
	}

	public synchronized void stop() {
		scCanvas.stop();
	}
}
