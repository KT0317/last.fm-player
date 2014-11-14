package Testing;

import java.io.File;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.sound.sampled.FloatControl;

import java.awt.*;
import java.awt.event.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;

public class MyMedia 
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	      try {
	          UIManager.setLookAndFeel(
	             UIManager.getSystemLookAndFeelClassName());
	       } catch (Exception e) {}
	    
	    File soundFile = new File("stuff.mp3");
	    MyMediaFrame frame = new MyMediaFrame(soundFile);
	    frame.setTitle("DemoSoundVolume");
	 	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	       
			/*JFXPanel fxPanel = new JFXPanel();
		    Media track = new Media(new File("stuff.mp3").toURI().toString());
		    MediaPlayer mediaPlayer = new MediaPlayer(track);
		    mediaPlayer.play();
		    mediaPlayer.stop();
		    mediaPlayer.setVolume(0.1);
		    System.out.println(mediaPlayer.volumeProperty());*/
		    
	}
}
class MyMediaFrame extends JFrame implements ActionListener, ChangeListener
{
	static final long serialVersionUID = 42L;
	FloatControl volumeControl;
	float maxVolume;
	float minVolume;
	MediaPlayer mediaPlayer;

	private JButton play;
	private JButton stop;
	private JButton exit;
	private JSlider volumeSlider;
		
	public MyMediaFrame(File playFile)
	{
		JFXPanel fxPanel = new JFXPanel();
		Media track = new Media(new File("stuff.mp3").toURI().toString());
		System.out.println(new File("stuff.mp3").toURI().toString());
		System.out.println(track.getMarkers());
		mediaPlayer = new MediaPlayer(track);
			

		play = new JButton("Play");
		stop = new JButton("Stop");
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
		buttonPanel.add(exit);
		
		play.addActionListener(this); 
		stop.addActionListener(this); 
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
	
	public void actionPerformed(ActionEvent ae) 
	{
		JButton b = (JButton)ae.getSource();
		if (b == play)
			playSound();
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
}

