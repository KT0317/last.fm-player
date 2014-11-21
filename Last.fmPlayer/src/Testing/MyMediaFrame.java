package Testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyMediaFrame extends JFrame implements ActionListener, ChangeListener
{
	static final long serialVersionUID = 42L;
	FloatControl volumeControl;
	float maxVolume;
	float minVolume;
	MediaPlayer mediaPlayer;
	
	String Year;
	String Title; 
	String Artist; 
	String Album; 
	
	private JButton play;
	private JButton stop;
	private JButton exit;
	private JButton open;
	private static JSlider volumeSlider;
	private JLabel artistUrl = new JLabel();
	private JLabel artistListners = new JLabel();
	private JLabel artistPlaycount = new JLabel();
	private JLabel trackLabel = new JLabel();
	private JLabel artistLabel = new JLabel();
	private JLabel albumLabel = new JLabel();
	private JLabel lengthLabel = new JLabel();
	private JMenuItem AddToPlaylist, exitMI, ViewPlayer, ViewDescription, OptionSettings, HelpAbout;
	private JButton playerButton, descriptionButton, settingsButton;
	private static final int PREF_MIN_WIDTH = 600;
	private static final int PREF_MIN_HEIGHT = 600;
	
	Scrobbler scrobbler = new Scrobbler();
	
	public MyMediaFrame(File playFile)
	{
		
		JFXPanel fxPanel = new JFXPanel();
		Media track = new Media(new File("stuff.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(track);
		this.setMinimumSize(new Dimension(PREF_MIN_WIDTH, PREF_MIN_HEIGHT));
		this.setVisible(true);
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
		exitMI = new JMenuItem("Exit");
		fileMenu.add(AddToPlaylist);
		fileMenu.add(exitMI);
		AddToPlaylist.addActionListener(this);
		exitMI.addActionListener(this);
		        
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
		        
		 
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.DARK_GRAY);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		play = new JButton("Play");
		stop = new JButton("Stop");
		open = new JButton("Open");
		exit = new JButton("Exit");
		
		volumeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 100);
		volumeSlider.setMinorTickSpacing(5);
		volumeSlider.setMajorTickSpacing(25);
		volumeSlider.setPaintTicks(true);
		volumeSlider.setPaintLabels(true);

		buttonPanel.add(play);
		buttonPanel.add(stop);
		buttonPanel.add(open);
		buttonPanel.add(exit);
		
		play.addActionListener(this); 
		stop.addActionListener(this); 
		open.addActionListener(this);
		exit.addActionListener(this); 
		volumeSlider.addChangeListener(this);

		
		buttonPanel.add(volumeSlider);
		
		JPanel currentlyPlaying = new JPanel();
		currentlyPlaying.setBorder(new TitledBorder(new EtchedBorder(), "Currently Playing"));
		currentlyPlaying.setLayout(new BoxLayout(currentlyPlaying, BoxLayout.PAGE_AXIS));
		currentlyPlaying.add(artistLabel);
		currentlyPlaying.add(trackLabel);
		currentlyPlaying.add(albumLabel);
		currentlyPlaying.add(lengthLabel);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Global Artist Information"));
		infoPanel.add(artistUrl);
		infoPanel.add(artistListners);
		infoPanel.add(artistPlaycount);
		
		JPanel panel = new JPanel();
	/*	panel.setLayout(new GridLayout(2, 1, 25, 25));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(buttonPanel);
		panel.add(volumePanel);	
		panel.add(currentlyPlaying);
		panel.add(infoPanel);*/

		Pane.add(Sidebar, BorderLayout.WEST);
        Pane.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
	//	this.setContentPane(panel);
		
		checkCache();
	}

	public void stateChanged(ChangeEvent ce) 
	{
		JSlider s = (JSlider)ce.getSource();
		if(s == volumeSlider)
		{
			float vol = s.getValue();
			mediaPlayer.setVolume(vol/100);
		}
	}
	
	public void getMetadata(File playFile)
	{
		try { 
     	    File song = playFile; 
            FileInputStream file = new FileInputStream(song); 
            int size = (int)song.length(); 
            file.skip(size - 128); 
            byte[] last128 = new byte[128]; 
            file.read(last128); 
            String id3 = new String(last128); 
            String tag = id3.substring(0, 3); 
            if (tag.equals("TAG")) { 
               this.setTitle(id3.substring(3, 32)); 
               this.setArtist(id3.substring(33, 62)); 
               this.setAlbum(id3.substring(63, 91)); 
               this.setYear(id3.substring(93, 97)); 
            }
            	else 
            		System.out.println(" does not contain" 
            		   + " ID3 info.");  
            	file.close(); 
            } 
     	catch (Exception e)
     	{ 
     		System.out.println("Error ? " + e.toString()); 
       }
	}
	
	public void actionPerformed(ActionEvent ae)  
	{
		JButton b = (JButton)ae.getSource();
		if (b == play)
		{
			playSound();
			scrobbler.setNowPlaying(this);
		}
		else if(b == open)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(this);
			File file = fileChooser.getSelectedFile();
			String fileString = file.toString();
			System.out.println(file);
			Media track = new Media(new File(fileString).toURI().toString());
			mediaPlayer.dispose();
			mediaPlayer = new MediaPlayer(track);
			this.getMetadata(file);
			scrobbler = new Scrobbler(this);
			artistUrl.setText("URL: " + scrobbler.getUrl());
			artistPlaycount.setText("Playcount: "+scrobbler.getPlaycount());
			artistListners.setText("Listeners: "+scrobbler.getListeners());
			artistLabel.setText("Artist: "+Artist);
			trackLabel.setText("Title: "+Title);
			albumLabel.setText("Album: "+Album);
			lengthLabel.setText("Tack legnth: "+getLength());
			
			scrobbler.scrobbleCurrent(this);
		}
		else if (b == stop)
			stopSound();
		else if (b == exit)
			System.exit(0);
	}
	
	public void checkCache()
	{//Check cache
		Scrobbler cacheScrobbler = new Scrobbler();
		cacheScrobbler.scrobbleCache();
	}//Check cache
	
	public void playSound()
	{
		mediaPlayer.play();
		scrobbler.setNowPlaying(this);
	}
		
	public void stopSound()
	{
		mediaPlayer.stop();
	}
	public void setYear(String Year)
	{
		this.Year = Year.trim();
	}
	public void setArtist(String Artist)
	{
		this.Artist=Artist.trim();
	}
	public void setTitle(String Title)
	{
		this.Title = Title.trim();
	}
	public void setAlbum(String Album)
	{
		this.Album=Album.trim();
	}
	public String getYear()
	{
		return Year;
	}
	public String getArtist()
	{
		return Artist;
	}
	public double getLength()
	{
		String data = mediaPlayer.totalDurationProperty().toString();
		data = data.split("value: ")[1];
		data = data.split(" ms")[0];
		double length = Double.parseDouble(data);
		return length;
	}
	public String getTitle()
	{
		return Title;
	}
	public String getAlbum()
	{
		return Album;
	}
}