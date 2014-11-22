package Testing;

import player.Playlist;

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
import java.util.LinkedList;
import java.util.List;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
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
	
	private String currentTrack;
	String Year;
	String Title; 
	String Artist; 
	String Album; 
	
	private JButton play;
	private JButton stop;
	private JButton exit;
	private JButton open;
	private JButton next;
	private JButton dickbutt;
	
	private JToggleButton shuffle;
	
	private static JSlider volumeSlider;
	private static JProgressBar timeSlider;
	private JLabel artistUrl = new JLabel();
	private JLabel artistListners = new JLabel();
	private JLabel artistPlaycount = new JLabel();
	private JLabel trackLabel = new JLabel();
	private JLabel artistLabel = new JLabel();
	private JLabel albumLabel = new JLabel();
	private JLabel lengthLabel = new JLabel();
	private boolean playing;
	private JMenuItem AddToPlaylist, exitMI, ViewPlayer, ViewDescription, OptionSettings, HelpAbout, openMI;
	private JButton playerButton, descriptionButton, settingsButton;
	private static final int PREF_MIN_WIDTH = 200;
	private static final int PREF_MIN_HEIGHT = 200;
	private boolean shekels = false;
	
	private JList playlistDisplay;
	private DefaultListModel<String> inPlaylist = new DefaultListModel<String>();
	
	Playlist playlist = new Playlist();
	
	Scrobbler scrobbler = new Scrobbler();
	
	public MyMediaFrame() throws Exception
	{
		JFXPanel fxPanel = new JFXPanel();
		
		/*playlist.add("stuff.mp3");
		//playlist.add("G:\\DT\\Dark Tranquillity - Construct (2013) (MP3)\\07 Endtime Hearts.mp3");
		Media track = new Media(playlist.getFile(0).toURI().toString());
		mediaPlayer = new MediaPlayer(track);*/
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
		
		AddToPlaylist = new JMenuItem("Add To Playlist...");
		exitMI = new JMenuItem("Exit");
		openMI = new JMenuItem("Open..");
		fileMenu.add(openMI);
		fileMenu.add(AddToPlaylist);
		fileMenu.add(exitMI);
		AddToPlaylist.addActionListener(this);
		exitMI.addActionListener(this);
		openMI.addActionListener(this);
		        
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
		dickbutt = new JButton("Previous");
		next = new JButton("Next");
		shuffle = new JToggleButton("Shuffle");
		shuffle.setSelected(false);
		
		volumeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 100);
		volumeSlider.setMinorTickSpacing(3);
		volumeSlider.setMajorTickSpacing(25);
		volumeSlider.setPaintTicks(true);
		volumeSlider.setPaintLabels(true);
		
		timeSlider = new JProgressBar();

		buttonPanel.add(dickbutt);
		buttonPanel.add(play);
		buttonPanel.add(stop);
		buttonPanel.add(next);
		buttonPanel.add(open);
		buttonPanel.add(shuffle);
		buttonPanel.add(exit);
		
		play.addActionListener(this); 
		stop.addActionListener(this); 
		open.addActionListener(this);
		exit.addActionListener(this); 
		dickbutt.addActionListener(this);
		next.addActionListener(this);
		volumeSlider.addChangeListener(this);
		shuffle.addActionListener(this);
		//timeSlider.addChangeListener(this);

		
		buttonPanel.add(volumeSlider);
		//buttonPanel.add(timeSlider);
		
		
		playlistDisplay = new JList<String>(inPlaylist);
		JPanel currentlyPlaying = new JPanel();
		JPanel playlist = new JPanel();
		JPanel trackInfo = new JPanel();
		currentlyPlaying.setBorder(new TitledBorder(new EtchedBorder(), "Currently Playing"));
		currentlyPlaying.setLayout(new BorderLayout());
		currentlyPlaying.add(trackInfo, BorderLayout.CENTER);
		currentlyPlaying.add(playlist, BorderLayout.EAST);
		playlist.add(playlistDisplay);
		trackInfo.setLayout(new BoxLayout(trackInfo, BoxLayout.PAGE_AXIS));
		trackInfo.add(artistLabel);
		trackInfo.add(trackLabel);
		trackInfo.add(albumLabel);
		trackInfo.add(lengthLabel);
		
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
        Pane.add(currentlyPlaying, BorderLayout.CENTER);
        this.pack();
	//	this.setContentPane(panel);
		
		checkCache();
		buttonCheck();
	}

	public void populatePlaylist()
	{
		inPlaylist.clear();
		try
		{
			for(int i = 0; i < playlist.getSize(); i++)
			{
				File current = playlist.getFile(i);
				String backupName = current.toString();
				String id3 = this.musicToBytes(current);
				String tag = id3.substring(0, 3);
				String songUndArtist = "";
				
				if (tag.equals("TAG")) 
		        { 
					songUndArtist = id3.substring(3, 32).trim() + " - " + id3.substring(33, 62).trim(); 
		        }
		        else 
		        {
		        	int index = backupName.lastIndexOf("/");
		        	if(index == -1)
		        		index = backupName.lastIndexOf("\\");
		        	songUndArtist = backupName.substring(index + 1);
		        }
				
				System.out.println(songUndArtist);
				inPlaylist.addElement(songUndArtist);
			}
		}
		catch (Exception e)
		{
			System.out.println("Ya dun goofed");
			System.out.println(e.getMessage());
		}
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
	
	private String musicToBytes(File playFile)
	{
		String id3 = "";
		try 
		{ 
     	    File song = playFile; 
            FileInputStream file = new FileInputStream(song); 
            int size = (int)song.length(); 
            file.skip(size - 128); 
            byte[] last128 = new byte[128]; 
            file.read(last128); 
            id3 = new String(last128);
            file.close();
        } 
     	catch (Exception e)
     	{ 
     		System.out.println("Error with tags: " + e.toString()); 
     	}
		return id3;
	}
	
	public void getMetadata(File playFile)
	{
		String id3 = this.musicToBytes(playFile);
		String tag = id3.substring(0, 3);
		if (tag.equals("TAG")) 
         { 
         	this.setTitle(id3.substring(3, 32)); 
            this.setArtist(id3.substring(33, 62)); 
            this.setAlbum(id3.substring(63, 91)); 
            this.setYear(id3.substring(93, 97)); 
         }
         	else 
         		System.out.println(" does not contain" 
         		   + " ID3 info.");  
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if (source.equals(AddToPlaylist))
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(this);
			File file = fileChooser.getSelectedFile();
			String fileString = file.toString();
			System.out.println(file);
			try
			{
				playlist.add(fileString);
			}
			catch(Exception e)
			{
				System.out.println("Ya dun goofed in adding to the playlist");
				System.out.println(e.getMessage());
			}
			buttonCheck();
			populatePlaylist();
			System.out.println(playlist.getSize());
		}
		else if(source.equals(shuffle))
		{
			if(shuffle.isSelected())
			{
				shuffle.setSelected(false);
			}
			else if(!shuffle.isSelected())
			{
				shuffle.setSelected(true);
			}
			shekels = !shekels;
		}
		else if (source.equals(play))
		{
			try
			{
				this.setCurrentTrack();
				scrobbler.setNowPlaying(this);
				this.displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
				playSound();
			}
				catch(Exception e)
				{
					System.out.println("Ya dun goofed in playing");
					System.out.println(e.getMessage());
				}
		}
		else if(source.equals(open) || source.equals(openMI))
		{
			try
			{
				playlist.clear();
				this.openFile();
				scrobbler.setNowPlaying(this);
				this.displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
				playSound();
			}
			catch (Exception e)
			{
				System.out.println("Ya dun goofed in opening a file");
				System.out.println(e.getMessage());
			}
		}
		else if (source.equals(stop))
				stopSound();
		
		else if (source.equals(exit) || source.equals(exitMI))
				System.exit(0);
		else if (source.equals(next))
		{
			try
			{
				this.playlist.getNext(shekels);
				this.setCurrentTrack();
				//stopSound();
			}
			catch (Exception e)
			{
				System.out.println("Ya dun goofed in next");
				System.out.println(e.getMessage());
			}
		}
		else if (source.equals(dickbutt))
		{
			try
			{
				this.playlist.getPrev(shekels);
				this.setCurrentTrack();
				//stopSound();
			}
			catch (Exception e)
			{
				System.out.println("Ya dun goofed in dickbutt");
				System.out.println(e.getMessage());
			}
		}

	}
	
	public void openFile() throws Exception
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(this);
		File file = fileChooser.getSelectedFile();
		currentTrack = file.toString();
		System.out.println(file);
		playlist.add(currentTrack);
		populatePlaylist();
		buttonCheck();
		
		
		//mediaPlayer.dispose();
		
		//System.out.println((int)getLength()/1000);
		//timeSlider.setMaximumSize(mediaPlayer.getTotalDuration());
		
		timeSlider.setMaximum((int)getLength()/1000);
		//lengthLabel.setText("Tack legnth: "+getLength());
	}
		
	public void displayTrackInfo(File file)
	{
		this.getMetadata(file);
		scrobbler = new Scrobbler(this);
		artistUrl.setText("URL: " + scrobbler.getUrl());
		artistPlaycount.setText("Playcount: "+scrobbler.getPlaycount());
		artistListners.setText("Listeners: "+scrobbler.getListeners());
		artistLabel.setText("Artist: "+Artist);
		trackLabel.setText("Title: "+Title);
		albumLabel.setText("Album: "+Album);
		
		scrobbler.scrobbleCurrent(this);
	}
	
	public void checkCache()
	{//Check cache
		Scrobbler cacheScrobbler = new Scrobbler();
		cacheScrobbler.scrobbleCache();
	}//Check cache
	
	public void setCurrentTrack() throws Exception
	{
		currentTrack = playlist.getFile(playlist.getCurrentIndex()).toString();
		Media track = new Media(new File(currentTrack).toURI().toString());
		mediaPlayer = new MediaPlayer(track);
	}
	
	public void playSound()
	{
		playing = true;
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
	
	public void buttonCheck()
	{
		if(playlist.isEmpty())
		{
			play.setEnabled(false);
			next.setEnabled(false);
			dickbutt.setEnabled(false);
			shuffle.setEnabled(false);
			stop.setEnabled(false);
		}
		else
		{
			play.setEnabled(true);
			next.setEnabled(true);
			dickbutt.setEnabled(true);
			stop.setEnabled(true);
		}
	}
}