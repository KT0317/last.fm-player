package userInterface;

import userInterface.launcher;

public class launcher
{
	public static void main(String[] args)
	{
		try
		{
			mainFrame view = new mainFrame();
			view.launchView();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
