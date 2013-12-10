package strategy;

import thread.SnatcherThread;
import utilities.BoyerMoore;
import utilities.ImageGetter;
import ai.Claire;

public class ShowMeStrategy extends ConcretePhraseStrategy {

	public ShowMeStrategy() {
		this.requiresSubject = true;
	}

	@Override
	public String respondToPhrase(Claire c) {
		// unsupported
		return null;
	}

	@Override
	public String respondToPhrase(String text, Claire c) {
		String noCaps = text.toLowerCase();
		BoyerMoore bm = new BoyerMoore("show me ");
		int i = bm.search(text) + 8;
		// TODO need to remove more garbage text
		// eg. show me "(a picture|an image) of a "
		// show me pictures/images of

		String subject = text.substring(i);
		SnatcherThread st = new SnatcherThread(subject,SnatcherThread.IMAGES);
		st.start();

		return "Give me a second...";
	}

}
