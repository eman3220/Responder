package strategy;

import ai.Claire;

public abstract class PhraseStrategy {

	protected boolean requiresSubject = false;
	public abstract String respondToPhrase(Claire c);
	public abstract String respondToPhrase(String text, Claire c);
	public boolean getRequiresText(){
		return this.requiresSubject;
	}
}
