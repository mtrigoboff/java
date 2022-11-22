package docksim;

import java.awt.*;

public interface Boat {

    boolean checkCollision(Point[] corners, Rectangle[] rects);

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:14:55
     * PM)
     *
     * @return mlt.docksim.GaugeDisplay
     */
    GaugeDisplay getGaugeDisplay();

    int getRudderAngleMax();

    int getRudderAngleMin();

    int getThrottlePosnMax();

    int getThrottlePosnMin();

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:21:49
     * PM)
     *
     * @param color java.awt.Color
     * @param lineColor java.awt.Color
     * @param initialX int
     * @param initialY int
     * @param initialHeading float
     * @param simulator mlt.docksim.Simulator
     * @param showGauges boolean
     */
    void init(
	    Color color,
	    Color lineColor,
	    int initialX,
	    int initialY,
	    float initialHeading,
	    Simulator simulator,
	    boolean showGauges);

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:22:43
     * PM)
     *
     * @param initialX int
     * @param initialY int
     * @param initialHeadingDeg float
     */
    void initPosn(int initialX, int initialY, float initialHeadingDeg);

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:19:44
     * PM)
     *
     * @param g java.awt.Graphics
     */
    void paint(Graphics g);

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:20:15
     * PM)
     */
    void recoverFromCollision();

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:24:14
     * PM)
     */
    void setCollided();

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:28:16
     * PM)
     *
     * @param direction float
     * @param value float
     */
    void setCurrent(float direction, float value);

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:14:07
     * PM)
     *
     * @param pxlsPerFoot float
     */
    void setPixelsPerFoot(float pxlsPerFoot);

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:25:04
     * PM)
     *
     * @param rudderAngle int
     */
    void setRudderAngle(int rudderAngle);

    void setThrottlePosn(int throttlePosn);

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:28:55
     * PM)
     *
     * @param direction float
     * @param value float
     */
    void setWind(float direction, float value);

    /**
     * Insert the method's description here. Creation date: (3/26/2003 5:23:37
     * PM)
     *
     * @return java.awt.Rectangle
     * @param intervalMs float
     */
    Rectangle updateTime(float intervalMs);
}
