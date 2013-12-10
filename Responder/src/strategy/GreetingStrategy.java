package strategy;

import utilities.BoyerMoore;
import data.Trie;
import ai.Claire;

public class GreetingStrategy extends AbstractPhraseStrategy{

	// input
	private final String[] words = {"hello","hiya","hi","greetings"};
	
	// output
	private final String[] responses = {"Hello *name*", "Hiya *name*", "Good day *name*"};
	// TODO could have additional response 
	
	public GreetingStrategy(){
		assembleTrie();
	}
	
	private void assembleTrie() {
		this.keywords = new Trie();
		for(String w : words){
			keywords.add(w);
		}
	}
	
	@Override
	public String respondToPhrase(Claire c) {
		int random = (int)Math.floor(Math.random()*responses.length);
		String s = responses[random];
			 
		return applyName(s,c);
	}

	private String applyName(String s, Claire c) {
		if(!s.contains("*name*"))return s;
		BoyerMoore bm = new BoyerMoore("*name*");
		int pos = bm.search(s);
		
		String preString = s.substring(0, pos-1);
		String postString = s.substring(pos+6, s.length());
		return preString+" "+c.getMemory().getUsersName()+" "+postString;
	}

	@Override
	public String respondToPhrase(String text, Claire c) {
		// unsupported
		return null;
	}

}
