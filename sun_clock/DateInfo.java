// (c) 1995 - 2022 Michael Trigoboff.  All Rights Reserved.

import java.util.*;

final class DateInfo
		extends Object {

	public int year;
	public int month;
	public int day;
	public int hour;
	public int minute;
	public int timeZoneOffset;
	public boolean nightTop;
	public int shadowCurveIndex;

	DateInfo() {
		// get date info that we need
		{
			Date date;

			date = new Date();
			timeZoneOffset = timeZoneOffset(date);
			year = date.getYear() + 1900;
			month = (date.getMonth() + 1);
			day = date.getDate();
			hour = date.getHours();
			minute = date.getMinutes();
		}

		// set top or bottom shadow
		nightTop = computeNightTop(month, day);

		// get shadow curve table index for today's date
		shadowCurveIndex = computeShadowCurveIndex(month, day);
	}

	/*
	 * public void print () {
	 * System.out.print("DateInfo new: year " + year);
	 * System.out.print(", month " + month + ", day " + day);
	 * System.out.println(", hour " + hour + ", minute " + minute);
	 * System.out.print("  timeZoneOffset " + timeZoneOffset);
	 * System.out.print(", nightTop " + nightTop);
	 * System.out.println(", shadowCurveIndex " + shadowCurveIndex);
	 * }
	 */

	private boolean computeNightTop(int month, int day) {
		boolean nightTop;

		switch (month) { // decide whether North or South shadow
			case 10:
			case 11:
			case 12:
			case 1:
			case 2: // Oct - Feb
				nightTop = true;
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8: // Apr - Aug
				nightTop = false;
				break;
			case 3: // Mar
				nightTop = (day < 20 ? true : false);
				break;
			case 9: // Sep
				nightTop = (day > 23 ? true : false);
				break;
			default: // error
				nightTop = true;
				break;
		}
		return nightTop;
	}

	private int computeShadowCurveIndex(int month, int day) {
		int daysFromEquinox;

		// compute days from closest equinox, for use as index for choosing shadow curve
		switch (month) {
			case 1:
				daysFromEquinox = 79 - day;
				break;
			case 2:
				daysFromEquinox = 48 - day;
				break;
			case 3:
				daysFromEquinox = day < 20 ? 20 - day : day - 20;
				break;
			case 4:
				daysFromEquinox = day + 11;
				break;
			case 5:
				daysFromEquinox = day + 41;
				break;
			case 6:
				daysFromEquinox = day < 21 ? day + 72 : 115 - day;
				if (daysFromEquinox > 89) {
					daysFromEquinox = 89;
				}
				break;
			case 7:
				daysFromEquinox = 85 - day;
				break;
			case 8:
				daysFromEquinox = 54 - day;
				break;
			case 9:
				daysFromEquinox = day < 23 ? 23 - day : day - 23;
				break;
			case 10:
				daysFromEquinox = day + 7;
				break;
			case 11:
				daysFromEquinox = day + 38;
				break;
			case 12:
				daysFromEquinox = day < 21 ? day + 68 : 110 - day;
				if (daysFromEquinox > 89) {
					daysFromEquinox = 89;
				}
				break;
			default:
				daysFromEquinox = 0; // should never get here, compiler demands this
		}
		return daysFromEquinox;
	}

	private int timeZoneOffset(Date date) {
		int tzOffset;
		long now, gmt;
		String nowGMTStr;

		tzOffset = date.getTimezoneOffset();
		if (tzOffset == 0) { // suspicious, could be result of error on Macs...

			// ...so what we do is get a Date equivalent to now, generate its GMT string,
			// cut the characters " GMT" off the end of this string, and parse it into a
			// long.
			// Then we subtract "now" from it, to get the time offset from local time
			// to GMT. We divide this by 60,000 to convert from milliseconds to minutes.
			now = System.currentTimeMillis();
			nowGMTStr = (new Date(now)).toGMTString();
			gmt = Date.parse(nowGMTStr.substring(0, nowGMTStr.length() - 4));
			tzOffset = (int) ((gmt - now) / (1000 * 60));
		}
		return tzOffset;
	}
}
