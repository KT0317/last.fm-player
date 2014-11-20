package Testing;


import java.util.ArrayList;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.Artist;

public class Scrobbler 
{//Scrobble class
	static String key = "4a7f6e9278971a6f8375d1f485e0d6a0";
    static String secret = "de5fd35fffed624ab4a3a2941e96b2bf";
    static String user = "TestAccount3461"; 
    static String password = "meowmix"; 
    		//Create session to communicate with last.fm server
    static Session session = Authenticator.getMobileSession(user, password, key, secret);
    static ArrayList<MyMediaFrame> songCache = new ArrayList();
    
    private String url = new String();
    private String mbid = new String();
    private String playcount = new String();
    private String listeners = new String();
    private String title = new String();
    private String artist = new String();
    private String album = new String();
    
    public void main(String args[])
    {//Main
    	
    }//Main
    
    												/**CONSTRUCTORS**/
    public Scrobbler()
    {//Default constructor
    	title = null;
    	artist = null;
    	album = null;
    }//Default constructor
    
    public Scrobbler(MyMediaFrame track)
    {//Constructor with track
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
		System.out.println("hi");
			//Get current time
		
		int now = (int) (System.currentTimeMillis() / 1000);

		System.out.println(title + " " + artist + " " + album);
		Track.scrobble(artist, title, now, session);
		getArtistInfo(artist);
		return;
	}//scrobbleCurrent
	
	public void buildCache()
	{//Build cache
		
		return;
	}//Build cache
	
	public void scrobbleCache()
	{//Scrobble cache
		return;
	}//Scrobble cache
	
	public void getArtistInfo(String artistName)
	{	
		String info = Artist.getInfo(artistName, key).toString();
		System.out.println(info);

		String[] toSplit = info.split("url='");
		url = toSplit[1].split("'")[0];
		
		toSplit = info.split("mbid='");
		mbid = toSplit[1].split("'")[0];
		
		toSplit = info.split("playcount=");
		playcount = toSplit[1].split(",")[0];
		
		toSplit = info.split("listeners=");
		listeners = toSplit[1].split(",")[0];
		
		System.out.println(url);
		System.out.println(mbid);
		System.out.println(playcount);
		System.out.println(listeners);
		
		return;
	}
	
	
}//Scrobble class
