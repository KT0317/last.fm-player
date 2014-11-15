package Testing;


import java.util.ArrayList;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import Testing.MyMediaFrame;

public class Scrobbler 
{//Scrobble class
	static String key = "4a7f6e9278971a6f8375d1f485e0d6a0";
    static String secret = "de5fd35fffed624ab4a3a2941e96b2bf";
    static String user = "TestAccount3461"; 
    static String password = "meowmix"; 
    		//Create session to communicate with last.fm server
    static Session session = Authenticator.getMobileSession(user, password, key, secret);
    static ArrayList<MyMediaFrame> songCache = new ArrayList();
    
    public void main(String args[])
    {//Main
    	
    }//Main
    
    
    					//Scrobble currently playing track
	public void scrobbleCurrent(MyMediaFrame track)
	{//scrobbleCurrent
		System.out.println("hi");
			//Get current time
		
		int now = (int) (System.currentTimeMillis() / 1000);
			
			//Get metadata from MyMediaFrame class
		String title = track.getTitle();
		String artist = track.getArtist();
		String album = track.getAlbum();
		System.out.println(title + " " + artist + " " + album);
		Track.scrobble(artist, title, now, session);
		return;
		
			//Attempt to scrobble, get result
		/*ScrobbleResult result = Track.scrobble(title, artist, now, session);
 
			//Check is scrobble was successful
		if((result.isSuccessful()) && (!result.isIgnored()))//Successful
			return true;
		else //Not successful
			return false;*/
	}//scrobbleCurrent
	
	public void buildCache()
	{//Build cache
		
		return;
	}//Build cache
	
	public void scrobbleCache()
	{//Scrobble cache
		return;
	}//Scrobble cache
	
}//Scrobble class
