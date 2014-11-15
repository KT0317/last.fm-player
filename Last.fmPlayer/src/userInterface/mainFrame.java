package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import player.Player;
import player.Playlist;

public class mainFrame extends JFrame implements ActionListener
{
	private static final int PREF_MIN_WIDTH = 600;
	private static final int PREF_MIN_HEIGHT = 600;
	private JMenuItem AddToPlaylist, exit, ViewPlayer, ViewDescription, OptionSettings, HelpAbout;
	private JButton playerButton, descriptionButton, settingsButton;
	
	public mainFrame()
	{
		super("Last.fm Player");
		Container Pane = this.getContentPane();
		Pane.setLayout(new BorderLayout());
		
		//The Menu bar
		JMenuBar mb = new JMenuBar();
        this.setJMenuBar(mb);
        //Menu Items
        JMenu fileMenu = new JMenu("File");
        JMenu ViewMenu = new JMenu("View");
        JMenu OptionMenu = new JMenu("Options");
        JMenu HelpMenu = new JMenu("Help");
        //Add Menu to Menu Bar
        mb.add(fileMenu);
        mb.add(ViewMenu);
        mb.add(OptionMenu);
        mb.add(HelpMenu);
        
        
        
        //Add Items to the FileMenu
        AddToPlaylist = new JMenuItem("AddToPlaylist");
        exit = new JMenuItem("Exit");
        fileMenu.add(AddToPlaylist);
        fileMenu.add(exit);
        AddToPlaylist.addActionListener(this);
        exit.addActionListener(this);
        
        //Add Items to the ViewMenu
        ViewPlayer = new JMenuItem("Music Player");
        ViewDescription = new JMenuItem("Track Description");
        ViewMenu.add(ViewPlayer);
        ViewMenu.add(ViewDescription);
        ViewPlayer.addActionListener(this);
        ViewDescription.addActionListener(this);
        
        //Add Items to OptionMenu
        OptionSettings = new JMenuItem("Settings");
        OptionMenu.add(OptionSettings);
        OptionSettings.addActionListener(this);
        
        //Add Items to HelpMenu
        HelpAbout = new JMenuItem("About");
        HelpMenu.add(HelpAbout);
        HelpAbout.addActionListener(this);
        
        
        
        //Buttons for the sidebar
        JPanel Sidebar = new JPanel();
        Sidebar.setBackground(Color.GRAY);
        Sidebar.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);
    	c.weightx = 0.5;
    	c.gridx = 0;
        
    	
        playerButton = new JButton("Player");
        descriptionButton = new JButton("Description");
        settingsButton = new JButton("Settings");
        playerButton.addActionListener(this);
        descriptionButton.addActionListener(this);
        settingsButton.addActionListener(this);
        
        Sidebar.add(playerButton, c);
        c.gridy = 1;
        Sidebar.add(descriptionButton, c);
        c.gridy = 2;
        Sidebar.add(settingsButton, c);
        
        
        //Buttons for the playerbar
        JPanel PlayerBar = new JPanel();
        PlayerBar.setBackground(Color.DARK_GRAY);
        PlayerBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        //temporary buttons for testing
        JButton tempButton = new JButton(" ");
        PlayerBar.add(tempButton);
        PlayerBar.add(new JButton("1"));
        
        
        Pane.add(Sidebar, BorderLayout.WEST);
        Pane.add(PlayerBar, BorderLayout.SOUTH);
        this.pack();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == exit)	System.exit(0);
		
		else if (source == AddToPlaylist)
		{
			//menu pops up letting you choose mp3
		}
		
		else if (source == playerButton || source == ViewPlayer)
		{
			//change to player
		}
		
		
		else if (source == descriptionButton || source == ViewDescription)
		{
			//change to description page
		}
		
		
		else if (source == settingsButton || source == OptionSettings)
		{
			//change to setting page
		}
		
		
		else if (source == HelpAbout)
		{
			//go to about page
		}
		
	}
}
