package Testing;


import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.codec.binary.Base64;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleData;;

public class Scrobbler implements ActionListener
{//Scrobble class
	final String key = "4a7f6e9278971a6f8375d1f485e0d6a0";
    final String secret = "de5fd35fffed624ab4a3a2941e96b2bf";
    private String user; 
    private String password;
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
    private JTextField usernameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JFrame newUserFrame = new JFrame("New User");
	private JFrame welcomeFrame = new JFrame("Welcome!");
	private JButton registerButton = new JButton("Register");
	private JButton welcomeButton = new JButton("Continue");
	private JLabel wrongCredLabel = new JLabel("The username or password you entered is incorrect.");
	
    private boolean offlineFlag = false;
    private boolean scrobbleFlag;
    boolean changeUserFlag = false;
    
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
    		 offlineFlag = true;
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
			offlineFlag = true;
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
	{
		System.out.println(scrobbleFlag);
		if(scrobbleFlag)
		{
			//scrobbleCurrent
			//Get current time
			int now = (int) (System.currentTimeMillis() / 1000);
			
			if(offlineFlag == true)
			{//Offline
					//Add to cache
				addToCache(track, now);
				return;
			}//Offline
			
			invokeScrobbler(now);
			return;
		}
		else
		{
			return;
		}
	}//scrobbleCurrent

	public void addToCache(MyMediaFrame track, int timeStamp)
	{//Build cache
		try
		{
	        FileWriter fw = new FileWriter(cacheFile, true);
	        fw.write(artist+";"+title+";"+album+";"+timeStamp+"\n");
	        fw.close();
		}
		catch(Exception e)
		{
		}
		return;
	}//Build cache
	
	public void scrobbleCache() throws InterruptedException
	{//Scrobble cache
		if(scrobbleFlag)
		{
			//If there is no account logged in, do nothing
			
			if((user == null) || (password == null))
				return;
								/**REMINDER: NO MORE THAN 5 SCROBBLES PER SECOND
								 * AS PER LAST.FM USAGE AGREEMENT.*/
			try {
						//Open scanner for cache
				Scanner fileReader = new Scanner(new File(cacheFile));
				
				while(fileReader.hasNextLine())
				{//While loop over cache
						//Retrieve next line in cache
					String line = fileReader.nextLine();
						//Parse all information from said line
					artist = line.split(";")[0];
					title = line.split(";")[1];
					album = line.split(";")[2];
					int timeStamp = Integer.parseInt(line.split(";")[3]);
					invokeScrobbler(timeStamp);//Scrobble track
					Thread.sleep(200);	//Sleep for 1 second, to prevent scrobbling too quickly
				}//While loop over cache
				//Clear cache file
				PrintWriter pw = new PrintWriter(cacheFile);
				pw.close();//Close write stream
				fileReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("You don't have a cache.");
			}
			return;
		}
		else
		{
			return;
		}
	}//Scrobble cache
	
	public void setNowPlaying(MyMediaFrame track)
	{//Set now playing
		if((scrobbleFlag) && (offlineFlag == false))
		{
			artist = track.getArtist();
			title = track.getTitle();
			Track.updateNowPlaying(artist, title, session);
		}
		else
		{
			return;
		}
	}//Set now playing
	
	
	private void getArtistInfo(String artistName)
	{//Get artist info
		if(offlineFlag == true) //Offline, skip this part
			return;
			//Get artist info from last.fm
		String info = Artist.getInfo(artistName, key).toString();
			//Parse info, store in variables
		String[] toSplit = info.split("url='");
		url = toSplit[1].split("'")[0];
		
		toSplit = info.split("mbid='");
		mbid = toSplit[1].split("'")[0];
		
		toSplit = info.split("playcount=");
		playcount = toSplit[1].split(",")[0];
		
		toSplit = info.split("listeners=");
		listeners = toSplit[1].split(",")[0];
		
		return;
	}//Get artist info
	
	public void invokeScrobbler(int timestamp)
	{
		if(offlineFlag == true)
			return;
		ScrobbleData data = new ScrobbleData();
		data.setAlbum(album);
		data.setAlbumArtist(artist);
		data.setArtist(artist);
		data.setTimestamp(timestamp);
		data.setTrack(title);
		List<ScrobbleData> listicus = new LinkedList<ScrobbleData>();
		listicus.add(data);
		
		Track.scrobble(listicus, session);
		getArtistInfo(artist);
	}
	
	public String getUrl()
	{//get artist url
		return url;
	}//get artist url
	public String getPlaycount()
	{//get global playcount
		return playcount;
	}//get global playcount
	public String getListeners()
	{//get amount of listners
		return listeners;
	}//get amount of listners
	public boolean getOfflineFlag()
	{
		return offlineFlag;
	}
	
	public void setUserAndPass(String user, String pass)
	{//Change username and password
		this.user = user;
		this.password = pass;
	}//Change username and password
	
