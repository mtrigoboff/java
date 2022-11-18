package animate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AnimatePanel
    extends JPanel
    implements Runnable
{
	private final static int    GRID_SPACING = 40;
    private final static int    SHAPE_SIZE = 60;
    private final static int    SHAPE_MOVE_DELTA = 1;

    private Image       offscreenI;                                     // offscreen image for painting
    private Graphics    offscreenG;                                     // graphics object for offscreen image
    private boolean     firstPaint = true;
    private boolean     animate = false;
    private Point       shapeLocation;
    private Color       shapeColor;

    public AnimatePanel()
    {
        addMouseListener(
            new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    synchronized (AnimatePanel.this) {
                        if (! animate) {
                            shapeLocation = new Point(e.getX() - SHAPE_SIZE / 2, e.getY() - SHAPE_SIZE / 2);
                            animate = true;
                            new Thread(AnimatePanel.this).start();
                            }
                        else {
                            animate = false;
                            repaint();
                            }
                        }
                }
            });
        shapeColor = alphaColor(Color.red, 150);
    }

    private Color alphaColor(Color color, int alpha)
    {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public void run()
    {
        for (;;) {
            synchronized (this) {
                if (! animate)
                    break;
                try {
                	moveShape(SHAPE_MOVE_DELTA, SHAPE_MOVE_DELTA);
                } catch (NullPointerException e) { }
                }
            try { Thread.sleep(25); } catch (InterruptedException e) { }
            }
    }

    public void closing()
    {
    	animate = false;
    }
    
    private void moveShape(int dx, int dy)
    {
        Rectangle     oldRect;
        Rectangle     newRect;
        Rectangle     clipRect;
        Graphics      g = getGraphics();            // get graphics object for this canvas

        oldRect = shapeRect();
        newRect = new Rectangle(oldRect);
        newRect.translate(dx, dy);
        shapeLocation.translate(dx, dy);
        clipRect = oldRect.union(newRect);
        offscreenG.setClip(clipRect);
        paintOffscreen();
        g.setClip(clipRect);
        paintOnscreen(g);
    }

    private Rectangle shapeRect()
    {
        return new Rectangle(shapeLocation.x, shapeLocation.y, SHAPE_SIZE, SHAPE_SIZE);
    }

    private void drawShape(Graphics g)
    {
        int     x, y;

        if (animate) {
            x = shapeLocation.x;
            y = shapeLocation.y;

            // draw shape
            g.setColor(shapeColor);
            g.fillRect(x, y, SHAPE_SIZE, SHAPE_SIZE);
            }
    }

    private void paintOffscreen()
    {
        Dimension   size = getSize();
        int         width = size.width;
        int         height = size.height;
        String      str = new String("test");
        FontMetrics	fontMetrics;
        int         strWidth;
        // int			fontAscent;

        if (firstPaint) {
            // do this here because at this point we are all set up (size, etc.)
            // and ready to start painting
            offscreenI = createImage(width, height);
            offscreenG = offscreenI.getGraphics();
            firstPaint = false;
            }

        // draw background color
        offscreenG.setColor(Color.yellow);
        offscreenG.fillRect(0, 0, width, height);

        // draw grid
        offscreenG.setColor(Color.green);
        // vertical lines
        for (int x = 0; x < width; x += GRID_SPACING)
            offscreenG.drawLine(x, 0, x, height);
        // horizontal lines
        for (int y = 0; y < height; y += GRID_SPACING)
            offscreenG.drawLine(0, y, width, y);

        // draw string centered in window
        offscreenG.setColor(Color.blue);
        offscreenG.setFont(new Font("Courier", Font.BOLD, 72));
        fontMetrics = offscreenG.getFontMetrics();
        strWidth = fontMetrics.stringWidth(str);
        //fontAscent = fontMetrics.getAscent();
        offscreenG.drawString(str, width / 2 - strWidth / 2, height / 2 + 16 /*- fontAscent*/);

        // draw oval
        offscreenG.setColor(Color.blue);
        offscreenG.drawOval(0, 0, width - 10, height - 10);

        // draw animated shape
        drawShape(offscreenG);
    }

    void paintOnscreen(Graphics g)
    {
        // draw offscreen image onto the visible window
        g.drawImage(offscreenI, 0, 0, null);
    }
    
    public void paint(Graphics g)
    {
        paintOffscreen();
        paintOnscreen(g);
    }

    public void update(Graphics g)
    {
        // keep window from being cleared to gray before painting
        paint(g);
    }
}
