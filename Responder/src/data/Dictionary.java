package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;


public class Dictionary {
	
	private Trie trie;
	
	public Dictionary(){
		trie = new Trie();
	}

	// TODO should have a version of getWords that uses String[] instead of 
	// ArrayList<String>
	public String[] search(String text) {
		ArrayList<String> als = trie.getWords(text);
		String[] words = new String[als.size()];
		for(int i=0;i<als.size();i++){
			words[i] = als.get(i);
		}
		
		// ListModel scenario
		//DefaultListModel lm = new DefaultListModel();
		//for(int i=0;i<als.size();i++){
		//	lm.addElement(als.get(i));
		//}
		
		return words;
	}

	public void assemble(File dict) {
		try {
			Scanner scan = new Scanner(dict);
			String word;
			
			while(scan.hasNext()){
				word = scan.nextLine();
				word = word.trim();
				trie.add(word);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
