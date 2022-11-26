
// (c) 1995 - 1998 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

final public class SunClockApplet
	extends javax.swing.JPanel {

    SunClockCanvas scCanvas;

    URL codeBase;

    BufferedImage dayMapImage;
    BufferedImage nightMapImage;

    int appletHeight;
    int appletWidth;

    Object imageLock = new Object();

    public void init() {
	MediaTracker mt = new MediaTracker(this);

	// find and preload the map images
	try {
	    dayMapImage = ImageIO.read(new File("src/daymap.gif"));
	    mt.addImage(dayMapImage, 0);
	    nightMapImage = ImageIO.read(new File("src/niteMap.gif"));
	    mt.addImage(nightMapImage, 0);
	    mt.waitForAll();
	} catch (IOException e) {
	    throw new Error("SunClockApplet.init: couldn't load images");
	} catch (InterruptedException e) {
	    throw new Error("SunClockApplet.init: interrupted exception");
	}

	// size the applet to the images (day and night are the same size)
	appletWidth = dayMapImage.getWidth(this) + 2;
	appletHeight = dayMapImage.getHeight(this) + 2;
	resize(appletWidth, appletHeight);

	setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	// after much frustrating experimentation, it turns out that this is the
	// best way to add a panel to an applet so that there are no unwanted
	// margins "helpfully" provided for us by the API
	scCanvas = new SunClockCanvas(codeBase, dayMapImage, nightMapImage);
	add("Sun Clock", scCanvas);
	// adding the name argument makes the panel get added to the layout mgr
	// (this was discovered by examination of the Container class code)
    }

    public synchronized void start() {
	scCanvas.start();
    }

    public synchronized void stop() {
	scCanvas.stop();
    }
}
