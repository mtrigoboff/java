package draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
class DrawPanel
    extends JPanel
{
    DrawPanel()
    {
        addMouseListener(
            new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    paintSpot(Color.blue, e.getX(), e.getY());
                }
                public void mouseReleased(MouseEvent e)
                {
                    paintSpot(Color.red, e.getX(), e.getY());
                }
            });
        addMouseMotionListener(
            new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {
                    paintSpot(Color.green, e.getX(), e.getY());
                }
            });

        // erase window when user types 'e'
        final String ERASE_KEY = "ERASE_KEY";
		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), ERASE_KEY);
		getActionMap().put(ERASE_KEY, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
    }

    private void paintSpot(Color color, int x, int y)
    {
        Graphics    g = getGraphics();

        g.setColor(color);
        g.fillOval(x, y, 10, 10);
    }

    public void paint(Graphics g)
    {
        Dimension   size = getSize();

        g.setColor(Color.yellow);
        g.fillRect(0, 0, size.width, size.height);
    }
}