	public void newAccountSetup()
	{//Set up new account
		
		try
		{
			if(changeUserFlag == false)
			{
					//Check account file for existing user account
				Scanner accountReader = new Scanner(new File(accountFile));
					//If it exists read it in then return
				String line = accountReader.nextLine();
				user = line.split(" ")[0];
				byte[] pass = Base64.decodeBase64(line.split(" ")[1]);
				password = new String(pass);
				accountReader.close();
				return;
			}
		}
		catch(Exception e)
		{
			
		}
		final int CHANGEUSER_PREF_MIN_WIDTH = 300;
		final int CHANGEUSER_PREF_MIN_HEIGHT = 300;
		
		JPanel changeUserPanel = new JPanel();
		wrongCredLabel.setVisible(false);
		
		changeUserPanel.setLayout(new BoxLayout(changeUserPanel, 1));
		
		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel noAccountLabel = new JLabel("<html>Don't have an account?<br>Get one here: <a href=\"\">Last.fm Registration</a>");
		noAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		noAccountLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI("https://secure.last.fm/join"));
                    } catch (URISyntaxException | IOException ex) {
                            //It looks like there's a problem
                    }
            }
        });
		registerButton.addActionListener(this);

		newUserFrame.setPreferredSize(new Dimension(CHANGEUSER_PREF_MIN_WIDTH, CHANGEUSER_PREF_MIN_HEIGHT));
		changeUserPanel.add(usernameLabel);
		usernameField.setMaximumSize(new Dimension(1000,25));
		passwordField.setMaximumSize(new Dimension(1000, 25));
		passwordField.addActionListener(this);
		changeUserPanel.add(usernameField);
		changeUserPanel.add(passwordLabel);
		changeUserPanel.add(passwordField);
		changeUserPanel.add(registerButton);
		changeUserPanel.add(wrongCredLabel);
		changeUserPanel.add(noAccountLabel);
		
		newUserFrame.add(changeUserPanel);
		newUserFrame.setContentPane(changeUserPanel);
		passwordField.setVisible(true);
		newUserFrame.toFront(); 
		newUserFrame.setVisible(true);
		newUserFrame.pack();
	}//Set up new account
	
	public void welcomeNewUser()
	{//Welcome new user
		final int WELCOME_WIDTH = 300;
		final int WELCOME_HEIGHT = 300;
		
		JPanel welcomePanel = new JPanel();
		welcomeFrame.setPreferredSize(new Dimension(WELCOME_WIDTH, WELCOME_HEIGHT));
		welcomeFrame.setResizable(false);
		JLabel welcomeLabel = new JLabel("Welcome to last.fm Player!");
		JLabel infoLabel = new JLabel("<html>Currently we support the following formats:<br>"
										+ ".mp3\n.aac\n.pcm</html>");
		welcomePanel.setLayout(new BoxLayout(welcomePanel, 1));
		welcomeButton.addActionListener(this);
		
		welcomePanel.add(welcomeLabel);
		welcomePanel.add(infoLabel);
		welcomePanel.add(welcomeButton);
		
		welcomeFrame.setContentPane(welcomePanel);
		welcomeFrame.pack();
		welcomeFrame.setVisible(true);
		return;
	}//Welcome new user
	
	public void actionPerformed(ActionEvent ae)
	{//All action listeners
			//Get source of event
		Object source = ae.getSource();
		if((source.equals(registerButton)) || (source.equals(passwordField)))
		{//Register button
				//Get username/password from respetive fields
			user = usernameField.getText();
			password = new String(passwordField.getPassword());
			MyMediaFrame.UserLabel.setText("Current user: "+user);
			if(Authenticator.getMobileSession(user, password, key, secret) == null)
			{
				passwordField.setText("");
				wrongCredLabel.setVisible(true);
				return;
				
			}
			try
			{
					//Open writing stream, overwrite file with new information
				FileWriter fw = new FileWriter(accountFile);
				
				String encoding = Base64.encodeBase64String(password.getBytes());
				//byte[] pass = Base64.decodeBase64(encoding);
				
		        fw.write(user + " " + new String(encoding));
		        fw.close();
			}
			catch(Exception ex)
			{
			}
			newUserFrame.dispose();//Close new user frame
			welcomeNewUser();//Show welcoming page
		}//Register button
		else if(source.equals(welcomeButton))//Welcome button
			welcomeFrame.dispose();//Close welcome frame
	}//Set action listeners
	
	public String getUser()
	{
		return user;
	}
	
	public void setChangeUserFlag(boolean val)
	{
		this.changeUserFlag = val;
	}
	
	public void setScrobbleFlag(boolean val)
	{
		this.scrobbleFlag = val;
	}
	public URI getOtherArtistUrl(String toFind) throws URISyntaxException
	{
		String info = Artist.getInfo(toFind, key).toString();
		//Parse info, store in variables
		String[] toSplit = info.split("url='");
		String otherUrl = toSplit[1].split("'")[0];
		URI otherUri = new URI(otherUrl);
		return otherUri;
	}
	@SuppressWarnings("null")
	public String[] SimilarArtists(MyMediaFrame track){
		String similar = Artist.getSimilar(artist, key).toString();
		String firstSplit[] = similar.split("'");
		String[] similarArtistList = new String[4];
			
			
		for(int i = 0; i < 4; i++)
		{
			similarArtistList[i] = firstSplit[i*8+1];
		}
		return similarArtistList;
	}
}//Scrobble class
