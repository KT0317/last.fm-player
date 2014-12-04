package player;

import java.util.List;
import java.io.File;
import java.util.LinkedList;

public class Playlist
{
	private List<File> files = new LinkedList<File>();
	private int playlistIndex = 0;
	int currentIndex;
	
	public Playlist()
	{
		//NOTHING!
	}
	
	public Playlist(String[] args) throws Exception
	{
		for(int i = 0; i < args.length; i++)
		{
			files.add(new File(args[i]));
		}
	}
	
	public void add(String name) throws Exception
	{
		File toAdd = new File(name);
		files.add(toAdd);
	}
	
	public void add(File file) throws Exception
	{
		files.add(file);
	}
	
	public void remove(String name) throws Exception
	{
		if(files.contains(new File(name)))
		{
			files.remove(files.indexOf(new File(name)));
		}
	}
	
	public void remove(File file) throws Exception
	{
		if(files.contains(file))
		{
			files.remove(files.indexOf(file));
		}
	}
	
	public void remove(int index) throws Exception
	{
		if(index > files.size())
		{
			files.remove(index);
		}
	}
	
	public File getFile(String name) throws Exception
	{
		File toReturn = null;
		File test = new File(name);
		if (files.contains(test))
		{
			toReturn = test;
		}
		return toReturn;
	}
	
	public File getFile(int index) throws Exception
	{
		if(index <= files.size())
		{
			return files.get(index);
		}
		return null;
	}
	
	
	public File getNext(boolean shuffle) throws Exception
	{
		File toReturn = null;
		if(shuffle)
		{
			currentIndex = playlistIndex;
			while(currentIndex == playlistIndex)
			{
				playlistIndex = (int) (Math.random() * (files.size()));
			}
			System.out.println("This is the index:" + playlistIndex + " And these are yor shekels:" + shuffle);
			toReturn = files.get(playlistIndex);
		}
		else
		{
			if(playlistIndex < files.size())
				playlistIndex++;
			toReturn = files.get(playlistIndex);
		}
		
		return toReturn;
	}
	
	public File getPrev(boolean shuffle) throws Exception
	{
		File toReturn = null;
		if(shuffle)
		{
			currentIndex = playlistIndex;
			while(currentIndex == playlistIndex)
			{
				playlistIndex = (int) (Math.random() * (files.size()));
			}
			toReturn = files.get(playlistIndex);
		}
		else
		{
			if(playlistIndex > 0)
				playlistIndex--;
			toReturn = files.get(playlistIndex);
		}
		return toReturn;
	}
	
	public int getIndexOf(String name) throws Exception
	{
		File test = new File(name);
		if(files.contains(test))
		{
			return files.indexOf(test);
		}
		return -1;
	}
	
	public int getIndexOf(File file) throws Exception
	{
		if(files.contains(file))
		{
			return files.indexOf(file);
		}
		return -1;
	}
	
	public void setToIndexOf(String name) throws Exception
	{
		File test = new File(name);
		if(files.contains(test))
		{
			this.playlistIndex = files.indexOf(test);
		}
	}
	
	public void setToIndexOf(File file) throws Exception
	{
		if(files.contains(file))
		{
			this.playlistIndex = files.indexOf(file);
		}
	}
	
	public void setIndex(int x)
	{
		this.playlistIndex = x;
	}
	
	public boolean hasNext()
	{
		if(this.playlistIndex < files.size() - 1)
			return true;
		return false;
	}
	
	public boolean hasPrevious()
	{
		if(this.playlistIndex > 0)
			return true;
		return false;
	}
	
	public int getCurrentIndex()
	{
		return playlistIndex;
	}
	
	public int getSize()
	{
		return files.size();
	}
	
	public boolean isEmpty()
	{
		return files.isEmpty();
	}
	
	public void clear()
	{
		files.clear();
		playlistIndex = 0;
	}

}
