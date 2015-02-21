package mainApp;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.Before;

public class TitleTest { //Test looking at one ID3 tag in an MP3 and how the application reads it.
	
	private MyMediaFrame mf;
	
	@Before
	public void setUp() throws Exception
	{
		mf = new MyMediaFrame();
	}
	
	@Test
	public void testFile01()
	{
		mf.getMetadata(new File("TestCases//Test01.mp3"));
		assertEquals("", mf.getTitle());
	}
	
	@Test
	public void testFile02()
	{
		mf.getMetadata(new File("TestCases//Test02.mp3"));
		assertEquals("R", mf.getTitle());
	}
	
	@Test
	public void testFile03()
	{
		mf.getMetadata(new File("TestCases//Test03.mp3"));
		assertEquals("צ", mf.getTitle());
	}
	
	@Test
	public void testFile04()
	{
		mf.getMetadata(new File("TestCases//Test04.mp3"));
		assertEquals("Rumpelkmbo!", mf.getTitle());
	}
	
	@Test
	public void testFile05()
	{
		mf.getMetadata(new File("TestCases//Test05.mp3"));
		assertEquals("القراصنة رهيبة", mf.getTitle());
	}
	
	@Test
	public void testFile06()
	{
		mf.getMetadata(new File("TestCases//Test06.mp3"));
		assertEquals("29 characters are what I nee", mf.getTitle());
	}
	
	@Test
	public void testFile07()
	{
		mf.getMetadata(new File("TestCases//Test07.mp3"));
		assertEquals("30 characters are what I need", mf.getTitle());
	}
	
	@Test
	public void testFile08()
	{
		mf.getMetadata(new File("TestCases//Test08.mp3"));
		assertEquals("I must exceed the 30 character limit! I must!", mf.getTitle());
	}
	
	@Test
	public void testFile09()
	{
		mf.getMetadata(new File("TestCases//Test09.mp3"));
		assertEquals("29 сщздфтфнгуоилролрннрошзыв", mf.getTitle());
	}
	
	@Test
	public void testFile10()
	{
		mf.getMetadata(new File("TestCases//Test10.mp3"));
		assertEquals("30 сщздфтфнгуоилролрннрошзыви", mf.getTitle());
	}
	
	@Test
	public void testFile11()
	{
		mf.getMetadata(new File("TestCases//Test11.mp3"));
		assertEquals("30+ сщздфтфнгуоилролрннрошзыви....", mf.getTitle());
	}
	
	@Test
	public void testFile12()
	{
		mf.getMetadata(new File("TestCases//Test12.jpg"));
		assertEquals(null, mf.getTitle());
	}

}
