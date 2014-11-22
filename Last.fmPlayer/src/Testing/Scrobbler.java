package Testing;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;

public class Scrobbler 
{//Scrobble class
	static String key = "4a7f6e9278971a6f8375d1f485e0d6a0";
    static String secret = "de5fd35fffed624ab4a3a2941e96b2bf";
    static String user = "TestAccount3461"; 
    static String password = "meowmix"; 
    		//Create session to communicate with last.fm server
    static Session session;
    private String cacheFile = "cache.txt";
    
    //java.lang.ExceptionInInitializerError 
    //de.umass.lastfm.CallException
    //java.net.UnknownHostException
    
    static ArrayList<MyMediaFrame> songCache = new ArrayList();
    
    private String url = new String();
    private String mbid = new String();
    private String playcount = new String();
    private String listeners = new String();
    private String title = new String();
    private String artist = new String();
    private String album = new String();
    
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
							//Clear cache file
				PrintWriter pw = new PrintWriter(cacheFile);
				pw.close();
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}//Scrobble cache
	
	public void setNowPlaying(MyMediaFrame track)
	{//Set now playing
		if(offlineFlag == 1)//If offline, dont do this
			return;
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
}//Scrobble class