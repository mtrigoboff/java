package chooseui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import mlt.SwingWindow;

public class ChooseUI {

    private static final UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();

    private final ChooseUIPanel panel;

    private SwingWindow wn;

    private ChooseUI(SwingWindow wn, ChooseUIPanel panel) {
	this.wn = wn;
	this.panel = panel;
    }

    private void setupUIMenu(SwingWindow wn) {
	final JRadioButtonMenuItem[] looksMIs = new JRadioButtonMenuItem[looks.length];

	JMenuBar menuBar;
	JMenu looksMenu;
	ButtonGroup bg = new ButtonGroup();
	JRadioButtonMenuItem mi;

	menuBar = new JMenuBar();
	looksMenu = new JMenu("Look and Feel");
	looksMenu.setMnemonic(KeyEvent.VK_L);
	for (int i = 0; i < looks.length; i++) {
	    mi = new JRadioButtonMenuItem(looks[i].getName());
	    if (i == 0) {
		mi.setSelected(true);
	    }
	    looksMenu.add(mi);
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    JRadioButtonMenuItem looksMI = (JRadioButtonMenuItem) e.getSource();
		    for (int i = 0; i < looks.length; i++) {
			if (looksMIs[i] == looksMI) {
			    setLookAndFeel(looks[i]);
			    break;
			}
		    }
		}
	    });
	    bg.add(mi);
	    looksMIs[i] = mi;
	}
	menuBar.add(looksMenu);
	wn.setJMenuBar(menuBar);
    }

    private void setLookAndFeel(UIManager.LookAndFeelInfo look) {
	try {
	    UIManager.setLookAndFeel(look.getClassName());
	} catch (Exception e) {
	}
	panel.setLookName(look.getName());
	SwingUtilities.updateComponentTreeUI(wn);
    }

    public static void main(String[] args) {
	// schedule a job for the event-dispatching thread to open the app
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		ChooseUIPanel panel = new ChooseUIPanel();
		SwingWindow wn = new SwingWindow("Choose User Interface", 600, 700, panel);
		ChooseUI app = new ChooseUI(wn, panel);
		wn.setMinimumSize(new Dimension(512, 480));
		wn.setQuitChar(KeyEvent.VK_ESCAPE);
		app.setupUIMenu(wn);
		panel.setWindow(wn);
		app.setLookAndFeel(looks[0]);
	    }
	});
    }

//	public static void xmain(String[] args) {
//		ChooseUIPanel	panel = new ChooseUIPanel();
//		SwingWindow		wn = new SwingWindow("Choose User Interface", 600, 700, panel);		
//		ChooseUI		app = new ChooseUI(wn, panel);
//		
//		wn.setMinimumSize(new Dimension(512, 480));
//		app.setupUIMenu();
//		panel.setWindow(wn);
//		app.setLookAndFeel(looks[0]);
//		wn.waitForClose();
//		System.exit(0);
//	}
}
