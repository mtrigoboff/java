package circlepainter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")

class CirclePanel
    extends JPanel
    implements ItemListener
{
    static class RadioBtn
        extends JRadioButton
    {
        Color       color;          // color for the circle when this radio button is selected
        
        RadioBtn(String name, Color color, ButtonGroup btnGroup)
        {
            super(name);
            this.color = color;
            btnGroup.add(this);
        }
    }
    
    private Color	color;
    private int		circleSizePct;   // diameter as a percent of size

    CirclePanel(Color color, int circleSizePct)
    {
        this.color = color;
        this.circleSizePct = circleSizePct;
    }

    public void setCircleSize(int circleSizePct)
    {
        this.circleSizePct = circleSizePct;
        repaint();
    }
    
    public Color getColor()
    {
        return color;
    }

    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	
        Dimension   size = getSize();
        int         width = size.width;
        int         height = size.height;
        int         circleWidth = width * circleSizePct / 100;
        int         circleHeight = height * circleSizePct / 100;

        g.setColor(color);
        g.fillOval((width - circleWidth) / 2, (height - circleHeight) / 2, circleWidth, circleHeight);
    }

    public void itemStateChanged(ItemEvent e)
    {
        color = ((RadioBtn) e.getItemSelectable()).color;
        repaint();
    }
}
