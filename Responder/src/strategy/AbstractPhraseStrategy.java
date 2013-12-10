package strategy;

import data.Trie;

/**
 * Concerns queries from the user that require more
 * thinking when giving an answer, especially questions
 * using the "5w's and H". Questions and greetings can 
 * be phrased in numerous different ways, so we need to
 * parser the users input to find keywords and respond 
 * accordingly.
 * 
 * eg. 
 *  - Tell me about *subject*
 *  - greetings
 * @author Emmanuel
 *
 */
public abstract class AbstractPhraseStrategy extends PhraseStrategy{
	
	protected Trie keywords;
	
	public Trie getKeywords(){
		return this.keywords;
	}
	
}
