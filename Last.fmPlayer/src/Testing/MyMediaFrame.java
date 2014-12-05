package Testing;

//Mike, you're a dick. Seriously.
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import player.Playlist;

public class MyMediaFrame extends JFrame implements ActionListener, ChangeListener, ItemListener
{
	static final long serialVersionUID = 42L;
	FloatControl volumeControl;
	float volume;
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
	private JButton previous;
	private int timeCounter;
	private int mins;
	private int seconds;
	private int endMins;
	private int endSeconds;
	private int totalTime;
	private JToggleButton shuffle;
	
	private JButton ChangeUser, Logoff;
	private JCheckBox EnableScrobbler;
	
	private static JSlider volumeSlider;
	private static JSlider timeSlider;
	private JLabel artistUrl = new JLabel();
	private JLabel artistListners = new JLabel();
	private JLabel artistPlaycount = new JLabel();
	private JLabel trackLabel = new JLabel();
	private JLabel artistLabel = new JLabel();
	private JLabel albumLabel = new JLabel();
	private JLabel lengthLabel = new JLabel();
	private JLabel noMetadataLabel = new JLabel("This track does not contain ID3 info.");
	public static JLabel UserLabel = new JLabel();
	private JPanel outerDescriptionPanel = new JPanel();
	private JMenuItem AddToPlaylist, exitMI, ViewPlayer, ViewDescription, OptionSettings, HelpAbout, openMI;
	private JButton playerButton, descriptionButton, settingsButton;
	JFrame notSupportedFrame = new JFrame("File Not Supported!");
	private JButton notSupportedButton = new JButton("Okay :c");
	private static final int PREF_MIN_WIDTH = 400;
	private static final int PREF_MIN_HEIGHT = 300;
	private boolean shekels = false;
	private boolean hasPaused = false;
	private boolean playing = false;
	private String fileString;
	
	private JLabel currentTime;
	private JLabel maxTime;
	private Timer timer = new Timer(1000,this);;
	
	private JPanel descriptionPanel = new JPanel();
	private JPanel currentlyPlaying = new JPanel();
	private JPanel SettingsPanel = new JPanel();
	private JPanel similarPanel = new JPanel();
	private JPanel aboutPanel = new JPanel();
	
	private JList playlistDisplay;
	private JMenuItem removeMenuItem;
	private JMenuItem playMenuItem;
	private JPopupMenu popupMenu;
	private int JListIndex;
	private JList JListCurrentItem;
	private DefaultListModel<String> inPlaylist = new DefaultListModel<String>();
	
	Playlist playlist = new Playlist();
	
	
	Scrobbler scrobbler =  new Scrobbler();
	
	JTextArea text = new JTextArea();
	public MyMediaFrame() throws Exception
	{
		super("ScrobbLord!");
		
		JFXPanel fxPanel = new JFXPanel();
		this.setMinimumSize(new Dimension(PREF_MIN_WIDTH, PREF_MIN_HEIGHT));
		this.setVisible(true);
		Container Pane = this.getContentPane();
		Pane.setLayout(new BorderLayout());
		
		new FileDrop( System.out, Pane, /*dragBorder,*/ new FileDrop.Listener()
        {   public void filesDropped( java.io.File[] files )
            {   for( int i = 0; i < files.length; i++ )
                {   try
                    {  
                		playlist.add(files[i].getCanonicalPath());
                    }   // end try
                    catch( Exception e ) {}
                } 
            populatePlaylist();
			//System.outprintln(playlist.getSize());
			buttonCheck();
            }   // end filesDropped
        }); // end FileDrop.Listener
		
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
		        
		    	
		playerButton = new JButton(/*"Player"*/ "\u266B");
		playerButton.setToolTipText("Now Playing");
		playerButton.setFont(new Font("", Font.PLAIN, 20));
		descriptionButton = new JButton(/*"Description"*/ "\u2630");
		descriptionButton.setToolTipText("Descriptions");
		descriptionButton.setFont(new Font("", Font.PLAIN, 20));
		settingsButton = new JButton(/*"Settings" "\u2622"*/ "\u2623");
		settingsButton.setToolTipText("Settings");
		settingsButton.setFont(new Font("", Font.PLAIN, 20));
		playerButton.addActionListener(this);
		descriptionButton.addActionListener(this);
		settingsButton.addActionListener(this);
		        
		Sidebar.add(playerButton, c);
		c.gridy = 1;
		Sidebar.add(descriptionButton, c);
		c.gridy = 2;
		Sidebar.add(settingsButton, c);
		        
		JPanel lowerPanel = new JPanel();
		lowerPanel.setBackground(Color.DARK_GRAY);
		lowerPanel.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.DARK_GRAY);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		play = new JButton("\u25BA");
		play.setToolTipText("Play/Pause");
		stop = new JButton("\u25A0");
		stop.setToolTipText("Stop");
		open = new JButton("Open");
		exit = new JButton("Exit");
		previous = new JButton("\u25C4\u25C4");
		previous.setToolTipText("Previous");
		next = new JButton("\u25BA\u25BA");
		next.setToolTipText("Next");
		shuffle = new JToggleButton("\u21C4");
		shuffle.setToolTipText("Shuffle");
		shuffle.setSelected(false);
		
		volumeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 50);
		volumeSlider.putClientProperty("JComponent.sizeVariant", "mini");
		volumeSlider.setPreferredSize(new Dimension(70, 30));
		volumeSlider.setMinorTickSpacing(5);
		volumeSlider.setMajorTickSpacing(25);
		volumeSlider.setPaintTicks(true);
		volumeSlider.setToolTipText("Volume");
		volume = (float) 100.0;
		
		currentTime = new JLabel("0:00");
		currentTime.setFont(new Font("", Font.PLAIN, 16));
		maxTime = new JLabel("0:00");
		maxTime.setFont(new Font("", Font.PLAIN, 16));
		timeSlider = new JSlider(0,100,0);
		
		buttonPanel.add(previous);
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
		previous.addActionListener(this);
		next.addActionListener(this);
		volumeSlider.addChangeListener(this);
		shuffle.addActionListener(this);
		timeSlider.addChangeListener(this);
		
		
		lowerPanel.add(buttonPanel, BorderLayout.CENTER);
		
		JPanel timePanel = new JPanel();
		timePanel.setBackground(Color.DARK_GRAY);
		timePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		timePanel.add(currentTime);
		timePanel.add(timeSlider);
		timePanel.add(maxTime);
		timePanel.add(volumeSlider);
		lowerPanel.add(timePanel, BorderLayout.NORTH);
		
		removeMenuItem = new JMenuItem("remove");
		playMenuItem = new JMenuItem("play");
		popupMenu = new JPopupMenu();
		popupMenu.add(playMenuItem);
		popupMenu.add(new JPopupMenu.Separator());
		popupMenu.add(removeMenuItem);
		
		playMenuItem.addActionListener(this);
		removeMenuItem.addActionListener(this);
		playlistDisplay = new JList<String>(inPlaylist);
		playlistDisplay.setDragEnabled(true);
		playlistDisplay.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent me)
			{
				JList temp = (JList) me.getSource();
				if (me.getClickCount() == 2)
				{
					int index = temp.locationToIndex(me.getPoint());
					//System.outprintln(index);
					if(!playlist.isEmpty())
					{
						playlist.setIndex(index);
						try
						{
							if(playing)
								stopSound();
							//Scrobbler.setNowPlaying(MyMediaFrame.this);
							displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
							setCurrentTrack();
							setMaxTime();
							timeSlider.setValue(timeCounter);
							playSound();
							play.setText("\u25AE\u25AE");
							hasPaused = false;
							buttonCheck();
						}
						catch(Exception e)
						{
							//System.outprintln("Ya dun goofed in playing");
							//System.outprintln(e.getMessage());
						}
					}
				}
				if (SwingUtilities.isRightMouseButton(me)
				           && !playlistDisplay.isSelectionEmpty()
				           && playlistDisplay.locationToIndex(me.getPoint())
				              == playlistDisplay.getSelectedIndex())
							 {
				               		popupMenu.show(playlistDisplay, me.getX(), me.getY());
				               		JListCurrentItem = (JList) me.getSource();
				               		JListIndex = JListCurrentItem.locationToIndex(me.getPoint());
							 }
			}
		});
		JPanel playlist = new JPanel();
		JPanel trackInfo = new JPanel();


		JScrollPane thePane = new JScrollPane(playlistDisplay);

		currentlyPlaying.setBorder(new TitledBorder(new EtchedBorder(), "Currently Playing"));
		currentlyPlaying.setLayout(new BorderLayout());
		currentlyPlaying.add(trackInfo, BorderLayout.CENTER);
		currentlyPlaying.add(playlist, BorderLayout.EAST);
		thePane.getViewport().setOpaque(false);
		thePane.setBorder(BorderFactory.createEmptyBorder());
		playlist.setOpaque(false);
		playlistDisplay.setOpaque(false);
		playlist.add(thePane);
		thePane.setBackground(currentlyPlaying.getBackground());
		thePane.getViewport().setBackground(currentlyPlaying.getBackground());
		thePane.setForeground(currentlyPlaying.getBackground());
		trackInfo.setLayout(new BoxLayout(trackInfo, BoxLayout.PAGE_AXIS));
		trackInfo.add(artistLabel);
		trackInfo.add(trackLabel);
		trackInfo.add(albumLabel);
		trackInfo.add(lengthLabel);
		noMetadataLabel.setVisible(false);
		trackInfo.add(noMetadataLabel);
		
		outerDescriptionPanel.setLayout(new GridLayout());
		descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.PAGE_AXIS));
		descriptionPanel.setBorder(new TitledBorder(new EtchedBorder(), "Global Artist Information"));
		descriptionPanel.add(artistUrl);
		descriptionPanel.add(artistListners);
		descriptionPanel.add(artistPlaycount);
		
		similarPanel.setLayout(new BoxLayout(similarPanel, BoxLayout.PAGE_AXIS));
		similarPanel.setBorder(new TitledBorder(new EtchedBorder(), "Artists you may like"));
		outerDescriptionPanel.add(descriptionPanel);//, BorderLayout.WEST);
		outerDescriptionPanel.add(similarPanel);//, BorderLayout.EAST);
		
		SettingsPanel.setLayout(new BoxLayout(SettingsPanel, BoxLayout.PAGE_AXIS));
		SettingsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Settings"));
		UserLabel.setText("Current User: " + scrobbler.getUser());
		EnableScrobbler = new JCheckBox("Disable Scrobbler");
		EnableScrobbler.addItemListener(this);
		ChangeUser = new JButton("Change User");
		ChangeUser.addActionListener(this);
		Logoff = new JButton("Log Off");
		Logoff.addActionListener(this);
		SettingsPanel.add(UserLabel);
		SettingsPanel.add(EnableScrobbler);
		SettingsPanel.add(ChangeUser);
		SettingsPanel.add(Logoff);
		
		aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.PAGE_AXIS));
		aboutPanel.setBorder(new TitledBorder(new EtchedBorder(), "About"));
		aboutPanel.add(new JLabel("ScrobbLord is a music player"));
		aboutPanel.add(new JLabel("with native Last.fm integration."));
		aboutPanel.add(new JLabel("What you listen to will give you recommendations"));
		aboutPanel.add(new JLabel("using your music taste as a heuristic."));
		aboutPanel.add(new JLabel("You will also be able to track your music habits using Last.fm!"));
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new CardLayout());
		contentPanel.add(currentlyPlaying);
		contentPanel.add(outerDescriptionPanel);
		contentPanel.add(SettingsPanel);
		contentPanel.add(aboutPanel);
		Pane.add(Sidebar, BorderLayout.WEST);
        Pane.add(lowerPanel, BorderLayout.SOUTH);
        Pane.add(contentPanel, BorderLayout.CENTER);
        this.pack();
		
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
				
				//System.outprintln(songUndArtist);
				inPlaylist.addElement(songUndArtist);
				playlistDisplay.setBackground(currentlyPlaying.getBackground());
			}
		}
		catch (Exception e)
		{
			//System.outprintln("Ya dun goofed");
			//System.outprintln(e.getMessage());
		}
	}
	
	public void stateChanged(ChangeEvent ce) 
	{
		JSlider s = (JSlider)ce.getSource();
		if(s == volumeSlider)
		{
			volume = s.getValue();
			mediaPlayer.setVolume(volume/50);
		}
		else if(s == timeSlider)
		{
			int newTime = s.getValue();
			if(newTime != timeCounter)
			{
				timeCounter = newTime;
				mediaPlayer.seek(Duration.millis(newTime*1000));
				mins = newTime/60;
				seconds = newTime%60;
				if(seconds < 10)
				{
					currentTime.setText(mins + ":0"+ seconds);
				}
				else
				{
					currentTime.setText(mins+":"+seconds);
				}
			}
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
     		//System.outprintln("Error with tags: " + e.toString()); 
     	}
		return id3;
	}
	
	public void getMetadata(File playFile)
	{
		String id3 = this.musicToBytes(playFile);
		String tag = id3.substring(0, 3);
		if (tag.equals("TAG")) 
         { 
         	this.setTrack(id3.substring(3, 32)); 
            this.setArtist(id3.substring(33, 62)); 
            this.setAlbum(id3.substring(63, 91)); 
            this.setYear(id3.substring(93, 97)); 
         }
         	else 
         	{
         		fileString = fileString.substring(fileString.lastIndexOf('\\') + 1);
         		trackLabel.setText("Title: "+fileString);
         		artistLabel.setText("Artist: Unknown");
         		albumLabel.setText("Album: Unknown");
         		noMetadataLabel.setVisible(true);
         	}
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if (source.equals(AddToPlaylist))
		{
			String userPlace = System.getProperty("user.home");
			JFileChooser fileChooser = new JFileChooser(userPlace+"\\Music");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Music Files", "mp3", "aac", "pcm");
			fileChooser.setFileFilter(filter);
			fileChooser.showOpenDialog(this);
			File file = fileChooser.getSelectedFile();
			
			//System.outprintln(file);
			fileString = file.toString();
			boolean check = checkFileFormat(fileString);
			if (check == false)
			{
				notSupported();
				return;
			}
			try
			{
				playlist.add(fileString);
			}
			catch(Exception e)
			{
				//System.outprintln("Ya dun goofed in adding to the playlist");
				//System.outprintln(e.getMessage());
			}
			
			if(playing)
			{
				play.setText("\u25AE\u25AE");
			}
			populatePlaylist();
			//System.outprintln(playlist.getSize());
			buttonCheck();
		}
		else if(source.equals(shuffle))
		{
			if(shuffle.isSelected())
			{
				shuffle.setSelected(false);
				shuffle.setText("\u21C4");			
			}
			else if(!shuffle.isSelected())
			{
				shuffle.setSelected(true);
				shuffle.setText("\u21C9");
			}	
			shekels = !shekels;
		}
		else if (source.equals(play))
		{
			try
			{
				if(playing)
				{
					pauseSound();
					play.setText("\u25BA");
					hasPaused = true;
				}
				else if (hasPaused)
				{
					playSound();
					play.setText("\u25AE\u25AE");
				}
				else
				{
					scrobbler.setNowPlaying(this);
					this.displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
					this.setCurrentTrack();
					this.setMaxTime();
					timeSlider.setValue(timeCounter);
					playSound();
					play.setText("\u25AE\u25AE");
					hasPaused = false;
				}
			}
			catch(Exception e)
			{
				//System.outprintln("Ya dun goofed in playing");
				//System.outprintln(e.getMessage());
			}
		}
		else if(source.equals(open) || source.equals(openMI))
		{
			
			timeCounter = 0;
			mins = 0;
			seconds = 0;
			try
			{
				playlist.clear();
				String userPlace = System.getProperty("user.home");
				JFileChooser fileChooser = new JFileChooser(userPlace+"\\Music");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Music Files", "mp3", "aac", "pcm");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog(this);
				File file = fileChooser.getSelectedFile();
				fileString = file.toString();
				if(playing)
					this.stopSound();
				String fileString = file.toString();
				
				boolean check = checkFileFormat(fileString);
				if (check == false)
				{
					notSupported();
					return;
				}
				try
				{
					playlist.add(fileString);
				}
				catch(Exception e)
				{
					//System.outprintln("Ya dun goofed in adding to the playlist");
					//System.outprintln(e.getMessage());
				}
				
				this.getMetadata(file);
				populatePlaylist();
				
				
				//scrobbler = new Scrobbler(this);
				scrobbler.setNowPlaying(this);
				this.displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
				getSimilar();
				this.setCurrentTrack();
				playSound();
				timeSlider.setValue(timeCounter);
				play.setText("\u25AE\u25AE");
				this.setMaxTime();
				buttonCheck();
			}
			catch (Exception e)
			{
				//System.outprintln("Ya dun goofed in opening a file");
				//System.outprintln(e.getMessage());
			}
		}
		else if(source.equals(notSupportedButton))
		{
			//System.outprintln("notSupporedButton");
			notSupportedFrame.dispose();
		}
		else if (source.equals(stop))
		{
			stopSound();
			play.setText("\u25BA");
		}
		else if (source.equals(exit) || source.equals(exitMI))
			System.exit(0);
		else if (source.equals(next))
		{
			timeCounter = 0;
			this.moveToNext();
		}
		else if (source.equals(previous))
		{
			timeCounter = 0;
			this.moveToPrev();
		}
		else if(source.equals(timer))
		{
			timeCounter++;
			seconds++;
			timeSlider.setValue(timeCounter);
			if(seconds == 60)
			{
				mins++;
				seconds =0;
			}
			if(seconds < 10)
			{
				currentTime.setText(mins + ":0"+ seconds);
			}
			else
			{
				currentTime.setText(mins+":"+seconds);
			}
			if(totalTime == timeCounter-1)
			{
				timer.stop();
				currentTime.setText("0:00");
				timeCounter = 0;
				mins = 0;
				seconds = 0;
				playing = false;
				if(playlist.hasNext())
				{
					try {
						playlist.getNext(shekels);
						getMetadata(playlist.getFile(playlist.getCurrentIndex()));
						this.displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
						this.setCurrentTrack();
						timeSlider.setValue(timeCounter);
						this.playSound();
						this.setMaxTime();
						buttonCheck();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else
				{
					play.setText("\u25BA");
					mediaPlayer.stop();
					playlist.setIndex(0);
					try {
						this.setCurrentTrack();
						timeSlider.setValue(timeCounter);
						this.setMaxTime();
						buttonCheck();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		else if(source.equals(descriptionButton) || source.equals(ViewDescription))
		{
			currentlyPlaying.setVisible(false);
			outerDescriptionPanel.setVisible(true);
			SettingsPanel.setVisible(false);
			aboutPanel.setVisible(false);
		}
		else if(source.equals(playerButton) || source.equals(ViewPlayer))
		{
			currentlyPlaying.setVisible(true);
			outerDescriptionPanel.setVisible(false);
			SettingsPanel.setVisible(false);
			aboutPanel.setVisible(false);
		}
		else if(source.equals(settingsButton) || source.equals(OptionSettings))
		{
			currentlyPlaying.setVisible(false);
			outerDescriptionPanel.setVisible(false);
			SettingsPanel.setVisible(true);
			aboutPanel.setVisible(false);
		}
		else if(source.equals(HelpAbout))
		{
			currentlyPlaying.setVisible(false);
			outerDescriptionPanel.setVisible(false);
			SettingsPanel.setVisible(false);
			aboutPanel.setVisible(true);
		}
		else if(source.equals(ChangeUser)){
			scrobbler.setChangeUserFlag(true);
			scrobbler.newAccountSetup();
			
			//System.outprintln("TEST"+scrobbler.getUser());
			UserLabel.setText("Current user: "+scrobbler.getUser());
		}
		
		else if(source.equals(Logoff))
		{
			if (Logoff.getText().equals("Login")){
				scrobbler.newAccountSetup();
				scrobbler.setScrobbleFlag(true);
				Logoff.setText("Log off");
			}
			else{
				scrobbler.setUserAndPass("", "");
				scrobbler.setScrobbleFlag(false);
				Logoff.setText("Login");
			}
		}
		else if(source.equals(playMenuItem))
		{
			System.out.println(JListIndex);
			if(!playlist.isEmpty())
			{
				playlist.setIndex(JListIndex);
				try
				{
					if(playing)
						stopSound();
					scrobbler.setNowPlaying(MyMediaFrame.this);
					displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
					setCurrentTrack();
					setMaxTime();
					timeSlider.setValue(timeCounter);
					playSound();
					play.setText("\u25AE\u25AE");
					hasPaused = false;
					buttonCheck();
				}
				catch(Exception e)
				{
					System.out.println("Ya dun goofed in playing");
					System.out.println(e.getMessage());
				}
			}
		}
		else if(source.equals(removeMenuItem))
		{
			try
			{
				DefaultListModel model = (DefaultListModel) playlistDisplay.getModel();
				model.remove(JListIndex);
				playlist.remove(JListIndex);
				populatePlaylist();
				System.out.println(JListIndex + "    " + playlist.getIndexOf("C:\\Users\\Michael\\Desktop\\Test FIles\\Depeche Mode - 25 Best Songs (2012) [Mp3][www.lokotorrents.com]\\02 - Personal Jesus 1990.mp3"));
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void setMaxTime() throws Exception
	{
		Thread.sleep(100);
		totalTime = (int)getLength()/1000;
		endMins = ((int)getLength()/1000)/60;
		endSeconds = ((int)getLength()/1000)%60;
		if(endSeconds < 10)
			maxTime.setText(endMins + ":0"+ endSeconds);
		else
			maxTime.setText(endMins + ":" + endSeconds);
		timeSlider.setMaximum((int)getLength()/1000);
	}
	public void moveToNext()
	{
		try
		{
			if(playing)
			{
				System.out.println(playlist.getCurrentIndex());
				this.stopSound();
				this.playlist.getNext(shekels);
				
				//System.outprintln("this is what the output is in media frame" + playlist.getCurrentIndex());
				this.setCurrentTrack();
				timeSlider.setValue(timeCounter);
				this.setMaxTime();
				this.playSound();
				this.buttonCheck();
			}
			else
			{
				this.playlist.getNext(shekels);
				this.setCurrentTrack();
			}
			this.displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
			
		}
		catch (Exception e)
		{
			//System.outprintln("Ya dun goofed in next");
			//System.outprintln(e.getMessage());
		}
	}
	
	public void moveToPrev()
	{
		try
		{
			if(playing)
			{
				this.stopSound();
				this.playlist.getPrev(shekels);
				this.setCurrentTrack();
				timeSlider.setValue(timeCounter);
				this.setMaxTime();
				this.playSound();
				this.buttonCheck();
			}
			else
			{
				this.playlist.getPrev(shekels);
				this.setCurrentTrack();
			}
			this.displayTrackInfo(playlist.getFile(playlist.getCurrentIndex()));
		}
		catch (Exception e)
		{
			//System.outprintln("Ya dun goofed in dickbutt");
			//System.outprintln(e.getMessage());
		}
	}
		
	public void displayTrackInfo(File file)
	{
		this.getMetadata(file);
		if(albumLabel.getText().equals("Album: Unknown"))
			return;

		if(scrobbler.getOfflineFlag() == false)
		{
			artistUrl.setText("<html>URL: <a href=\"" + scrobbler.getUrl() + "\">"+scrobbler.getUrl()+"</html>");
			artistUrl.setCursor(new Cursor(Cursor.HAND_CURSOR));
			artistUrl.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                    try {
	                           Desktop.getDesktop().browse(new URI(scrobbler.getUrl()));
	                    } catch (URISyntaxException | IOException ex) {
	                    }
	            }
	        });
			artistPlaycount.setText("Playcount: "+scrobbler.getPlaycount());
			artistListners.setText("Listeners: "+scrobbler.getListeners());
			//System.outprintln(scrobbler.getListeners());
		}
		System.out.println(Artist+Title);
		artistLabel.setText("Artist: "+Artist);
		trackLabel.setText("Title: "+Title);
		albumLabel.setText("Album: "+Album);
		if(Title.equals(null))
			artistLabel.setText("meow"); 
		else
		{
			scrobbler = new Scrobbler(this);
			scrobbler.scrobbleCurrent(this);
		}
	}
	
	private boolean checkFileFormat(String file)
	{
		String supportedFormats[] = ".aac .mp3 .pcm".split(" ");
		boolean supported = false;
		
		for(int i = 0; i < 3; i++)
		{
			//System.outprintln(supportedFormats[i]);
			if(file.endsWith(supportedFormats[i]))
			{
				supported = true;
				//System.outprintln(supported);
			}
		}
		
		return supported;
	}
	
	private void notSupported()
	{
		final int NOT_SUPPORTED_WIDTH = 400;
		final int NOT_SUPPORTED_HEIGHT = 200;
		JPanel notSupportedPanel = new JPanel();
		JLabel notSupportedLabel = new JLabel("Sorry, the file you have selected is not supported.");
		notSupportedFrame.setPreferredSize(new Dimension(NOT_SUPPORTED_WIDTH, NOT_SUPPORTED_HEIGHT));
		notSupportedFrame.setResizable(false);
		notSupportedButton.addActionListener(this);
		
		notSupportedPanel.add(notSupportedLabel);
		notSupportedPanel.add(notSupportedButton);
		notSupportedFrame.setContentPane(notSupportedPanel);
		notSupportedFrame.pack();
		notSupportedFrame.setVisible(true);
	}
	
	public void checkCache() throws InterruptedException
	{//Check cache
		scrobbler.scrobbleCache(); 
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
		mediaPlayer.setVolume(volume/50);
		mediaPlayer.play();
		System.out.println(mediaPlayer.getVolume());
		if (hasPaused)
			timer.start();
		else
		{
			timeCounter = 0;
			timer.restart();
		}
			
		scrobbler.setNowPlaying(this);
	}
	
	public void pauseSound()
	{
		playing = false;
		mediaPlayer.pause();
		timer.stop();
	}
		
	public void stopSound()
	{
		playing = false;
		mediaPlayer.stop();
		timer.stop();
		timeCounter = 0;
		mins = 0;
		seconds = 0;
		timeSlider.setValue(timeCounter);
		currentTime.setText("0:00");
	}
	public void setYear(String Year)
	{
		this.Year = Year.trim();
	}
	public void setArtist(String Artist)
	{
		this.Artist=Artist.trim();
	}
	public void setTrack(String Title)
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
			previous.setEnabled(false);
			shuffle.setEnabled(false);
			stop.setEnabled(false);
		}
		else
		{
			play.setEnabled(true);
			stop.setEnabled(true);
			
			if(playlist.getSize() >= 2)
			{
				shuffle.setEnabled(true);
			}
			else
			{
				shuffle.setEnabled(false);
			}
			
			if(!playlist.hasPrevious())
			{
				previous.setEnabled(false);
			}
			else
			{
				previous.setEnabled(true);
			}
			
			if(!playlist.hasNext()&& shekels == false)
			{
				next.setEnabled(false);
			}
			else
			{
				next.setEnabled(true);
			}
		}
	}
	

	public void getSimilar()
	{
		if(scrobbler.getOfflineFlag() == true)
			return;
		JLabel[] similarArtistLabels = new JLabel[4];
		final String[] similarArtistList = scrobbler.SimilarArtists(this);
	
		for(int i = 0; i < 4; i++)
		{
			final int j = i;
			//System.outprintln(similarArtistList[i]);
			similarArtistLabels[i] = new JLabel("<html><a href=''>"+similarArtistList[i]+"</a>");
			similarArtistLabels[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			similarArtistLabels[i].addMouseListener(new MouseAdapter() 
			{
	            public void mouseClicked(MouseEvent e) 
	            {
	                    try 
	                    {
	                            Desktop.getDesktop().browse(scrobbler.getOtherArtistUrl(similarArtistList[j]));
	                    } 
	                    catch (URISyntaxException | IOException ex) 
	                    {
	                   }
	            }
	        });
			similarPanel.add(similarArtistLabels[i]);
		}	
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		if (source == EnableScrobbler)
		{
			if (EnableScrobbler.isSelected()) scrobbler.setScrobbleFlag(true);
			else scrobbler.setScrobbleFlag(false);
			
		}
		
	}
}
