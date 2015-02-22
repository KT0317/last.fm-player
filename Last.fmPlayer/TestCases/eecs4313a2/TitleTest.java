package eecs4313a2;

import static org.junit.Assert.*;

import java.io.File;

import mainApp.MyMediaFrame;

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
		assertEquals("\u05E6", mf.getTitle());
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
		assertEquals("\u0627\u0644\u0642\u0631\u0627\u0635\u0646\u0629\u0631\u0647\u064A\u0628\u0629", mf.getTitle());
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
		assertEquals("29 \u0441\u0449\u0437\u0434\u0444\u0442\u0444\u043D\u0433\u0443\u043E\u0438\u043B\u0440\u043E\u043B"
				+ "\u0440\u043D\u043D\u0440\u043E\u0448\u0437\u044B\u0432", mf.getTitle());
	}
	
	@Test
	public void testFile10()
	{
		mf.getMetadata(new File("TestCases//Test10.mp3"));
		assertEquals("30 \u0441\u0449\u0437\u0434\u0444\u0442\u0444\u043D\u0433\u0443\u043E\u0438\u043B\u0440\u043E\u043B"
				+ "\u0440\u043D\u043D\u0440\u043E\u0448\u0437\u044B\u0432\u0438", mf.getTitle());
	}
	
	@Test
	public void testFile11()
	{
		mf.getMetadata(new File("TestCases//Test11.mp3"));
		assertEquals("30+ \u0441\u0449\u0437\u0434\u0444\u0442\u0444\u043D\u0433\u0443\u043E\u0438\u043B\u0440\u043E\u043B"
				+ "\u0440\u043D\u043D\u0440\u043E\u0448\u0437\u044B\u0432\u0438....", mf.getTitle());
	}
	
	@Test
	public void testFile12()
	{
		mf.getMetadata(new File("TestCases//Test12.jpg"));
		assertEquals(null, mf.getTitle());
	}

}
