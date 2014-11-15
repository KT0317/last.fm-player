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

import javax.imageio.ImageIO;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
			
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1, 25, 25));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(buttonPanel);
		panel.add(volumePanel);	
		Title = Title.trim();
		Artist = Artist.trim();
		Album = Album.trim();
		this.setContentPane(panel);
		Scrobbler x = new Scrobbler();
		x.scrobbleCurrent(this);
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
		//	scrobbleCurrent(this);
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
			Title = Title.trim();
			Artist = Artist.trim();
			Album = Album.trim();
			System.out.println(Title+Artist+Album);
			Scrobbler x = new Scrobbler();
			x.scrobbleCurrent(this);
		}
		else if (b == stop)
			stopSound();
		else if (b == exit)
			System.exit(0);
	}
	public void playSound()
	{
		mediaPlayer.play();
	}
		
	public void stopSound()
	{
		mediaPlayer.stop();
	}
	public void setYear(String Year)
	{
		this.Year = Year;
	}
	public void setArtist(String Artist)
	{
		this.Artist=Artist;
	}
	public void setTitle(String Title)
	{
		this.Title = Title;
	}
	public void setAlbum(String Album)
	{
		this.Album=Album;
	}
	public String getYear()
	{
		return Year;
	}
	public String getArtist()
	{
		return Artist;
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

