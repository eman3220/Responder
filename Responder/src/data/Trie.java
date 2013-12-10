package data;

import java.util.ArrayList;

public class Trie {
	
	private TrieNode root;
	
	public Trie(){
		System.out.println(">>> Trie initialised");
		root = new TrieNode();
	}

	public void add(String word) {
		if(word.equals(""))return;
		root.add(word, 0);
	}
	
	public boolean contains(String word){
		if(word.equals(""))return false;
		return root.contains(word,0);
	}
	
	public ArrayList<String> getWords(String prefix){		
		if(prefix.equals("")){
			return root.find(prefix,root,new ArrayList<String>());
		}
		ArrayList<String> toReturn = root.getWords(prefix,0);
		return toReturn;
	}

	public void clear() {
		this.root = new TrieNode();
		System.gc();
	}
}
