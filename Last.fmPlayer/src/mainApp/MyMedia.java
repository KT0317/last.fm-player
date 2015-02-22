package mainApp;

import java.io.File;
import java.io.FileInputStream;

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

	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		
	      try {
	          UIManager.setLookAndFeel(
	             UIManager.getSystemLookAndFeelClassName());
	       } catch (Exception e) {}
	    
	    //File soundFile = new File("stuff.mp3");
	    MyMediaFrame frame = new MyMediaFrame();
	    frame.setTitle("ScrobbLord!");
	    //frame.testMode = true;
	    //frame.setTitle("DemoSoundVolume");
	 	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
