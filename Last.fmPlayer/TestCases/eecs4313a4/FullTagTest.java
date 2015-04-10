package eecs4313a4;


import static org.junit.Assert.*;

import java.io.File;

import mainApp.MyMediaFrame;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.sun.glass.events.KeyEvent;

import java.awt.Robot;

public class FullTagTest {
/*
 * Testing all tags as empty/non-empty using decision-table created approach
 * Also, automated testing that, though relies on an ugly quick hack, goes through all cases
 * Also tests the system's file selector via the Java Robot library
 */
	private MyMediaFrame mf;
	private Robot rb;
	
	@Before
	public void setUp() throws Exception
	{
		mf = new MyMediaFrame();
		mf.setTestMode(true);
		rb = new Robot();
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_O);		
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_O);
		rb.delay(100);
	}
	
	@Test
	public void testFullTags()
	{
		rb.keyPress(KeyEvent.VK_8);
		rb.keyRelease(KeyEvent.VK_8);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals("Rumpelkmbo!", mf.getTitle());
		assertEquals("Alestorm", mf.getArtist());
		assertEquals("Back Through Time", mf.getAlbum());
	}
	
	@Test
	public void testNoTags()
	{
		rb.keyPress(KeyEvent.VK_5);
		rb.keyRelease(KeyEvent.VK_5);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals("", mf.getTitle());
		assertEquals("", mf.getArtist());
		assertEquals("", mf.getAlbum());
	}
	
	@Test
	public void testNoTitle()
	{
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals("", mf.getTitle());
		assertEquals("Alestorm", mf.getArtist());
		assertEquals("Back Through Time", mf.getAlbum());
	}
	
	@Test
	public void testNoArtist()
	{
		rb.keyPress(KeyEvent.VK_6);
		rb.keyRelease(KeyEvent.VK_6);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals("Rumpelkombo!", mf.getTitle());
		assertEquals("", mf.getArtist());
		assertEquals("Back Through Time.", mf.getAlbum());
	}
	
	@Test
	public void testNoAlbum()
	{
		rb.keyPress(KeyEvent.VK_7);
		rb.keyRelease(KeyEvent.VK_7);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals("Rumpelkombo!", mf.getTitle());
		assertEquals("Alestorm", mf.getArtist());
		assertEquals("", mf.getAlbum());
	}
	
	@Test
	public void testOnlyArtist()
	{
		rb.keyPress(KeyEvent.VK_2);
		rb.keyRelease(KeyEvent.VK_2);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals("", mf.getTitle());
		assertEquals("Alestorm", mf.getArtist());
		assertEquals("", mf.getAlbum());
	}
	
	@Test
	public void testOnlyAlbum()
	{
		rb.keyPress(KeyEvent.VK_1);
		rb.keyRelease(KeyEvent.VK_1);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals("", mf.getTitle());
		assertEquals("", mf.getArtist());
		assertEquals("Back Through Time", mf.getAlbum());
	}
	
	@Test
	public void testOnlyTitle()
	{
		rb.keyPress(KeyEvent.VK_4);
		rb.keyRelease(KeyEvent.VK_4);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals("Rumpelkombo!", mf.getTitle());
		assertEquals("", mf.getArtist());
		assertEquals("", mf.getAlbum());
	}
	
	@Test
	public void testNonSound()
	{
		rb.keyPress(KeyEvent.VK_0);
		rb.keyRelease(KeyEvent.VK_0);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_J);
		rb.keyRelease(KeyEvent.VK_J);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_G);
		rb.keyRelease(KeyEvent.VK_G);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals(null, mf.getTitle());
		assertEquals(null, mf.getArtist());
		assertEquals(null, mf.getAlbum());
		
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(200);
		rb.keyPress(KeyEvent.VK_ALT);
		rb.keyPress(KeyEvent.VK_F4);
		rb.keyRelease(KeyEvent.VK_ALT);
		rb.keyRelease(KeyEvent.VK_F4);
	}
	
	@Test
	public void testNonFile()
	{
		rb.keyPress(KeyEvent.VK_0);
		rb.keyRelease(KeyEvent.VK_0);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals(null, mf.getTitle());
		assertEquals(null, mf.getArtist());
		assertEquals(null, mf.getAlbum());
		
		rb.delay(200);
		rb.keyPress(KeyEvent.VK_ALT);
		rb.keyPress(KeyEvent.VK_F4);
		rb.keyRelease(KeyEvent.VK_ALT);
		rb.keyRelease(KeyEvent.VK_F4);
	}
	
	@Test
	public void testProblematicFile() //Test using a file known not to have its tags read by other software; Android music player in particular.
	{ //Expected null tags due to problematic nature of file. If failed, it means metadata acquisition has changed and this file's encoding is supported.
		rb.keyPress(KeyEvent.VK_9);
		rb.keyRelease(KeyEvent.VK_9);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_PERIOD);
		rb.keyRelease(KeyEvent.VK_PERIOD);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_P);
		rb.keyRelease(KeyEvent.VK_P);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		rb.delay(100);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(2000);
		
		assertEquals(null, mf.getTitle());
		assertEquals(null, mf.getArtist());
		assertEquals(null, mf.getAlbum());
	}
	
	@After
	public void tearDown()
	{
		rb.keyPress(KeyEvent.VK_ALT);
		rb.keyPress(KeyEvent.VK_F4);
		rb.keyRelease(KeyEvent.VK_ALT);
		rb.keyRelease(KeyEvent.VK_F4);
	}
	
}
