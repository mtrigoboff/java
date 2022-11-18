package circlepainter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")

class FramedPanel
    extends JPanel
{
	public enum Level {LOW, HIGH};
	
    final private static Level	LEVEL_DEFAULT = Level.LOW;
    final private static int    MARGIN = 8;

    private Color     topLeftBorder;
    private Color     bottomRightBorder;

    public FramedPanel()
    {
    	setupBorder();
        setShadingColors(LEVEL_DEFAULT);
    }

    public FramedPanel(Level level)
    {
    	setupBorder();
        setShadingColors(level);
    }

    public FramedPanel(LayoutManager layoutMgr)
    {
        super(layoutMgr);
    	setupBorder();
        setShadingColors(LEVEL_DEFAULT);
    }

    public FramedPanel(Level level, LayoutManager layoutMgr)
    {
        super(layoutMgr);
    	setupBorder();
        setShadingColors(level);
    }

	void setupBorder()
	{
		setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
	}
	
    private void setShadingColors(Level level)
    {
        if (level == Level.LOW) {
            topLeftBorder = Color.darkGray;
            bottomRightBorder = Color.white;
            }
        else {     // Level.HIGH
            topLeftBorder = Color.white;
            bottomRightBorder = Color.darkGray;
            }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); // needed so that Graphics coordinates are properly set up

        Dimension   size = getSize();
        int         width = size.width - 1;
        int         height = size.height - 1;

        g.setColor(topLeftBorder);
        g.drawLine(0, 0, width, 0);
        g.drawLine(0, 0, 0, height);
        g.setColor(bottomRightBorder);
        g.drawLine(width, 0, width, height);
        g.drawLine(0, height, width, height);
    }
}
