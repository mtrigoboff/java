package circlepainter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")

public class CirclePainterPanel
    extends JPanel
    implements ActionListener, ChangeListener, ItemListener
{
    final private static int        MARGIN = 30;
    final private static String     dialogBtnName = "Properties";

    private JFrame		window;
    private CirclePanel	circle;
    private int			circleSizePct = 50;
    private TextField	circleSizeTxt;
    private JPanel		circlePanel;
    JButton				dialogBtn;

    public CirclePainterPanel()
    {
        JPanel       guiPanel = new FramedPanel(FramedPanel.Level.HIGH, new GridLayout(3, 3, 20, 20));

        setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // top row

        guiPanel.add(new JPanel());

        {
        JPanel		scPanel = new FramedPanel();
        JCheckBox	showCircle = new JCheckBox("Show CirclePanel");

        scPanel.add(showCircle);
        showCircle.addItemListener(this);
        guiPanel.add(scPanel);
        }

        guiPanel.add(new JPanel());

        // middle row
        
        {
        JPanel		sliderPanel = new FramedPanel();
        JSlider		slider = new JSlider(0, 100, circleSizePct);

        sliderPanel.setLayout(new BorderLayout());
        slider.addChangeListener(this);
        sliderPanel.add(slider, BorderLayout.NORTH);
        circleSizeTxt = new TextField("circle size: " + circleSizePct + "%");
        circleSizeTxt.setEditable(false);
        sliderPanel.add(circleSizeTxt, BorderLayout.SOUTH);
        guiPanel.add(sliderPanel);
        }

        {
        JPanel       dlogBtnPanel = new JPanel();

        dialogBtn = new JButton(dialogBtnName);
        dialogBtn.setEnabled(false);
        dialogBtn.addActionListener(this);
        dlogBtnPanel.add(dialogBtn);
        guiPanel.add(dlogBtnPanel);
        }

        {
        ButtonGroup				btnGroup = new ButtonGroup();
        CirclePanel.RadioBtn[]	rBtns = {new CirclePanel.RadioBtn("Red", Color.red, btnGroup),
                                         new CirclePanel.RadioBtn("Green", Color.green, btnGroup),
                                         new CirclePanel.RadioBtn("Blue", Color.blue, btnGroup)};

        JPanel					rbPanel = new FramedPanel();

        rbPanel.setLayout(new GridLayout(3, 1));
        circle = new CirclePanel(rBtns[0].color, circleSizePct);
        for (int i = 0; i < rBtns.length; i++) {
            rbPanel.add(rBtns[i]);
            rBtns[i].addItemListener(circle);
            }
        btnGroup.setSelected(rBtns[0].getModel(), true);
        guiPanel.add(rbPanel);
        }

        // bottom row

        guiPanel.add(new JPanel());

        circlePanel = new FramedPanel(new BorderLayout());
        circlePanel.add(circle, BorderLayout.CENTER);
        circle.setVisible(false);
        guiPanel.add(circlePanel);

        guiPanel.add(new JPanel());
        
        // add guiPanel to this panel

        add(guiPanel, BorderLayout.CENTER);
    }

    // only the "Show CirclePanel" check box uses this listener
    public void itemStateChanged(ItemEvent e)
    {
        boolean     enabled = ((JCheckBox) e.getItemSelectable()).isSelected();

        circle.setVisible(enabled);
        dialogBtn.setEnabled(enabled);
        circlePanel.validate();
    }

    // only the slider uses this listener
    public void stateChanged(ChangeEvent e)
    {
    	JSlider		slider = (JSlider) e.getSource();
    	
        circleSizePct = slider.getValue();
        circleSizeTxt.setText("circle size: " + circleSizePct + "%");
        circle.setCircleSize(circleSizePct);
    }

    // only the "Properties" button uses this listener
    public void actionPerformed(ActionEvent e)
    {
        // final because they're constants, and so that local classes can use them
        final int	DLOG_WIDTH = 240;
        final int   DLOG_HEIGHT = 160;
        final int   DLOG_MARGIN = 20;
        final int   TEXT_MARGIN = 12;

        final JDialog	dlog;		// final so that local anonymous classes can use it

        JLabel      text;
        JPanel      btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton     btn = new JButton("Close");
        Point       parentLoc = getLocationOnScreen();
        Dimension   parentSize = getSize();

        if (e.getActionCommand().equals(dialogBtnName)) {
            dlog = new JDialog(window, "CirclePanel Properties", true);
    		dlog.getRootPane().setBorder(
    				BorderFactory.createEmptyBorder(DLOG_MARGIN, DLOG_MARGIN, DLOG_MARGIN, DLOG_MARGIN));
            dlog.setResizable(false);
            dlog.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    dlog.dispose();
                }
            });
            // center dialog in parent window
            dlog.setBounds(parentLoc.x + (parentSize.width - DLOG_WIDTH) / 2,
                           parentLoc.y + (parentSize.height - DLOG_HEIGHT) / 2,
                           DLOG_WIDTH, DLOG_HEIGHT);
            text = new JLabel("circle size " + circleSizePct + "%", JLabel.CENTER);
    		text.setBorder(
    				BorderFactory.createEmptyBorder(TEXT_MARGIN, TEXT_MARGIN, TEXT_MARGIN, TEXT_MARGIN));
            text.setForeground(circle.getColor());
            text.setBackground(Color.white);
            text.setOpaque(true);
            dlog.add(text, BorderLayout.NORTH);
            btnPanel.add(btn);
            dlog.add(btnPanel, BorderLayout.SOUTH);
            btn.addActionListener(ae -> dlog.dispose());
            dlog.setVisible(true);
            }
    }
}
