package snow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Snow extends JPanel {
	static private Random	r = new Random();

	private int				x, y, rad, i;
	private BufferedImage	image;
	private Graphics2D		g2d;
	private Timer 			timer;

	Snow() {
		image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
		g2d = (Graphics2D) image.getGraphics();
		setBackground(Color.black);
		g2d.setColor(Color.white);
		i = 0;
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				iterate();
			}
		};
		timer = new Timer(10, listener);
		timer.start();
	}

	public void iterate() {
		x = r.nextInt(600);
		y = r.nextInt(600);
		rad = r.nextInt(5) + 5;
		g2d.fillOval(x, y, rad, rad);
		repaint();
		i++;
		if (i == 1000) {
			timer.stop();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public static void main(String[] args) {
		JFrame sboard = new JFrame();
		sboard.setSize(600, 600);
		sboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Snow mypanel = new Snow();
		sboard.add(mypanel);
		sboard.setVisible(true);
	}

}
