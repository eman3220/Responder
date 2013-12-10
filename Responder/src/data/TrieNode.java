package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrieNode {

	private HashMap<Character,TrieNode> children;
	private boolean marked;
	
	public TrieNode(){
		children = new HashMap<Character,TrieNode>();
	}
	
	public void add(String word, int charNum) {
		// we have reached our goal, so mark it
		if(charNum == word.length()){
			this.marked = true;
			return;
		}
		
		char currentChar = word.charAt(charNum);
		
		if(children.containsKey(currentChar)){
			// there is a path we can take
			children.get(currentChar).add(word, ++charNum);
		}else{
			// path does not exist yet, so create one
			children.put(currentChar, new TrieNode());
			children.get(currentChar).add(word, ++charNum);
		}
	}

	public boolean contains(String word, int charNum) {
		if(charNum == word.length()){
			if(this.marked){
				return true;
			}
		}
		
		char currentChar = word.charAt(charNum);
		
		if(children.containsKey(currentChar)){
			return children.get(currentChar).contains(word, ++charNum);
		}else{
			return false;
		}
	}

	public ArrayList<String> find(String prefix, TrieNode subroot, ArrayList<String> words) {
		if(this.marked){
			words.add(prefix);
		}
		// traverse subtree
		for(Map.Entry<Character,TrieNode> e : children.entrySet()){
			String s = prefix + e.getKey();
			e.getValue().find(s, subroot, words);
		}
		return words;
	}

	public ArrayList<String> getWords(String prefix, int charNum) {
		if(charNum == prefix.length()){
			return find(prefix, this, new ArrayList<String>());
		}
		
		char currentChar = prefix.charAt(charNum);
		
		if(children.containsKey(currentChar)){
			return children.get(currentChar).getWords(prefix, ++charNum);
		}else{
			// there is no path for the prefix
			return new ArrayList<String>();
		}
	}

}
