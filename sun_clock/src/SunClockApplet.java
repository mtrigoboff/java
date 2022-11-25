
// (c) 1995 - 1998 MLT Software, Inc.  All Rights Reserved.

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;

final public class SunClockApplet 
	extends java.applet.Applet
{
	SunClockCanvas	scCanvas;

	URL							codeBase;

	Image						dayMapImage;
	Image						nightMapImage;

	int							appletHeight;
	int							appletWidth;

	Object					imageLock = new Object();

	public void init ()
	{
		MediaTracker		mt = new MediaTracker(this);
		boolean					devMode = false;

		// show msg while we're loading, initializing, etc.
		showStatus("Sun Clock applet preparing to run...");

		{
		String		devModeParm = getParameter("dev");
		
		if (devModeParm != "")
			devMode = Boolean.valueOf(devModeParm).booleanValue();
		}
		
		codeBase = getCodeBase();

		// check for proper docBase
		{
		URL				docBase = getDocumentBase();
		boolean		ok;
		
		ok = devMode ? true : docBase.getHost().equals("www.europa.com");
		if (! ok)
			throw new java.lang.InternalError();
		}
		
		// find and preload the map images
		try {
			dayMapImage = getImage(codeBase, getParameter("dayMap"));
			mt.addImage(dayMapImage, 0);
			nightMapImage = getImage(codeBase, getParameter("nightMap"));
			mt.addImage(nightMapImage, 0);
			mt.waitForAll();
			}
		catch (InterruptedException e) {
			throw new Error("SunClockApplet.init: couldn't load images");
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
	public synchronized void start ()
	{
		scCanvas.start();
	}
	public synchronized void stop ()
	{
		scCanvas.stop();
	}
}