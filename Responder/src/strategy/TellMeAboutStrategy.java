package strategy;

import data.Trie;
import ai.Claire;

public class TellMeAboutStrategy extends AbstractPhraseStrategy{
	
	private final String[] words = {"tell","me","about","know","i","what's","what","is"};
	
	public TellMeAboutStrategy(){
		assembleTrie();
		this.requiresSubject = true;
	}
	
	private void assembleTrie() {
		this.keywords = new Trie();
		for(String w : words){
			keywords.add(w);
		}
	}

	@Override
	public String respondToPhrase(String text, Claire c) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String respondToPhrase(Claire c) {
		// TODO Auto-generated method stub
		return null;
	}

}
