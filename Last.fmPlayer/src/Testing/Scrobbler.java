package Testing;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;

public class Scrobbler 
{//Scrobble class
	static String key = "4a7f6e9278971a6f8375d1f485e0d6a0";
    static String secret = "de5fd35fffed624ab4a3a2941e96b2bf";
    static String user;// = "TestAccount3461"; 
    static String password;// = "meowmix"; 
    		//Create session to communicate with last.fm server
    static Session session;
    private String cacheFile = "cache.txt";
    private String accountFile = "account.txt";
    
    //java.lang.ExceptionInInitializerError 
    //de.umass.lastfm.CallException
    //java.net.UnknownHostException
    
    
    private String url = new String();
    private String mbid = new String();
    private String playcount = new String();
    private String listeners = new String();
    private String title = new String();
    private String artist = new String();
    private String album = new String();
    JTextField usernameField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JFrame newUserFrame = new JFrame("New User");
    
    private int offlineFlag = 0;
    
    public void main(String args[])
    {//Main
    	
    }//Main
    
    												/**CONSTRUCTORS**/
    public Scrobbler()
    {//Default constructor
    	title = null;
    	artist = null;
    	album = null;
    	 try
    	 {
    		 if((user == null) || (password == null))
    			 newAccountSetup();
    		 
    	    session = Authenticator.getMobileSession(user, password, key, secret);
    	 }
    	 catch(Exception e)
    	 {
    		 offlineFlag = 1;
    	 }
    }//Default constructor
    
    
    public Scrobbler(MyMediaFrame track)
    {//Constructor with track
    	try{
    		if((user == null) || (password == null))
    			newAccountSetup();
	    	session = Authenticator.getMobileSession(user, password, key, secret);
    	}
		catch(Exception e)
		{
			offlineFlag = 1;
		}
    	//Get metadata from MyMediaFrame class
    	title = track.getTitle();
    	artist = track.getArtist();
    	album = track.getAlbum();
    	getArtistInfo(artist);
    	
    }//Constructor with track
    
    												/**METHODS**/
    
    					//Scrobble currently playing track
	public void scrobbleCurrent(MyMediaFrame track)
	{//scrobbleCurrent
		//Get current time
		int now = (int) (System.currentTimeMillis() / 1000);
		
		if(offlineFlag == 1)
		{//Offline
				//Add to cache
			addToCache(track, now);
			return;
		}//Offline

		Track.scrobble(artist, title, now, session);
		getArtistInfo(artist);
		return;
	}//scrobbleCurrent
	
	public void addToCache(MyMediaFrame track, int timeStamp)
	{//Build cache
		
		try
		{
	        FileWriter fw = new FileWriter(cacheFile, true);
	        fw.write(artist+";"+title+";"+timeStamp+"\n");
	        fw.close();
		}
		catch(Exception e)
		{
		}
		return;
	}//Build cache
	
	public void scrobbleCache()
	{//Scrobble cache
		if((user == null) || (password == null))
			return;
							/**REMINDER: NO MORE THAN 5 SCROBBLES PER SECOND
							 * AS PER LAST.FM USAGE AGREEMENT.*/
		try {
			Scanner fileReader = new Scanner(new File(cacheFile));
			
			while(fileReader.hasNextLine())
			{
				String line = fileReader.nextLine();
				artist = line.split(";")[0];
				title = line.split(";")[1];
				System.out.println(artist+title);
				int timeStamp = Integer.parseInt(line.split(";")[2]);
				Track.scrobble(artist, title, timeStamp, session);
			}
			//Clear cache file
			PrintWriter pw = new PrintWriter(cacheFile);
			pw.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("You don't have a cache. B-baka!");
		}
		return;
	}//Scrobble cache
	
	public void setNowPlaying(MyMediaFrame track)
	{//Set now playing
		System.out.println("SUP");
		if(offlineFlag == 1)//If offline, dont do this
			return;
		artist = track.getArtist();
		title = track.getTitle();
		Track.updateNowPlaying(artist, title, session);
	}//Set now playing
	
	
	private void getArtistInfo(String artistName)
	{	
		String info = Artist.getInfo(artistName, key).toString();

		String[] toSplit = info.split("url='");
		url = toSplit[1].split("'")[0];
		
		toSplit = info.split("mbid='");
		mbid = toSplit[1].split("'")[0];
		
		toSplit = info.split("playcount=");
		playcount = toSplit[1].split(",")[0];
		
		toSplit = info.split("listeners=");
		listeners = toSplit[1].split(",")[0];
		
		return;
	}
	
	public String getUrl()
	{
		return url;
	}
	public String getPlaycount()
	{
		return playcount;
	}
	public String getListeners()
	{
		return listeners;
	}
	
	public void setUserAndPass(String user, String pass)
	{
		this.user = user;
		this.password = pass;
	}
	
	public void newAccountSetup()
	{
		
		try
		{
			Scanner accountReader = new Scanner(new File(accountFile));
			String line = accountReader.nextLine();
			user = line.split(" ")[0];
			password = line.split(" ")[1];
			accountReader.close();
			return;
		}
		catch(Exception e)
		{
			
		}
		final int CHANGEUSER_PREF_MIN_WIDTH = 300;
		final int CHANGEUSER_PREF_MIN_HEIGHT = 300;
		
		JPanel changeUserPanel = new JPanel();
		JButton registerButton = new JButton("Register");
		JLabel welcomeLabel = new JLabel("Meow");
		
		changeUserPanel.setLayout(new BoxLayout(changeUserPanel, 1));
		
		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String user = usernameField.getText();
				String password = new String(passwordField.getPassword());
				try
				{
					FileWriter fw = new FileWriter(accountFile);
			        fw.write(user + " " + password);
			        fw.close();
				}
				catch(Exception ex)
				{
				}
				newUserFrame.dispose();
			}
		});

		newUserFrame.setPreferredSize(new Dimension(CHANGEUSER_PREF_MIN_WIDTH, CHANGEUSER_PREF_MIN_HEIGHT));
		changeUserPanel.add(usernameLabel);//, usernameField);
		usernameField.setMaximumSize(new Dimension(1000,25));
		passwordField.setMaximumSize(new Dimension(1000, 25));
		changeUserPanel.add(usernameField);
		changeUserPanel.add(passwordLabel);
		changeUserPanel.add(passwordField);
		changeUserPanel.add(registerButton);
		
		newUserFrame.add(changeUserPanel);
		newUserFrame.setContentPane(changeUserPanel);
		passwordField.setVisible(true);
		newUserFrame.setVisible(true);
		newUserFrame.pack();
	}
}//Scrobble class
