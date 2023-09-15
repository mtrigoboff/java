
// (c) 1995 - 1998 MLT Software, Inc.  All Rights Reserved.
import java.io.*;

final class ShadowCurve {

	static final int curveWidth = 512;

	static String tableDirectoryPath;
	static float pixelsPerMin; // pixels/day / mins/day

	public int[] mapY;
	public int[] oldMapY;

	int index;
	int offset;
	int[] curveY;

	ShadowCurve(DateInfo dateInfo) {
		curveY = new int[curveWidth];
		oldMapY = new int[curveWidth];
		mapY = new int[curveWidth];

		index = dateInfo.shadowCurveIndex;
		load();

		// slide shadow curve horizontally to reflect the current time
		offset = computeOffset(dateInfo);
		slide();
		// System.out.println("ShadowCurve new: offset " + offset);
	}

	private static int computeOffset(DateInfo dateInfo) {
		int shadowOffset;

		// to start out, either midnight or noon is aligned with the prime meridian.
		// This is by definition, because of the way the shadow curve tables are built.
		// By subtracting timeZoneOffset, we will have noon or midnight aligned with
		// our current location.
		shadowOffset = (int) (-dateInfo.timeZoneOffset * pixelsPerMin);

		// if nightTop is true, then noon is aligned with our current location.
		// By adding curveWidth/2, we shift 12 hours and will have midnight aligned
		// with our current location, just as it is with nightMap == false.
		if (dateInfo.nightTop) {
			shadowOffset += curveWidth / 2;
		}

		// now we slide left by an amount equivalent to the number of minutes
		// since midnight represented by the current time
		shadowOffset -= (dateInfo.hour * 60 + dateInfo.minute) * pixelsPerMin;

		// normalize to [0 .. curveWidth-1]
		while (shadowOffset < 0 || shadowOffset >= curveWidth) {
			if (shadowOffset >= curveWidth) {
				shadowOffset -= curveWidth;
			} else if (shadowOffset < 0) {
				shadowOffset += curveWidth;
			}
		}

		// System.out.println("ShadowCurve:computeOffset " + shadowOffset);
		return shadowOffset;
	}

	static void init() {
		pixelsPerMin = (float) (curveWidth / 1440.0); // pixels/day / mins/day
		try {
			tableDirectoryPath = "tables/";
		} catch (Exception e) {
			System.out.print("ShadowCurve.init: ");
			System.out.println(e.toString());
		}
	}

	private void load() {
		String scTablePath;
		File scTable;
		InputStream scTableInput;
		DataInputStream scTableData;

		try {
			scTablePath = tableDirectoryPath + "sctbl" + Integer.toString(index);
			scTable = new File(scTablePath);
			scTableInput = new FileInputStream(scTable);
			scTableData = new DataInputStream(scTableInput);
			for (int i = 0; i < curveWidth; i++) {
				curveY[i] = scTableData.readShort();
			}
		} catch (Exception e) {
			System.out.print("ShadowCurve.load: ");
			System.out.println(e.toString());
		}
	}

	private void saveMapY() {
		// save old mapY values
		for (int x = 0; x < curveWidth; x++) {
			oldMapY[x] = mapY[x];
		}
	}

	private void slide() {
		for (int x = 0, x2 = offset; x < curveWidth; x++) {
			mapY[x2] = curveY[x];
			if (++x2 >= curveWidth) {
				x2 = 0;
			}
		}
	}

	boolean update(DateInfo dateInfo) {
		boolean loadedNewCurve = false;
		boolean offsetChanged = false;
		int newOffset;

		// load new curve, compute new offset if necessary
		if (dateInfo.shadowCurveIndex != index) {
			index = dateInfo.shadowCurveIndex;
			load();
			loadedNewCurve = true;
			// System.out.println("ShadowCurve.update: index " + index);
		}
		newOffset = computeOffset(dateInfo);

		if (newOffset != offset || loadedNewCurve) {
			saveMapY();
			offset = newOffset;
			slide();
			// System.out.println("ShadowCurve.update: offset " + offset);
			offsetChanged = true;
		}

		// tell them what happened
		return loadedNewCurve || offsetChanged;
	}
}
