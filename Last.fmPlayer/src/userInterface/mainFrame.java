package userInterface;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class mainFrame extends JFrame
{
	private static final int PREF_MIN_WIDTH = 600;
	private static final int PREF_MIN_HEIGHT = 600;
	
	public mainFrame()
	{
		super("Last.fm Player");
		//Do some stuff here; we'll figure it out.
	}
	
	public void launchView()
	{
		//Set Minimum window size
		this.setMinimumSize(new Dimension(PREF_MIN_WIDTH, PREF_MIN_HEIGHT));
		this.setVisible(true);
		//Window Listener, to close and whatnot
		this.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			}
		);
	}
}
