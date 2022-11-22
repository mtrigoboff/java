// (c) 1999-2000 MLT Software, Inc.  All Rights Reserved.
package docksim;

import java.awt.*;

public class BoatSingleIO
	implements Boat {

    private int iii;			// debugging hack that somehow fixed the obfuscation bug that manifested in the browsers!

    private final static float pi = (float) Math.PI;
    private final static float minusPi = -pi;
    private final static float twoPi = 2 * pi;
    private final static float minusTwoPi = -twoPi;
    private final static float piOverTwo = pi / 2;
    private final static float minusPiOverTwo = -piOverTwo;
    private final static float piOverOneEighty = pi / 180;
    private final static float oneEightyOverPi = 180 / pi;

    private final static float ftPerSecPerKnot = (float) 6072.0 // ft/nautical mile
	    / (float) 3600.0;			// sec/hour

    private final static int rudderAngles = 4;
    private final static int rudderAngleMin = -rudderAngles;
    private final static int rudderAngleMax = rudderAngles;
    private final static float rudderAngleCoefficientR = (float) 0.2;
    private final static float throttlePosnCoefficientR = (float) 0.1;
    private final static float speedCoefficientR = (float) 0.1;
    private final static float dragCoefficientR = (float) 1;
    private final static float windCoefficientR = (float) 0.01;

    private final static int throttlePosns = 4;
    private final static float throttlePosnMaxSpeed = (float) 5;		// ft/sec, per throttle position
    private final static int throttlePosnMin = -throttlePosns;
    private final static int throttlePosnMax = throttlePosns;
    private final static float throttleCoefficient = (float) 0.60;
    private final static float reverseCoefficient = (float) 0.8;		// prop is less efficient in reverse
    private final static float dragFACoefficient = (float) 0.1;
    private final static float dragSideCoefficient = (float) 0.25;		// was 2
    private final static float windCoefficient = (float) 0.015;

    private float pixelsPerFoot;

    // definition of the boat polygon:  needs to be pointing up to match initial heading of 0 (= North, up)
    private final static float[] xpts0 = {-10, -10, -7, 0, 7, 10, 10};
    private final static float[] ypts0 = {10, -10, -21, -30, -21, -10, 10};

    private Polygon polygon;

    private Color color;
    private Color lineColor;

    // coordinates in the Simulator canvas, pixels
    private float x;
    private float y;

    // simulated environment (wind, current)
    private float windFtPerSec;				// ft/sec
    private float windDirection;				// radians, nautical interpretation
    private float currentX, currentY;			// ft/sec

    // when the current is changed, or we're recovering from a collision, these vars are use to start
    // the current off gently, otherwise there's too much of an unnatural "lurch"
    private final static float currentStartInterval = 6;				// seconds
    private boolean currentStart;
    private float currentStartSeconds;
    private float prevCurrentX, prevCurrentY;			// previous current values

    // boat controls
    private int throttlePosn;
    private int rudderAngle;

    // rotation-related variables
    private float speedR;									// radians/sec, rate of change of heading, rotational speed
    private float heading;								// radians, nautical interpretation, 0 == North, pi/2 == East, etc.

    // speed-related variables
    private float course;									// radians, same interpretation as heading
    private float speed;									// ft/sec
    private float speedOverGround;				// ft/sec
    private boolean speedForward;

    private boolean collided;
    private boolean collisionTooFast;
    private final static float collisionSpeedMaxOK = (float) 2.8;			// ft/sec
    private final static int[][] collisionRecoveryOffsets = {{0, -1}, {1, 0}, {0, 1}, {0, 1},
    {-1, 0}, {-1, 0}, {0, -1}, {0, -1}};
    // this array is used to check the 8 points around the boat's location when it collided,
    // looking for a place where there ISN'T a collision.  Each of the 8 entries in this array
    // specifies where to move the boat starting from its previous position - in other words,
    // each of the entries is relative to the previous entry.  If this fails, then it is tried
    // again, multiplying these offsets by 2, then 3, etc.  See recoverFromCollision.

    private GaugeDisplay gauges;
    private String[] gaugeLabels = {"speed (knots)", "speedFA", "speedSide",
	"skidAngle", "heading", "course",
	"speedX", "speedY", "pixelsPerFoot",
	"sinCourse", "cosCourse", "speedOG",
	"currentX", "currentY", "speedColl",
	"windFtPerSec", "windDirection", "",
	"windFA", "windSide"};

    private Simulator simulator;

    public boolean checkCollision(Point[] corners, Rectangle[] rects) {
	for (int i = 0; i < corners.length; i++) {
	    if (polygon.inside(corners[i].x, corners[i].y)) {
		return true;
	    }
	}

	for (int r = 0; r < rects.length; r++) {
	    for (int p = 0; p < polygon.npoints; p++) {
		if (rects[r].inside(polygon.xpoints[p], polygon.ypoints[p])) {
		    return true;
		}
	    }
	}

	return false;
    }

    private static Polygon createPolygon(float[] xf, float[] yf, float angle, int xCoord, int yCoord) {
	// angle interpretation: radians, nautical interpretation

	int n = xf.length;
	int[] x = new int[n];
	int[] y = new int[n];
	float sinAngle = (float) Math.sin(angle);
	float cosAngle = (float) Math.cos(angle);

	// rotate reference polygon to requested angle
	for (int i = 0; i < n; i++) {
	    x[i] = Math.round(xf[i] * cosAngle - yf[i] * sinAngle) + xCoord;
	    y[i] = Math.round(xf[i] * sinAngle + yf[i] * cosAngle) + yCoord;
	}

	return new Polygon(x, y, n);
    }

    public GaugeDisplay getGaugeDisplay() {
	return gauges;
    }

    public int getRudderAngleMax() {
	return rudderAngleMax;
    }

    public int getRudderAngleMin() {
	return rudderAngleMin;
    }

    public int getThrottlePosnMax() {
	return throttlePosnMax;
    }

    public int getThrottlePosnMin() {
	return throttlePosnMin;
    }

    public void init(Color color, Color lineColor,
	    int initialX, int initialY, float initialHeading,
	    Simulator simulator, boolean showGauges) {
	this.color = color;
	this.lineColor = lineColor;
	initPosn(initialX, initialY, initialHeading);
	if (showGauges) {
	    gauges = new TextGaugeDisplay(3, gaugeLabels);
	}
	this.simulator = simulator;
    }

    public void initPosn(int initialX, int initialY, float initialHeadingDeg) {
	iii = 0;
	//System.out.println("init: initialX " + initialX + ", initialY " + initialY);
	// debugging hack that somehow fixed the obfuscation bug that manifested in the browsers!

	// set x and y to 0 so that when the boat polygon is translated to
	// initialX, initialY it will end up AT initialX, initialY
	x = 0;
	y = 0;

	setHeading(initialHeadingDeg * piOverOneEighty);

	move(initialX, initialY);
	collided = false;
	collisionTooFast = false;
	speed = 0;
	speedForward = true;
	speedOverGround = 0;
	speedR = 0;
	course = 0;
	throttlePosn = 0;
	rudderAngle = 0;
	currentStart = false;
	prevCurrentX = 0;
	prevCurrentY = 0;
    }

    private static float knotsToFtPerSec(float knots) {
	return knots * ftPerSecPerKnot;
    }

    private void move(float deltaX, float deltaY) {
	// debugging hack that somehow fixed the obfuscation bug that manifested in the browsers!
	if (iii > 0) {
	    System.out.print("move: x " + x + ", y " + y + ", dX " + deltaX + ", dY " + deltaY);
	}

	x += deltaX;
	y += deltaY;

	// debugging hack that somehow fixed the obfuscation bug that manifested in the browsers!
	if (iii > 0) {
	    System.out.println(" -> x " + x + ", y " + y);
	    iii--;
	}

	polygon = createPolygon(xpts0, ypts0, heading, (int) x, (int) y);
    }

    private static float newScalarSpeed(float speed, float acceleration, float drag, float seconds) {
	return speed + (acceleration + (speed > 0 ? -1 : 1) * drag) * seconds;
    }

    private static float normalizeRadians(float r) {
	if (r < 0) {
	    r += twoPi;
	} else if (r >= twoPi) {
	    r -= twoPi;
	}

	return r;
    }

    public void paint(Graphics g) {
	Color color = collided
		? collisionTooFast ? Color.red : Color.green
		: this.color;

	g.setColor(color);
	g.fillPolygon(polygon);
	g.setColor(lineColor);
	g.drawPolygon(polygon);
    }

    public void recoverFromCollision() {
	int[] offsets;

	// spiral outwards, looking for a place where we aren't collided with the dock we hit
	clearedDock:
	for (int i = 1; i <= 4; i++) {
	    // test positions in this iteration of the outwards "spiral"
	    for (int j = 0; j < 8; j++) {
		offsets = collisionRecoveryOffsets[j];
		move(i * offsets[0], i * offsets[1]);
		if (!simulator.checkCollision()) {
		    break clearedDock;
		}
	    }
	    // put boat back in center of "spiral" in preparation for next iteration
	    move(i, i);
	}

	speed = 0;
	speedForward = true;
	speedOverGround = 0;
	speedR = 0;

	currentStart = true;
	currentStartSeconds = currentStartInterval;
	collisionTooFast = false;
	collided = false;
	prevCurrentX = 0;					// hack to give them a chance to get going...
	prevCurrentY = 0;					// ... when they start after a collision

	if (gauges != null) {
	    gauges.setGauge(14, 0);
	}
    }

    public void setCollided() {
	if (!collided) {
	    collided = true;
	    collisionTooFast = speedOverGround > collisionSpeedMaxOK;
	    currentStart = false;
	    if (gauges != null) {
		gauges.setGauge(14, speedOverGround);
	    }
	}
    }

    public synchronized void setCurrent(float direction /* radians */, float value /* knots */) // direction is nautical interpretation of angle
    {
	float currentFtPerSec = knotsToFtPerSec(value);

	prevCurrentX = currentX;
	prevCurrentY = currentY;

	currentX = currentFtPerSec * (float) Math.sin(direction);
	currentY = -currentFtPerSec * (float) Math.cos(direction);

	currentStart = true;
	currentStartSeconds = currentStartInterval;

	if (gauges != null) {
	    gauges.setGauge(12, currentX);
	    gauges.setGauge(13, currentY);
	}
    }

    private void setHeading(float heading /* radians */) {
	this.heading = normalizeRadians(heading);
	polygon = createPolygon(xpts0, ypts0, this.heading, (int) x, (int) y);
    }

    public void setPixelsPerFoot(float pxlsPerFoot) {
	pixelsPerFoot = pxlsPerFoot;
    }

    public synchronized void setRudderAngle(int rudderAngle) {
	this.rudderAngle = rudderAngle;
    }

    public synchronized void setThrottlePosn(int throttlePosn) {
	this.throttlePosn = throttlePosn;
    }

    public synchronized void setWind(float direction /* radians */, float value /* knots */) // direction is nautical interpretation of angle
    {
	windFtPerSec = knotsToFtPerSec(value);
	windDirection = direction;

	if (gauges != null) {
	    gauges.setGauge(15, windFtPerSec);
	    gauges.setGauge(16, windDirection * oneEightyOverPi);
	}
    }

    private void updatePhysics(float seconds) {
	float windAngle = windAngle();
	// angle between windDirection and boat's heading, normalized to [-pi .. pi]

	// compute new heading
	{
	    float accelerationR;
	    float dragR;

	    accelerationR = (speedCoefficientR * speed * (speedForward ? +1 : -1)
		    + throttlePosnCoefficientR * throttlePosn)
		    * rudderAngleCoefficientR * rudderAngle
		    + windFtPerSec * (float) Math.sin(windAngle) * windCoefficientR;
	    dragR = Math.abs(dragCoefficientR * speedR);
	    speedR = newScalarSpeed(speedR, accelerationR, dragR, seconds);
	    setHeading(heading + speedR * seconds);
	}

	// compute new course and speed, based on previous course, speed, throttle setting, and drag
	{
	    float throttlePosnAbs = Math.abs(throttlePosn);
	    float throttleDirection = throttlePosn >= 0 ? 1 : -1;
	    float skidAngle;							// radians
	    float acceleration;
	    float speedFA, dragFA;
	    float speedSide, dragSide;
	    float windFA, windSide;
	    float throttlePosnSpeed = throttlePosnMaxSpeed * throttlePosnAbs;

	    // normalize skidAngle so that 0 is straight ahead, +90 is skidding to the right,
	    // -90 is skidding to the left, and 180 is straight back
	    skidAngle = course - heading;
	    if (skidAngle <= minusPi) {
		skidAngle += twoPi;
	    } else if (skidAngle > pi) {
		skidAngle -= twoPi;
	    }

	    speedFA = speed * (float) Math.cos(skidAngle);
	    dragFA = Math.abs(dragFACoefficient * speedFA);

	    windFA = windFtPerSec * (float) Math.cos(windAngle);
	    windSide = windFtPerSec * (float) Math.sin(windAngle);

	    acceleration = throttleCoefficient * throttlePosnAbs
		    * (throttlePosnSpeed - Math.min(Math.abs(speedFA), throttlePosnSpeed));
	    // the idea here is that since this is a propellor and not a rocket engine,
	    // thrust for any throttle setting is limited by a theoretical maximum speed
	    // determined by propellor rpm and blade pitch
	    if (throttlePosn < 0) {
		acceleration *= reverseCoefficient;
	    }
	    speedFA = newScalarSpeed(speedFA, acceleration * throttleDirection + windFA * windCoefficient, dragFA, seconds);

	    speedSide = speed * (float) Math.sin(skidAngle);
	    dragSide = Math.abs(dragSideCoefficient * speedSide);
	    speedSide = newScalarSpeed(speedSide, windSide * windCoefficient, dragSide, seconds);

	    // compute new skid angle
	    if (speedFA == 0) {
		skidAngle = speedSide == 0 ? 0
			: speedSide > 0 ? piOverTwo
				: minusPiOverTwo;
	    } else {
		skidAngle = Math.abs((float) Math.atan(speedSide / speedFA));
		// Math.atan returns values in the range [0..90), so we need to restore the appropriate
		// sign and add 90 if needed, since our ultimate target range is (-180..180]
		if (speedFA < 0) {
		    skidAngle = pi - skidAngle;
		}
		if (speedSide < 0) {
		    skidAngle *= -1;
		}
	    }

	    // compute new course
	    course = normalizeRadians(heading + skidAngle);

	    if (gauges != null) {
		gauges.setGauge(3, skidAngle * oneEightyOverPi);
		gauges.setGauge(4, heading * oneEightyOverPi);
		gauges.setGauge(5, course * oneEightyOverPi);
		gauges.setGauge(1, speedFA);
		gauges.setGauge(2, speedSide);
		gauges.setGauge(18, windFA);
		gauges.setGauge(19, windSide);
	    }

	    // compute new speed
	    speed = (float) Math.pow(Math.pow(speedFA, 2) + Math.pow(speedSide, 2), 0.5);
	    speedForward = speedFA >= 0;

	    if (gauges != null) {
		gauges.setGauge(0, speed / ftPerSecPerKnot);
	    }
	}

	// move the boat	
	{
	    float sinCourse = (float) Math.sin(course);
	    float cosCourse = (float) -Math.cos(course);
	    // negative because y axis increases downwards, and cos of 0.0 (i.e. North, up) is +1.0
	    float speedX = speed * sinCourse;
	    float speedY = speed * cosCourse;
	    float pfs = pixelsPerFoot * seconds;
	    float currentFraction;

	    if (currentStart) {
		currentStartSeconds -= seconds;
		if (currentStartSeconds <= 0) {
		    currentStart = false;
		    speedX += currentX;
		    speedY += currentY;
		} else {
		    currentFraction = (currentStartInterval - currentStartSeconds) / currentStartInterval;
		    speedX += currentX * currentFraction + prevCurrentX * (1 - currentFraction);
		    speedY += currentY * currentFraction + prevCurrentY * (1 - currentFraction);
		}
		//System.out.println("speedX " + speedX + ", speedY " + speedY);
	    } else {
		speedX += currentX;
		speedY += currentY;
	    }

	    move(speedX * pfs, speedY * pfs);
	    speedOverGround = (float) Math.pow(Math.pow(speedX, 2) + Math.pow(speedY, 2), 0.5);

	    if (gauges != null) {
		gauges.setGauge(6, speedX);
		gauges.setGauge(7, speedY);
		gauges.setGauge(8, pixelsPerFoot);
		gauges.setGauge(9, sinCourse);
		gauges.setGauge(10, cosCourse);
		gauges.setGauge(11, speedOverGround);
	    }
	}
    }

    public synchronized Rectangle updateTime(float intervalMs) {
	Rectangle prevRect = polygon.getBoundingBox();
	Rectangle rect;

	if (collided) {
	    return prevRect;
	}

	updatePhysics(intervalMs / 1000);

	rect = prevRect.union(polygon.getBoundingBox());
	rect.width += 1;
	rect.height += 1;

	return rect;
    }

    private float windAngle() {
	// compute windAngle, normalized to [-pi .. pi]

	float windAngle;		// angle between windDirection and boat's heading
	long time;

	windAngle = windDirection - heading;

	if (windAngle == minusPi || windAngle == minusPi) {
	    // don't allow boat to stay stably pointed into the wind
	    time = System.currentTimeMillis();
	    windAngle = (time & 0x01) == 1 ? pi - (float) 0.04 : minusPi + (float) 0.04;
	} else if (windAngle < minusPi) {
	    windAngle = twoPi + windAngle;
	} else if (windAngle > pi) {
	    windAngle = minusTwoPi + windAngle;
	}

	return windAngle;
    }
}
