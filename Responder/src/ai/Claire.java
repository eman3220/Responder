package ai;

import gui.ConversationPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import data.Trie;

import strategy.CallMeStrategy;
import strategy.GreetingStrategy;
import strategy.PhraseStrategy;
import strategy.ShowMeStrategy;
import strategy.TellMeAboutStrategy;

import attitude.Neutral;

/**
 * "Claire" is the representation of the artificial intelligence in this
 * project.
 * 
 * @author Emmanuel
 * 
 */
public class Claire {

	public static int state = 0;
	public static int LOADING = 0;
	public static int WAITING = 1;
	public static int THINKING = 2;
	public static int TALKING = 3;
	public static int SLEEPING = 4;

	private Memory memory;
	private Attitude attitude;
	private PhraseStrategy phrStrat;

	// concrete
	private static CallMeStrategy cms = new CallMeStrategy();
	private static ShowMeStrategy sms = new ShowMeStrategy();
	
	// abstract
	private static TellMeAboutStrategy tmas = new TellMeAboutStrategy();
	private static GreetingStrategy gs = new GreetingStrategy();

	public Claire() {
		remember();
		this.attitude = new Neutral();
	}

	/**
	 * Loads memory from files. If files does not exist, we must make new files.
	 * 
	 * @return
	 */
	private void remember() {
		this.memory = new Memory();
		memory.assembleDictionary();
		memory.assembleSubjectContent();
	}

	/**
	 * Check for key phrases
	 * 
	 * @param text
	 */
	public void respond(String text) {
		assert !text.equals("");
		String response = "";

		parseText(text);
		if (phrStrat.getRequiresText()) {
			response = this.phrStrat.respondToPhrase(text, this);
		} else {
			response = this.phrStrat.respondToPhrase(this);
		}

		if (response.equals(""))
			return;
		ConversationPanel.claireToConversation(response);
	}

	/**
	 * Parses the text entered by user. Handles the concrete phrases first,
	 * followed by abstract phrases.
	 * 
	 * @param text
	 */
	private void parseText(String text) {
		if (text.equals(""))
			return;

		this.phrStrat = null;
		
		// Check concrete strategies
		String noCaps = text.toLowerCase();
		if (noCaps.contains("call me ")) {
			this.phrStrat = cms;
			return;
		}else if(noCaps.contains("show me ")){
			this.phrStrat = sms;
			return;
		}
		
		// if none, check abstract strategies 
		
		// greeting strategy
		Trie gsKeywords = gs.getKeywords();
		int gsCount = 0;
		
		// tell me about strategy
		Trie tmasKeywords = tmas.getKeywords();
		int tmasCount = 0;
		
		Scanner scan = new Scanner(noCaps);
		while(scan.hasNext()){
			String word = scan.next();
			// if gsKeyword contains word, gsCount++
			if(gsKeywords.contains(word)) gsCount++;
			
			// if tmasKeyword contains word, tmasCount++
			if(tmasKeywords.contains(word))tmasCount++;
		}
		if(gsCount>tmasCount){
			this.phrStrat = gs;
		}
	}
	

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public Attitude getAttitude() {
		return attitude;
	}

	public void setAttitude(Attitude attitude) {
		this.attitude = attitude;
	}

}
