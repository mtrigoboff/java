package chooseui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mlt.SwingWindow;

@SuppressWarnings("serial")
public class ChooseUIPanel
		extends JPanel {

	private static final int SLIDER_INIT_VALUE = 50;

	private SwingWindow window;
	private JLabel uiLookLbl;
	private JPanel uiLookPanel;
	private JPanel btns;
	private JPanel controls;
	private JLabel pctLbl;
	private JTabbedPane tabs;
	private JSlider slider;

	private static abstract class TabbedPaneComponent
			extends JPanel
			implements ChangeListener {

		private String tabName;
		private Color shapeFillColor;
		private int sliderValue;

		protected Color shapeLineColor;

		private TabbedPaneComponent(String tabName, Color shapeFillColor, Color shapeLineColor, JSlider slider) {
			this.tabName = tabName;
			this.shapeFillColor = shapeFillColor;
			this.shapeLineColor = shapeLineColor;
			setBorder(BorderFactory.createLoweredBevelBorder());
			sliderValue = slider.getValue();
			slider.addChangeListener(this);
		}

		// defined by ChangeListener, called when the user moves the slider
		public void stateChanged(ChangeEvent e) {
			sliderValue = ((JSlider) e.getSource()).getValue();
			repaint();
		}

		public void paintComponent(Graphics g) {
			// always need to do this first in paintComponent override,
			// otherwise the coordinates get massively screwed up
			super.paintComponent(g);

			// compute paint coordinates for the shape
			Dimension size = getSize();
			Insets borderInsets = getBorder().getBorderInsets(this);
			int width = size.width, height = size.height;
			int shapeWidth = (width - borderInsets.left - borderInsets.right)
					* sliderValue / 100;
			int shapeHeight = (height - borderInsets.top - borderInsets.bottom)
					* sliderValue / 100;
			int x = (width - shapeWidth) / 2;
			int y = (height - shapeHeight) / 2;

			// paint shape
			g.setColor(shapeFillColor);
			paintShape(g, x, y, shapeWidth, shapeHeight);
		}

		protected abstract void paintShape(Graphics g, int x, int y, int width, int height);
	}

	public ChooseUIPanel() {
		setLayout(new BorderLayout());

		// set up top panel that shows which user interface we're seeing
		uiLookLbl = new JLabel("", SwingConstants.CENTER);
		uiLookPanel = new JPanel();
		uiLookPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		uiLookPanel.add(uiLookLbl);
		add(uiLookPanel, BorderLayout.NORTH);

		slider = new JSlider(0, 200, SLIDER_INIT_VALUE);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(50);

		// create tabbed panes
		{
			TabbedPaneComponent[] tabbedPanes = {
					new TabbedPaneComponent("Circle", Color.RED, null, slider) {
						public void paintShape(Graphics g, int x, int y, int width, int height) {
							g.fillOval(x, y, width, height);
						}
					},
					new TabbedPaneComponent("Rectangle", new Color(0, 160, 0), Color.RED, slider) // color is dark green
					{
						public void paintShape(Graphics g, int x, int y, int width, int height) {
							g.fillRect(x, y, width, height);
							g.setColor(shapeLineColor);
							g.drawRect(x, y, width, height);

							// Why does this inner rectangle flicker when the slider is moved?
							// Drawing in a JPanel is supposed to be double buffered by default.
							// Might have to do double buffering by hand. A mystery...
							g.drawRect(x + width / 4, y + height / 4, width / 2, height / 2);
						}
					},
					new TabbedPaneComponent("Star", Color.BLUE, Color.ORANGE, slider) {
						int[] starShapeX = new int[] { 0, 62, -100, 100, -62 };
						int[] starShapeY = new int[] { -92, 88, -26, -26, 88 };
						int starShapePts = starShapeX.length;

						public void paintShape(Graphics g, int x, int y, int width, int height) {
							Polygon star = new Polygon(starShapeX, starShapeY, starShapePts);
							int paneMinSize = width < height ? width : height;
							int xCenter = x + width / 2;
							int yCenter = y + height / 2;

							for (int i = 0; i < starShapePts; i++) {
								star.xpoints[i] = (star.xpoints[i] * paneMinSize) / 200;
								star.ypoints[i] = (star.ypoints[i] * paneMinSize) / 200;
							}
							star.translate(xCenter, yCenter);
							g.fillPolygon(star);
							g.setColor(shapeLineColor);
							g.drawPolygon(star);
						}
					} };

			tabs = new JTabbedPane();
			for (int i = 0; i < tabbedPanes.length; i++) {
				tabs.addTab(tabbedPanes[i].tabName, tabbedPanes[i]);
				tabs.setForegroundAt(i, tabbedPanes[i].shapeFillColor);
			}
			add(tabs, BorderLayout.CENTER);
		}

		// set up buttons
		{
			JButton prevBtn = new JButton("Prev");
			JButton nextBtn = new JButton("Next");
			JButton quitBtn = new JButton("Quit");

			// no lambda
			prevBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedTabIndex = tabs.getSelectedIndex();
					int nTabs = tabs.getTabCount();

					tabs.setSelectedIndex(selectedTabIndex == 0 ? nTabs - 1 : selectedTabIndex - 1);
				}
			});

			// using lambda
			nextBtn.addActionListener(e -> {
				int selectedTabIndex = tabs.getSelectedIndex();
				int nTabs = tabs.getTabCount();

				tabs.setSelectedIndex(selectedTabIndex == nTabs - 1 ? 0 : selectedTabIndex + 1);
			});

			quitBtn.addActionListener(e -> {
				window.dispose();
			});

			btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			btns.add(prevBtn);
			btns.add(nextBtn);
			btns.add(quitBtn);
		}

		// label that displays setting of slider
		{
			class PctLabel extends JLabel implements ChangeListener {

				private PctLabel(int pct, int posn) {
					super("", posn);
					setPct(SLIDER_INIT_VALUE);
				}

				private void setPct(int pct) {
					setText(String.format("%d%%", pct));
				}

				// defined by ChangeListener, called when the user moves the slider
				public void stateChanged(ChangeEvent e) {
					setPct(slider.getValue());
				}
			}

			pctLbl = new PctLabel(SLIDER_INIT_VALUE, SwingConstants.CENTER);
			slider.addChangeListener((ChangeListener) pctLbl);
		}

		// set up bottom panel
		controls = new JPanel(new BorderLayout());
		controls.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		controls.add(slider, BorderLayout.WEST);
		controls.add(pctLbl, BorderLayout.CENTER);
		controls.add(btns, BorderLayout.EAST);
		add(controls, BorderLayout.SOUTH);
	}

	public void setWindow(SwingWindow window) {
		this.window = window;
	}

	public void setLookName(String lookName) {
		uiLookLbl.setText(lookName + " Look and Feel");
	}

}
