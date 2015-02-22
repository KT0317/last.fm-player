package eecs4313a2;

import static org.junit.Assert.*;

import java.io.File;

import mainApp.MyMediaFrame;

import org.junit.Test;
import org.junit.Before;


public class FileCompatibilityTest { //Tests if the check for supported formats works with common sound file formats.
	//Equivalence classes: openable, non-openable files

	private MyMediaFrame mf;
	
	@Before
	public void setUp() throws Exception
	{
		mf = new MyMediaFrame();
	}
	
	@Test
	public void testWithAAC()
	{
		assertTrue(mf.checkFileFormat(new File("TestCases/AACTest.m4a")));
	}

	@Test
	public void testWithMP3()
	{
		assertTrue(mf.checkFileFormat(new File("TestCases/Test01.mp3")));
	}
	
	@Test
	public void testWithWAV()
	{
		assertFalse(mf.checkFileFormat(new File("TestCases/WAVTest.wav")));
	}
	
	@Test
	public void testWithWMA()
	{
		assertFalse(mf.checkFileFormat(new File("TestCases/WMATest.wma")));
	}
	
	@Test
	public void testNonSound()
	{
		assertFalse(mf.checkFileFormat(new File("TestCases/Test12.jpg")));
	}
}
