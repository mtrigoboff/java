package mlt.swingwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// This is a utility package for use by the other packages in this Eclipse project.
// It opens a Swing window for display of the specified window contents.

@SuppressWarnings("serial")
public class SwingWindow
    extends JFrame
{
    private boolean     				closed = false;

    public SwingWindow(String title, int width, int height, Component windowContents)
    {
        super(title);

        addWindowListener(
            new WindowAdapter()
            {
                public void windowClosed(WindowEvent e)
                {
                	// window has closed, allow waitForClose() to return
                    closed();
                }
            });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().add(windowContents, BorderLayout.CENTER);
            // default content pane layout manager is BorderLayout
        setSize(width, height);
        setVisible(true);
    }

    private synchronized void closed()
    {
        closed = true;
        notify();
    }

    // Allows the main() method in a Java app to wait until the window is closed by the user.
    // This is necessary because when main() exits, the window goes away. When this method
    // is not used, the window appears only briefly.
    public synchronized void waitForClose()
    {
        while (! closed)
            try { wait(); } catch (InterruptedException e) { }
    }
}