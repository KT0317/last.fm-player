package Testing;

import java.awt.GridLayout;
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
	
	Scrobbler scrobbler = new Scrobbler();
	
	public MyMediaFrame(File playFile)
	{
		System.out.println(Artist);
		
		this.getMetadata(playFile);

		JFXPanel fxPanel = new JFXPanel();
		Media track = new Media(new File("stuff.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(track);

		play = new JButton("Play");
		stop = new JButton("Stop");
		open = new JButton("Open");
		exit = new JButton("Exit");
		volumeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 100);
		volumeSlider.setMinorTickSpacing(5);
		volumeSlider.setMajorTickSpacing(25);
		volumeSlider.setPaintTicks(true);
		volumeSlider.setPaintLabels(true);

			
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Command"));
		buttonPanel.add(play);
		buttonPanel.add(stop);
		buttonPanel.add(open);
		buttonPanel.add(exit);
		
		play.addActionListener(this); 
		stop.addActionListener(this); 
		open.addActionListener(this);
		exit.addActionListener(this); 
		volumeSlider.addChangeListener(this);

		JPanel volumePanel = new JPanel();
		volumePanel.setBorder(new TitledBorder(new EtchedBorder(), "Volume"));
		volumePanel.add(volumeSlider);
		
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
		panel.setLayout(new GridLayout(2, 1, 25, 25));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(buttonPanel);
		panel.add(volumePanel);	
		panel.add(currentlyPlaying);
		panel.add(infoPanel);

		this.setContentPane(panel);
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
	public int getLength()
	{
		String data = mediaPlayer.totalDurationProperty().toString();
		data = data.split("value: ")[1];
		data = data.split(" ms")[0];
		int length = Integer.parseInt(data);
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

