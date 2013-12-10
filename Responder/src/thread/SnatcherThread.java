package thread;

import java.net.UnknownHostException;

import gui.ConversationPanel;
import utilities.DefinitionSnatcher;
import utilities.ImageGetter;

public class SnatcherThread extends Thread{

	private String subject;
	
	private int mode;
	public static int IMAGES = 0;
	public static int DEFINITIONS = 1;
	
	public SnatcherThread(String subject, int mode){
		this.subject = subject;
		this.mode = mode;
	}
	
	public void run(){
		try {
			if(mode==IMAGES){
				ImageGetter ig = new ImageGetter(subject);
			}else{
				DefinitionSnatcher ds = new DefinitionSnatcher(subject);
			}
		} catch(UnknownHostException uhe){
			System.err.println("Problems connecting to web");
			ConversationPanel.claireToConversation("Sorry, I could not connect to the web.");
			return;
		}catch (Exception e) {
			System.err.println("Failed to search subject");
			ConversationPanel.claireToConversation("Sorry, an error occured while I was searching.");
			e.printStackTrace();
			return;
		}
		ConversationPanel.claireToConversation("Here's what I found.");
	}
	
}
