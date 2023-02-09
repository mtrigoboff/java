// (c) 2000-2003 MLT Software, Inc.  All Rights Reserved.

import docksim.util.PersistentStatusMsgClient;
import docksim.util.PersistentStatusMsg;
import docksim.VectorInput;
import docksim.Boat;
import docksim.FramedPanel;
import docksim.Simulator;
import docksim.BoatSingleIO;
import docksim.GaugeDisplay;
import docksim.SimulatorApplet;
import docksim.HelpText;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class DockingSimulator
		extends JPanel
		implements SimulatorApplet, PersistentStatusMsgClient {

	private final static int windIndex = 0;
	private final static int currentIndex = 1;

	private final static Color frameColor = Color.gray;

	private final static Color waterColor = new Color(200, 240, 255);
	private final static Color boatColor = Color.orange;
	private final static Color boatLineColor = boatColor.darker();
	private final static Color dockColor = new Color(180, 150, 70);
	private final static Color dockLineColor = dockColor.darker();
	private final static float pixelsPerFoot = ((float) 4.0) / (float) 3.0;
	private final static int dockBoardWidth = 8;
	private final static Rectangle[] dockRects = {
			new Rectangle(40, 40, 320, 20),
			new Rectangle(40, 60, 20, 120),
			new Rectangle(90, 60, 20, 60),
			new Rectangle(140, 60, 20, 60),
			new Rectangle(190, 60, 20, 60),
			new Rectangle(240, 60, 20, 60),
			new Rectangle(290, 60, 20, 60),
			new Rectangle(340, 60, 20, 60),
			new Rectangle(40, 180, 260, 20), };

	private final static String forwardStr = "Forward";
	private final static String neutralStr = "Neutral";
	private final static String reverseStr = "Reverse";

	private final static String portStr = "Port";
	private final static String amidshipsStr = "Amidships";
	private final static String starboardStr = "Starboard";

	private int initialBoatX = 410;
	private int initialBoatY = 340;
	private float initialBoatHeading = 0;
	// degrees, nautical interpretation, 0 == North, etc.

	private boolean boatHitDock;

	private Button startOverBtn;
	private final static String startOverBtnLabel = "Start Over";

	private Button zeroBtn;
	private final static String zeroBtnLabel = "Zero";

	private int throttlePosn;
	private int rudderAngle;

	private TextField throttlePosnDisplay;
	private TextField rudderAngleDisplay;

	private VectorInput windControl, currentControl;

	private GaugeDisplay gaugeDisplay;

	private Simulator simulator;
	private Boat boat = new BoatSingleIO();

	private String[] vectorHelp = {
			"To set current",
			"or wind, click",
			"in labeled circle.",
			"Wind or current",
			"direction is from",
			"center of circle",
			"to the edge." };

	private String[] bottomHelp = {
			"Use UP and DOWN arrow keys to control the boat's throttle. Use LEFT and RIGHT arrow keys to steer.",
			"Position of throttle and rudder are shown at top of window.  Hit DOWN arrow when in Neutral for Reverse."
			// "Hit the \"s\" key or the \"Start Over\" button to restart."
	};

	private final static String versionStr = "Docking Simulator (c) 2000-2008 MLT Software, Inc.  All Rights Reserved.  Version 1.07";

	public synchronized boolean action(Event event, Object what) {
		if (event.target == startOverBtn) {
			startOver();
		} else if (event.target == zeroBtn) {
			zeroSettings();
		}
		requestFocus();
		return true;
	}

	public void blankStatus() {
		showStatus(" ");
	}

	public void boatHitDock() {
		setThrottlePosn(0);
		boatHitDock = true;
	}

	public synchronized void init() {
		Label label;
		Panel settings;
		Panel displays;
		Panel displaysCenter;
		Panel startOver;

		setLayout(new BorderLayout());

		{
			boolean showGauges = false;

			simulator = new Simulator(
					waterColor,
					boatColor,
					boatLineColor,
					frameColor,
					dockRects,
					dockColor,
					dockLineColor,
					dockBoardWidth,
					boat,
					initialBoatX,
					initialBoatY,
					initialBoatHeading,
					pixelsPerFoot,
					50,
					this,
					showGauges);
		}

		displays = new FramedPanel(
				frameColor,
				Color.lightGray,
				new Insets(1, 1, 1, 1),
				new BorderLayout());
		displays.setBackground(Color.lightGray);

		displaysCenter = new Panel();
		displaysCenter.setBackground(Color.lightGray);
		displaysCenter.setLayout(new FlowLayout(FlowLayout.CENTER));

		label = new Label("Rudder", Label.RIGHT);
		label.setForeground(Color.black);
		displaysCenter.add(label);
		rudderAngleDisplay = new TextField(
				Math.max(portStr.length(), starboardStr.length()) + 2);
		// + 2 for space and number
		rudderAngleDisplay.setBackground(Color.lightGray);
		rudderAngleDisplay.setForeground(Color.blue);
		setRudderAngle(0);
		displaysCenter.add(rudderAngleDisplay);

		label = new Label("Throttle", Label.RIGHT);
		label.setForeground(Color.black);
		displaysCenter.add(label);
		throttlePosnDisplay = new TextField(
				Math.max(
						forwardStr.length(),
						Math.max(neutralStr.length(), reverseStr.length()) + 2));
		// + 2 for space and number
		throttlePosnDisplay.setBackground(Color.lightGray);
		throttlePosnDisplay.setForeground(Color.blue);
		setThrottlePosn(0);
		displaysCenter.add(throttlePosnDisplay);

		startOver = new Panel();
		startOver.setBackground(Color.lightGray);
		startOver.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
		startOverBtn = new Button(startOverBtnLabel);
		startOverBtn.setForeground(Color.blue);
		startOver.add(startOverBtn);

		gaugeDisplay = simulator.getBoatGaugeDisplay();
		if (gaugeDisplay != null) {
			displays.add("South", gaugeDisplay);
		}

		displays.add("Center", displaysCenter);
		displays.add("East", startOver);

		{
			Panel settings2, centered1, centered2, centered3;
			int centerOffsetX = 1;
			int centerOffsetY = 1;

			settings = new FramedPanel(
					frameColor,
					Color.lightGray,
					new Insets(0, 1, 0, 1),
					new BorderLayout());
			windControl = new VectorInput(
					this,
					windIndex,
					"Wind",
					" knots",
					Color.blue,
					94,
					128,
					14,
					30,
					3,
					centerOffsetX,
					centerOffsetY);
			centered1 = new Panel();
			centered1.setBackground(Color.lightGray);
			centered1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			centered1.add(windControl);
			settings.add("North", centered1);
			settings2 = new Panel();
			settings2.setBackground(Color.lightGray);
			settings2.setLayout(new BorderLayout());
			currentControl = new VectorInput(
					this,
					currentIndex,
					"Current",
					" knots",
					Color.blue,
					94,
					128,
					14,
					9,
					3,
					centerOffsetX,
					centerOffsetY);
			centered2 = new Panel();
			centered2.setBackground(Color.lightGray);
			centered2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			centered2.add(currentControl);
			settings2.add("North", centered2);
			centered3 = new Panel();
			centered3.setBackground(Color.lightGray);
			centered3.setLayout(new FlowLayout(FlowLayout.CENTER, 4, 4));
			zeroBtn = new Button(zeroBtnLabel);
			zeroBtn.setForeground(Color.blue);
			centered3.add(zeroBtn);
			settings2.add("Center", centered3);
			settings2.add("South", new HelpText(vectorHelp));
			settings.add("Center", settings2);
		}

		{
			Panel bottomHelpFrame = new FramedPanel(
					frameColor,
					Color.lightGray,
					new Insets(1, 1, 1, 1),
					new BorderLayout());

			add("North", displays);
			add("Center", simulator);
			add("East", settings);
			bottomHelpFrame.add("Center", new HelpText(bottomHelp));
			add("South", bottomHelpFrame);
		}

		boatHitDock = false;

		startOverBtn.requestFocus();
	}

	private void forward() {
		if (boatHitDock) {
			recoverFromCollision();
		}
		setThrottlePosn(
				Math.min(throttlePosn + 1, boat.getThrottlePosnMax()));
		simulator.setBoatThrottlePosn(throttlePosn);
	}

	private void backward() {
		if (boatHitDock) {
			recoverFromCollision();
		}
		setThrottlePosn(
				Math.max(throttlePosn - 1, boat.getThrottlePosnMin()));
		simulator.setBoatThrottlePosn(throttlePosn);
	}

	public synchronized void keyDown(KeyEvent e) {
		switch (e.getKeyChar()) {
			case 's', 'S' ->
				startOver();
			case 'z', 'Z' ->
				zeroSettings();
			case 'g', 'G' ->
				forward();
			case 'b', 'B' ->
				backward();
			default -> {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP, KeyEvent.VK_KP_UP ->
						forward();
					case KeyEvent.VK_DOWN, KeyEvent.VK_KP_DOWN ->
						backward();
					case KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT -> {
						setRudderAngle(
								Math.max(rudderAngle - 1, boat.getRudderAngleMin()));
						simulator.setBoatRudderAngle(rudderAngle);
					}
					case KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT -> {
						setRudderAngle(
								Math.min(rudderAngle + 1, boat.getRudderAngleMax()));
						simulator.setBoatRudderAngle(rudderAngle);
					}
				}
			}
		}
	}

	private void recoverFromCollision() {
		simulator.recoverFromCollision();
		boatHitDock = false;
		simulator.start();
	}

	private void setRudderAngle(int rudderAngle) {
		rudderAngleDisplay.setText(
				rudderAngle == 0
						? amidshipsStr
						: (rudderAngle < 0 ? portStr : starboardStr)
								+ " "
								+ Math.abs(rudderAngle));
		this.rudderAngle = rudderAngle;
	}

	public void setSpeed(float speed) {
	}

	private void setThrottlePosn(int throttlePosn) {
		throttlePosnDisplay.setText(
				throttlePosn == 0
						? neutralStr
						: (throttlePosn > 0 ? forwardStr : reverseStr)
								+ " "
								+ Math.abs(throttlePosn));
		this.throttlePosn = throttlePosn;
	}

	public synchronized void setVectorValue(
			int index,
			float direction,
			float value) {
		switch (index) {
			case windIndex:
				simulator.setWind(direction, value);
				break;
			case currentIndex:
				simulator.setCurrent(direction, value);
				break;
		}
	}

	public synchronized void start() {
		simulator.start();

		new PersistentStatusMsg(this, versionStr, 5);
	}

	private void startOver() {
		setThrottlePosn(0);
		setRudderAngle(0);
		boatHitDock = false;
		simulator.reset();
	}

	public synchronized void stop() {
		simulator.stop();
	}

	private void zeroSettings() {
		windControl.reset();
		currentControl.reset();
	}

	public void showStatus(String status) {
	}
}
