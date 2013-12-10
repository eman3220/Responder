package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.Trie;

public class TrieTests {

	private Trie t = new Trie();
	
	@Test
	public void testBadContains() {
		t.clear();
		assertFalse("does not contain apple",t.contains("apple"));
		assertFalse("does not contain nothing", t.contains(""));
	}

	@Test
	public void testGoodContains() {
		t.clear();
		t.add("apple");
		assertTrue("Should contain apple",t.contains("apple"));
	}
	
	@Test
	public void testMixContains() {
		t.clear();
		t.add("apple");
		assertTrue("does contain apple",t.contains("apple"));
		assertFalse("does not contain banana", t.contains("banana"));
		t.add("banana");
		assertTrue("does contain banana",t.contains("banana"));
	}
	
	@Test
	public void testClear(){
		t.clear();
		t.add("apple");
		assertTrue("does contain apple", t.contains("apple"));
		t.clear();
		assertFalse("does not contain apple anymore", t.contains("apple"));
	}
	
	@Test
	public void testGetWords(){
		t.clear();
		t.add("apple");
		t.add("applet");
		t.add("app");
		t.add("arc");
		t.add("arch");
		
		ArrayList<String> result = t.getWords("app");
		
		assertTrue("contains apple", result.contains("apple"));
		assertTrue("contains applet", result.contains("applet"));
		assertTrue("contains app", result.contains("app"));
	}
}
